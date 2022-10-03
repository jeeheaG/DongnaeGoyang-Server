package com.example.dongnaegoyangserver2022.domain.cat.application;

import com.example.dongnaegoyangserver2022.domain.cat.dao.CatRepository;
import com.example.dongnaegoyangserver2022.domain.cat.domain.Cat;
import com.example.dongnaegoyangserver2022.domain.cat.dto.CatResponse;
import com.example.dongnaegoyangserver2022.domain.cat.model.CatServiceModel.*;
import com.example.dongnaegoyangserver2022.domain.image.application.ImageService;
import com.example.dongnaegoyangserver2022.domain.image.model.ImageServiceModel;
import com.example.dongnaegoyangserver2022.domain.member.application.MemberService;
import com.example.dongnaegoyangserver2022.domain.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CatService {

    public final CatRepository catRepository;
    @Autowired
    private MemberService memberService;
    @Autowired
    private ImageService imageService;

    public Long addCat(HttpServletRequest servletRequest, CreateCatModel model){
//        log.info("[addCat] model: "+model); //잘 됨
        //cat을 만들고 member를 가져옴
        Cat cat = model.toEntity();
        Member member = getMember(servletRequest);

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

    public CatResponse.CatListResponseContainer getCatList(HttpServletRequest servletRequest, Pageable pageable){
        Member member = getMember(servletRequest);
        Page<Cat> catPaged = catRepository.findBySidoAndGugun(pageable, member.getSido(), member.getGugun()); //그냥 바로 List로 받아도 됨

        List<Cat> content = catPaged.getContent();

        return Cat.toCatListResponseContainer(content);
    }

    //-- function --//
    private Member getMember(HttpServletRequest servletRequest){
        return memberService.getMemberByHeader(servletRequest);
    }

}
