package com.example.dongnaegoyangserver2022.service;

import com.example.dongnaegoyangserver2022.dto.MemberRequest;
import com.example.dongnaegoyangserver2022.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Long login(HttpServletRequest httpServletRequest){

        Long memberIdx = 1L; //TODO
        return memberIdx;
    }

    public Long addMember(HttpServletRequest httpServletRequest, MemberRequest.signUpRequest request){
        String kakaoToken = httpServletRequest.getHeader("AUTH");


        Long memberIdx = 1L; //TODO
        return memberIdx;
    }


}
