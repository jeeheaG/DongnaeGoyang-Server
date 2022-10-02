package com.example.dongnaegoyangserver2022.domain.cat.application;

import com.example.dongnaegoyangserver2022.domain.cat.dao.CatRepository;
import com.example.dongnaegoyangserver2022.domain.cat.domain.Cat;
import com.example.dongnaegoyangserver2022.domain.cat.model.CatServiceModel.*;
import com.example.dongnaegoyangserver2022.domain.member.application.MemberService;
import com.example.dongnaegoyangserver2022.domain.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@Service
public class CatService {

    public final CatRepository catRepository;
    @Autowired
    private MemberService memberService;

    public Long addCat(HttpServletRequest servletRequest, CreateModel model){
//        log.info("[addCat] model: "+model); //잘 됨
        //cat을 만들고 member를 가져옴
        Cat cat = model.toEntity();
        Member member = memberService.getMemberByHeader(servletRequest);

        //cat에 member와 isPhoto를 설정함
        cat.setMember(member);
        cat.setIsPhoto(model.getPhotoList());

        Long catIdx = catRepository.save(cat).getCatIdx();
        //TODO : Image 테이블에 사진 저장
        if(cat.isPhoto()) {
            //..image
        }

        return catIdx;
    }

}
