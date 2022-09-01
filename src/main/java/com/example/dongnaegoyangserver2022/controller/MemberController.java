package com.example.dongnaegoyangserver2022.controller;

import com.example.dongnaegoyangserver2022.dto.JsonResponse;
import com.example.dongnaegoyangserver2022.dto.MemberRequest;
import com.example.dongnaegoyangserver2022.dto.MemberResponse;
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

    //test
    @GetMapping("/v1/members/tokenInfo")
    public ResponseEntity<Object> checkTokenInfo(HttpServletRequest httpServletRequest) {
        String info = memberService.checkTokenInfo(httpServletRequest);
        return ResponseEntity.ok(new JsonResponse(200, "checkTokenInfo called", info));
    }


    @PostMapping("/v1/members/login")
    public ResponseEntity<Object> login(@RequestBody MemberRequest.loginRequest request,
                                        HttpServletRequest httpServletRequest) { //@RequestHeader("AUTH") String header 로 바로 가져올 수도 있음
        MemberResponse.loginResponse loginResponse = memberService.login(httpServletRequest, request);
        return ResponseEntity.ok(new JsonResponse(200, "Success login", loginResponse));
    }

    @PostMapping("/v1/members/signUp")
    public ResponseEntity<Object> addMember(@RequestBody MemberRequest.signUpRequest request,
                                            HttpServletRequest httpServletRequest ) {
        Long memberIdx = memberService.addMember(httpServletRequest, request);
        return ResponseEntity.ok(new JsonResponse(201, "Success sign up", memberIdx));
    }
}
