package com.example.dongnaegoyangserver2022.domain.cat.application;

import com.example.dongnaegoyangserver2022.domain.cat.dao.CatRepository;
import com.example.dongnaegoyangserver2022.domain.cat.domain.Cat;
import com.example.dongnaegoyangserver2022.domain.cat.dto.CatResponse;
import com.example.dongnaegoyangserver2022.domain.cat.model.CatServiceModel.*;
import com.example.dongnaegoyangserver2022.domain.image.application.ImageService;
import com.example.dongnaegoyangserver2022.domain.image.model.ImageServiceModel;
import com.example.dongnaegoyangserver2022.domain.member.application.MemberService;
import com.example.dongnaegoyangserver2022.domain.member.domain.Member;
import com.example.dongnaegoyangserver2022.global.config.exception.RestApiException;
import com.example.dongnaegoyangserver2022.global.config.exception.error.CommonErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            imageService.createImage(imageModel);
        }

        return catIdx;
    }

    public CatResponse.CatListResponseContainer getCatList(Member member, Pageable pageable){
        Page<Cat> catPaged = catRepository.findBySidoAndGugun(pageable, member.getSido(), member.getGugun()); //그냥 바로 List로 받아도 됨

        List<Cat> content = catPaged.getContent();

        return Cat.toCatListResponseContainer(content);
    }

    public CatResponse.CatDetailResponse getCatDetail(Long kakaoId, Long catIdx){
        Cat cat = getCatByIdx(catIdx);

        log.info("[getCatDetail] cat : "+cat);

        Member writer = cat.getMember();
        List<Cat> otherCatList = catRepository.findOther5BySidoAndGugunRandom(writer.getSido(), writer.getGugun(), cat.getCatIdx());

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

        Member owner = cat.getMember();
        List<Cat> otherCatList = catRepository.findOther5BySidoAndGugunRandom(owner.getSido(), owner.getGugun(), cat.getCatIdx());

        return cat.toCatDetailAdditionalResponse(imageService.getImageList(cat), otherCatList);
    }

    //-- function --//
    private Cat getCatByIdx(Long catIdx){
        return catRepository.findById(catIdx)
                .orElseThrow(() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));
    }

}
