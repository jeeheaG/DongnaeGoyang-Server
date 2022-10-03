package com.example.dongnaegoyangserver2022.domain.member.api;

import com.example.dongnaegoyangserver2022.global.common.JsonResponse;
import com.example.dongnaegoyangserver2022.domain.member.dto.MemberRequest;
import com.example.dongnaegoyangserver2022.domain.member.dto.MemberResponse;
import com.example.dongnaegoyangserver2022.domain.member.application.MemberService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<Object> login(@RequestBody MemberRequest.LoginRequest request,
                                        HttpServletRequest httpServletRequest) { //@RequestHeader("AUTH") String header 로 바로 가져올 수도 있음
        MemberResponse.LoginResponse loginResponse = memberService.login(httpServletRequest, request);
        return ResponseEntity.ok(new JsonResponse(200, "Success login", loginResponse));
    }

    @PostMapping("/v1/members/signUp")
    public ResponseEntity<Object> addMember(@RequestBody MemberRequest.SignUpRequest request,
                                            HttpServletRequest httpServletRequest ) {
        Long memberIdx = memberService.addMember(httpServletRequest, request);
        return ResponseEntity.ok(new JsonResponse(201, "Success sign up", memberIdx));
    }

    @GetMapping("/v1/members/refresh")
    public ResponseEntity<Object> refreshToken(HttpServletRequest httpServletRequest) {
        String token = memberService.refreshToken(httpServletRequest);
        return ResponseEntity.ok(new JsonResponse(200, "Success refresh token", token));
    }
}
