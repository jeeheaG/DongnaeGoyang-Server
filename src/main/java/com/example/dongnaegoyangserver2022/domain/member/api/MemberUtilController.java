package com.example.dongnaegoyangserver2022.domain.member.api;

import com.example.dongnaegoyangserver2022.domain.member.application.MemberAuthService;
import com.example.dongnaegoyangserver2022.domain.member.application.MemberUtilService;
import com.example.dongnaegoyangserver2022.domain.member.domain.Member;
import com.example.dongnaegoyangserver2022.domain.member.dto.MemberRequest;
import com.example.dongnaegoyangserver2022.domain.member.dto.MemberResponse;
import com.example.dongnaegoyangserver2022.global.common.JsonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MemberUtilController {

    public final MemberUtilService memberUtilService;
    public final MemberAuthService memberAuthService;

    @GetMapping("/v1/members/town")
    public ResponseEntity<Object> getTown(HttpServletRequest servletRequest){
        log.info("[API] getTown");
        Member member = memberAuthService.getMemberByHeader(servletRequest);
        return ResponseEntity.ok(new JsonResponse(200, "success getTown", member.toMemberTownResponse()));
    }

    @PatchMapping("/v1/members/town")
    public ResponseEntity<Object> updateTown(HttpServletRequest servletRequest,
                                             @RequestBody MemberRequest.UpdateMemberTownRequest request){
        log.info("[API] updateTown");
        Member member = memberAuthService.getMemberByHeader(servletRequest);
        MemberResponse.MemberTownResponse memberTownResponse = memberUtilService.updateTown(member, request.toServiceModel());
        return ResponseEntity.ok(new JsonResponse(200, "success updateTown", memberTownResponse));
    }

    @PatchMapping("/v1/members/nickname")
    public ResponseEntity<Object> updateNickname(HttpServletRequest servletRequest,
                                             @RequestBody MemberRequest.UpdateMemberNicknameRequest request){
        log.info("[API] updateNickname");
        Member member = memberAuthService.getMemberByHeader(servletRequest);
        MemberResponse.MemberNicknameResponse memberNicknameResponse = memberUtilService.updateNickname(member, request.toServiceModel());
        return ResponseEntity.ok(new JsonResponse(200, "success updateNickname", memberNicknameResponse));
    }

}
