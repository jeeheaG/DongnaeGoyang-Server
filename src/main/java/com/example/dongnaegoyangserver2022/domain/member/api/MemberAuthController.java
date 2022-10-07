package com.example.dongnaegoyangserver2022.domain.member.api;

import com.example.dongnaegoyangserver2022.domain.member.domain.Member;
import com.example.dongnaegoyangserver2022.global.common.JsonResponse;
import com.example.dongnaegoyangserver2022.domain.member.dto.MemberRequest;
import com.example.dongnaegoyangserver2022.domain.member.dto.MemberResponse;
import com.example.dongnaegoyangserver2022.domain.member.application.MemberAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MemberAuthController {
    private final MemberAuthService memberAuthService;

    //test
    @GetMapping("/v1/members/tokenInfo")
    public ResponseEntity<Object> checkTokenInfo(HttpServletRequest httpServletRequest) {
        log.info("[API] checkTokenInfo");
        String info = memberAuthService.checkTokenInfo(httpServletRequest);
        return ResponseEntity.ok(new JsonResponse(200, "checkTokenInfo called", info));
    }


    @PostMapping("/v1/members/login")
    public ResponseEntity<Object> login(@RequestBody MemberRequest.LoginRequest request,
                                        HttpServletRequest httpServletRequest) { //@RequestHeader("AUTH") String header 로 바로 가져올 수도 있음
        log.info("[API] login");
        MemberResponse.LoginResponse loginResponse = memberAuthService.login(httpServletRequest, request);
        return ResponseEntity.ok(new JsonResponse(200, "Success login", loginResponse));
    }

    @PostMapping("/v1/members/signUp")
    public ResponseEntity<Object> addMember(@RequestBody MemberRequest.SignUpRequest request,
                                            HttpServletRequest httpServletRequest ) {
        log.info("[API] addMember");
        Long memberIdx = memberAuthService.addMember(httpServletRequest, request);
        return ResponseEntity.ok(new JsonResponse(201, "Success sign up", memberIdx));
    }

    @GetMapping("/v1/members/refresh")
    public ResponseEntity<Object> refreshToken(HttpServletRequest httpServletRequest) {
        log.info("[API] refreshToken");
        String token = memberAuthService.refreshToken(httpServletRequest);
        return ResponseEntity.ok(new JsonResponse(200, "Success refresh token", token));
    }


    @DeleteMapping("/v1/members/delete-account")
    public ResponseEntity<Object> deleteAccount(HttpServletRequest servletRequest){
        log.info("[API] deleteAccount");
        Member member = memberAuthService.getMemberByHeader(servletRequest);
        memberAuthService.deleteAccount(member);
        return ResponseEntity.ok(new JsonResponse(204, "Success deleteAccount", null));
    }
}
