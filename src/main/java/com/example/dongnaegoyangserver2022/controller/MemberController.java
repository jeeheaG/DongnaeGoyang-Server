package com.example.dongnaegoyangserver2022.controller;

import com.example.dongnaegoyangserver2022.dto.JsonResponse;
import com.example.dongnaegoyangserver2022.dto.MemberRequest;
import com.example.dongnaegoyangserver2022.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/api/members/tokenInfo")
    public ResponseEntity<Object> checkTokenInfo(HttpServletRequest httpServletRequest) {
        String info = memberService.checkTokenInfo(httpServletRequest);
        return ResponseEntity.ok(new JsonResponse(200, "checkTokenInfo called", info));
    }

    @PostMapping("/api/members/login")
    public ResponseEntity<Object> login( HttpServletRequest httpServletRequest ) { //@RequestHeader("AUTH") String header 로 바로 가져올 수도 있음
//        String kakaoToken = httpServletRequest.getHeader("AUTH");
//        System.out.println("token : "+ kakaoToken);
//        Long memberId = memberService.login(httpServletRequest);

        String token = memberService.login(httpServletRequest);
        return ResponseEntity.ok(new JsonResponse(200, "login called", token));
    }

    @PostMapping("/api/members/signUp")
    public ResponseEntity<Object> addMember(
            @RequestBody MemberRequest.signUpRequest request,
            HttpServletRequest httpServletRequest ) {
        Long memberIdx = memberService.addMember(httpServletRequest, request);
        return ResponseEntity.ok(new JsonResponse(200, "signUp called", memberIdx));
    }
}
