package com.example.dongnaegoyangserver2022.domain.cat.api;

import com.example.dongnaegoyangserver2022.domain.cat.application.CatService;
import com.example.dongnaegoyangserver2022.domain.cat.dto.CatRequest;
import com.example.dongnaegoyangserver2022.domain.cat.dto.CatResponse;
import com.example.dongnaegoyangserver2022.domain.cat.model.CatServiceModel;
import com.example.dongnaegoyangserver2022.domain.member.application.MemberService;
import com.example.dongnaegoyangserver2022.domain.member.domain.Member;
import com.example.dongnaegoyangserver2022.global.common.JsonResponse;
import com.example.dongnaegoyangserver2022.global.config.exception.RestApiException;
import com.example.dongnaegoyangserver2022.global.config.exception.error.MemberErrorCode;
import com.example.dongnaegoyangserver2022.global.config.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CatController {

    private final JwtTokenProvider jwtTokenProvider;

    private final CatService catService;
    private final MemberService memberService;

    @PostMapping("/v1/cats")
    public ResponseEntity<Object> createCat(HttpServletRequest servletRequest,
                                            @RequestBody CatRequest.CreateCatRequest request) {
        log.info("[API] createCat");
        Member member = memberService.getMemberByHeader(servletRequest);
        Long catIdx = catService.addCat(member, request.toCatServiceModel());
        return ResponseEntity.ok(new JsonResponse(201, "success createCat", catIdx));
    }

    @GetMapping("/v1/cats")
    public ResponseEntity<Object> getCatList(@RequestBody CatRequest.GetCatListRequest request,
                                             @RequestParam int page) { //TODO : member필요없음. body에서 주소 가져오도록 수정할 것
        log.info("[API] getCatList");
        PageRequest pageRequest = PageRequest.of(page, 20); // Pageable : page와 size
        CatServiceModel.GetCatListModel model = request.toCatServiceModel(pageRequest);
        CatResponse.CatListResponseContainer catListResponseContainer = catService.getCatList(model, pageRequest);
        return ResponseEntity.ok(new JsonResponse(200, "success getCatList", catListResponseContainer));
    }

    @GetMapping("/v1/cats/{catIdx}")
    public ResponseEntity<Object> getCatDetail(HttpServletRequest servletRequest, @PathVariable Long catIdx) {
        log.info("[API] getCatDetail");
        Long kakaoId = jwtTokenProvider.getUserPKByServlet(servletRequest);
        CatResponse.CatDetailResponse catDetailResponse = catService.getCatDetail(kakaoId, catIdx);
        return ResponseEntity.ok(new JsonResponse(200, "success getCatDetail", catDetailResponse));
    }

    @GetMapping("/v1/cats/{catIdx}/basic-info")
    public ResponseEntity<Object> getCatDetailBasic(HttpServletRequest servletRequest, @PathVariable Long catIdx) {
        log.info("[API] getCatDetailBasic");
        Long kakaoId = jwtTokenProvider.getUserPKByServlet(servletRequest);
        CatResponse.CatDetailBasicResponse catDetailBasicResponse = catService.getCatDetailBasic(kakaoId, catIdx);
        return ResponseEntity.ok(new JsonResponse(200, "success getCatDetailBasic", catDetailBasicResponse));
    }

    @GetMapping("/v1/cats/{catIdx}/additional-info")
    public ResponseEntity<Object> getCatDetailAdditional(@PathVariable Long catIdx) {
        log.info("[API] getCatDetailAdditional");
        CatResponse.CatDetailAdditionalResponse catDetailAdditionalResponse = catService.getCatDetailAdditional(catIdx);
        return ResponseEntity.ok(new JsonResponse(200, "success getCatDetailAdditional", catDetailAdditionalResponse));
    }

    @PatchMapping("/v1/cats/{catIdx}")
    public ResponseEntity<Object> updateCat(HttpServletRequest servletRequest,
                                            @PathVariable Long catIdx,
                                            @RequestBody CatRequest.UpdateCatRequest request) {
        log.info("[API] updateCat");
        Member member = memberService.getMemberByHeader(servletRequest);
        Long updatedCatIdx = catService.updateCat(member, catIdx, request.toCatServiceModel());
        return ResponseEntity.ok(new JsonResponse(200, "success updateCat", updatedCatIdx));
    }

    @GetMapping("/v1/cats/hello")
    public ResponseEntity<Object> hello() {
        return ResponseEntity.ok(new JsonResponse(200, "hello 고영", "test"));
    }

    @GetMapping("/v1/cats/hello-string")
    public String helloString() {
        return "hello";
    }



}
