package com.example.dongnaegoyangserver2022.domain.member.api;

import com.example.dongnaegoyangserver2022.domain.cat.application.CatService;
import com.example.dongnaegoyangserver2022.domain.cat.dto.CatResponse;
import com.example.dongnaegoyangserver2022.domain.member.application.MemberAuthService;
import com.example.dongnaegoyangserver2022.domain.member.application.MemberUtilService;
import com.example.dongnaegoyangserver2022.domain.member.domain.Member;
import com.example.dongnaegoyangserver2022.domain.member.dto.MemberRequest;
import com.example.dongnaegoyangserver2022.domain.member.dto.MemberResponse;
import com.example.dongnaegoyangserver2022.global.common.JsonResponse;
import com.example.dongnaegoyangserver2022.global.config.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MemberUtilController {

    public final MemberUtilService memberUtilService;
    public final MemberAuthService memberAuthService;
    public final CatService catService;

    @GetMapping("/v1/members/me/info")
    public ResponseEntity<Object> getMyInfo(HttpServletRequest servletRequest){
        log.info("[API] getMyInfo");
        Member member = memberAuthService.getMemberByHeader(servletRequest);
        MemberResponse.MemberMyInfoResponse memberMyInfoResponse = memberUtilService.getMyInfo(member);
        return ResponseEntity.ok(new JsonResponse(200, "success getMyInfo", memberMyInfoResponse));
    }

    @GetMapping("/v1/members/me/cats")
    public ResponseEntity<Object> getMyCatList(HttpServletRequest servletRequest,
                                               @RequestParam int page){
        log.info("[API] getMyCatList");
        Member member = memberAuthService.getMemberByHeader(servletRequest);
        PageRequest pageRequest = PageRequest.of(page, 30);
        List<CatResponse.SimpleCatResponse> catListResponses = catService.getSimpleCatListByMember(member,pageRequest);
        MemberResponse.MemberMyCatListResponse memberMyCatListResponse = MemberResponse.MemberMyCatListResponse.builder()
                .catList(catListResponses)
                .build();
        return ResponseEntity.ok(new JsonResponse(200, "success getMyCatList", memberMyCatListResponse));
    }

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
                                             @Valid @RequestBody MemberRequest.UpdateMemberNicknameRequest request){
        log.info("[API] updateNickname");
        Member member = memberAuthService.getMemberByHeader(servletRequest);
        MemberResponse.MemberNicknameResponse memberNicknameResponse = memberUtilService.updateNickname(member, request.toServiceModel());
        return ResponseEntity.ok(new JsonResponse(200, "success updateNickname", memberNicknameResponse));
    }

}
