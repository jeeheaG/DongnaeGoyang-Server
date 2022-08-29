package com.example.dongnaegoyangserver2022.controller;

import com.example.dongnaegoyangserver2022.dto.JsonResponse;
import com.example.dongnaegoyangserver2022.dto.MemberRequest;
import com.example.dongnaegoyangserver2022.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/api/members/login")
    public ResponseEntity<Object> login(
            HttpServletRequest httpServletRequest ) { //@RequestHeader("AUTH") String header 로 바로 가져올 수도 있음
        String kakaoToken = httpServletRequest.getHeader("AUTH");
//        System.out.println("token : "+ kakaoToken);
        return ResponseEntity.ok(new JsonResponse(200, "login called", kakaoToken));
    }

    @PostMapping("/api/members/signUp")
    public ResponseEntity<Object> addMember(
            @RequestBody MemberRequest.signUpRequest request,
            HttpServletRequest httpServletRequest ) {
        Long memberId = memberService.addMember(httpServletRequest, request);
        return ResponseEntity.ok(new JsonResponse(200, "signUp called", memberId));
    }
}
