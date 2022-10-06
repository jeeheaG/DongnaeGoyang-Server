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
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CatService {

    public final CatRepository catRepository;
    @Autowired
    private ImageService imageService;

    public Long addCat(Member member, CreateCatModel model){
//        log.info("[addCat] model: "+model); //잘 됨
        //cat을 만들고 member를 가져옴
        Cat cat = model.toEntity();

        //cat에 member와 isPhoto를 설정함
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
        Page<Cat> catPaged = catRepository.findBySidoAndGugun(pageable, model.getSido(), model.getGugun()); //그냥 바로 List로 받아도 됨

        List<Cat> content = catPaged.getContent();
        List<CatResponse.CatListResponse> catListResponses = content.stream().map(cat -> cat.toCatListResponse()).collect(Collectors.toList());
        return CatResponse.CatListResponseContainer.builder()
                .catList(catListResponses)
                .build();
    }

    public CatResponse.CatDetailResponse getCatDetail(Long kakaoId, Long catIdx){
        Cat cat = getCatByIdx(catIdx);

        log.info("[getCatDetail] cat : "+cat);

        List<Cat> otherCatList = catRepository.findOther5BySidoAndGugunRandom(cat.getSido(), cat.getGugun(), cat.getCatIdx());

        return cat.toCatDetailResponse(kakaoId, imageService.getImageList(cat), otherCatList);
    }

    public CatResponse.CatDetailBasicResponse getCatDetailBasic(Long kakaoId, Long catIdx){
        Cat cat = getCatByIdx(catIdx);
        log.info("[getCatDetailBasic] cat : "+cat);

        return cat.toCatDetailBasicResponse(kakaoId);
    }

    public CatResponse.CatDetailAdditionalResponse getCatDetailAdditional(Long catIdx){
        Cat cat = getCatByIdx(catIdx);
        log.info("[getCatDetailAdditional] cat : "+cat);

        List<Cat> otherCatList = catRepository.findOther5BySidoAndGugunRandom(cat.getSido(), cat.getGugun(), cat.getCatIdx());

        return cat.toCatDetailAdditionalResponse(imageService.getImageList(cat), otherCatList);
    }

    public Long updateCat(Member member, Long catIdx, UpdateCatModel model){
        Cat cat = getCatByIdx(catIdx);

        if(member != cat.getMember()){
            throw new RestApiException(MemberErrorCode.MEMBER_FORBIDDEN);
        }

        ModelMapper mapper = ModelMapperUtil.getModelMapper();
        ModelMapperUtil.setSkipNullEnabled(true);
        mapper.map(model, cat);
        ModelMapperUtil.setSkipNullEnabled(false);
        // static이니까 다시 setSkipNullEnabled(false) 해줘야 하나???oo
        // 원래 바디에 null로 하더라도 변수를 다 보내줬어야 했나.....? 아니 그게 아니라 원래 json안에 주석 못넣음........

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
    private Cat getCatByIdx(Long catIdx){
        return catRepository.findById(catIdx)
                .orElseThrow(() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));
    }

}
