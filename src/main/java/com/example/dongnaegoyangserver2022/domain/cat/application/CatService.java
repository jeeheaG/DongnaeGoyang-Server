package com.example.dongnaegoyangserver2022.domain.cat.application;

import com.example.dongnaegoyangserver2022.domain.cat.dao.CatRepository;
import com.example.dongnaegoyangserver2022.domain.cat.domain.Cat;
import com.example.dongnaegoyangserver2022.domain.cat.dto.CatResponse;
import com.example.dongnaegoyangserver2022.domain.cat.model.CatServiceModel.*;
import com.example.dongnaegoyangserver2022.domain.image.application.ImageService;
import com.example.dongnaegoyangserver2022.domain.image.model.ImageServiceModel;
import com.example.dongnaegoyangserver2022.domain.member.domain.Member;
import com.example.dongnaegoyangserver2022.global.common.ModelMapperUtil;
import com.example.dongnaegoyangserver2022.global.config.exception.RestApiException;
import com.example.dongnaegoyangserver2022.global.config.exception.error.CommonErrorCode;
import com.example.dongnaegoyangserver2022.global.config.exception.error.MemberErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CatService {

    public final CatRepository catRepository;
    @Autowired
    private ImageService imageService;

    public Cat getCatByIdx(Long catIdx){
        log.info("[SERVICE] getCatByIdx");
        Cat cat = null;
        try {
            cat = findCatByIdx(catIdx);
        }catch (RestApiException e){
            if(e.getErrorCode() == CommonErrorCode.RESOURCE_NOT_FOUND){
                log.info("[REJECT] getCatByIdx : No cat of catIdx = "+ catIdx);
                throw new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND_URL);
            }
        }
        return cat;
    }

    public List<CatResponse.SimpleCatResponse> getSimpleCatListByMember(Member member, Pageable pageable){
        log.info("[SERVICE] getSimpleCatListByMember");
        Page<Cat> catPage = catRepository.findByMember(pageable, member);

        List<Cat> catList = catPage.getContent();
        List<CatResponse.SimpleCatResponse> catResponseList = catList.stream().map(cat ->
                cat.toSimpleCatResponse())
                .collect(Collectors.toList());

        return catResponseList;
    }

    public Long addCat(Member member, CreateCatModel model){
        log.info("[SERVICE] addCat");
//        log.info("[addCat] model: "+model); //??? ???
        //cat??? ????????? member??? ?????????
        Cat cat = model.toEntity();

        //cat??? member??? isPhoto??? ?????????
        cat.setMember(member);
        cat.setIsPhoto(model.getPhotoList());

        Long catIdx = catRepository.save(cat).getCatIdx();

        //image
        if(cat.isPhoto()) {
            ImageServiceModel.CreateImageModel imageModel = new ImageServiceModel.CreateImageModel(model.getPhotoList(), cat);
            imageService.createImages(imageModel);
        }

        return catIdx;
    }

    public CatResponse.CatListResponseContainer getCatList(GetCatListModel model, Pageable pageable){
        log.info("[SERVICE] getCatList");
        Page<Cat> catPaged = catRepository.findBySidoAndGugun(pageable, model.getSido(), model.getGugun()); //?????? ?????? List??? ????????? ???

        List<Cat> content = catPaged.getContent();
        List<CatResponse.CatListResponse> catListResponses = content
                .stream()
                .map(cat ->
                        cat.toCatListResponse())
                .collect(Collectors.toList());

        return CatResponse.CatListResponseContainer.builder()
                .catList(catListResponses)
                .build();
    }

    public CatResponse.CatDetailResponse getCatDetail(Long kakaoId, Long catIdx){
        log.info("[SERVICE] getCatDetail");
        Cat cat = findCatByIdx(catIdx);

        log.info("[getCatDetail] cat : "+cat);

        List<Cat> otherCatList = catRepository.findOther5BySidoAndGugunRandom(cat.getSido(), cat.getGugun(), cat.getCatIdx());

        return cat.toCatDetailResponse(kakaoId, imageService.getImageList(cat), otherCatList);
    }

    public CatResponse.CatDetailBasicResponse getCatDetailBasic(Long kakaoId, Long catIdx){
        log.info("[SERVICE] getCatDetailBasic");
        Cat cat = findCatByIdx(catIdx);
        log.info("[getCatDetailBasic] cat : "+cat);

        return cat.toCatDetailBasicResponse(kakaoId);
    }

    public CatResponse.CatDetailAdditionalResponse getCatDetailAdditional(Long catIdx){
        log.info("[SERVICE] getCatDetailAdditional");
        Cat cat = findCatByIdx(catIdx);
        log.info("[getCatDetailAdditional] cat : "+cat);

        List<Cat> otherCatList = catRepository.findOther5BySidoAndGugunRandom(cat.getSido(), cat.getGugun(), cat.getCatIdx());

        return cat.toCatDetailAdditionalResponse(imageService.getImageList(cat), otherCatList);
    }

    public Long updateCat(Member member, Long catIdx, UpdateCatModel model){
        log.info("[SERVICE] updateCat");
        Cat cat = findCatByIdx(catIdx);

        if(member != cat.getMember()){
            throw new RestApiException(MemberErrorCode.MEMBER_FORBIDDEN);
        }

        ModelMapper mapper = ModelMapperUtil.getModelMapper();
        ModelMapperUtil.setSkipNullEnabled(true);
        mapper.map(model, cat);
        ModelMapperUtil.setSkipNullEnabled(false);
        // static????????? ?????? setSkipNullEnabled(false) ????????? ?????????oo
        // ?????? ????????? null??? ???????????? ????????? ??? ??????????????? ??????.....? ?????? ?????? ????????? ?????? json?????? ?????? ?????????........

        //image
        if(model.getCreatePhotoList() != null && !model.getCreatePhotoList().isEmpty()){
            imageService.createImages(new ImageServiceModel.CreateImageModel(model.getCreatePhotoList(), cat));
        }

        if(model.getDeletePhotoList() != null && !model.getDeletePhotoList().isEmpty()){
            imageService.deleteImages(model.getDeletePhotoList());
        }

        //isPhoto
        if((model.getCreatePhotoList() != null && !model.getCreatePhotoList().isEmpty()) || (model.getDeletePhotoList() != null && !model.getDeletePhotoList().isEmpty())){
            cat.setIsPhoto(!imageService.getImageList(cat).isEmpty());
        }

        return catRepository.save(cat).getCatIdx();
    }

    //-- function --//
    private Cat findCatByIdx(Long catIdx) throws RestApiException {
        log.info("[METHOD] findCatByIdx");
        return catRepository.findById(catIdx)
                .orElseThrow(() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));
    }

}
