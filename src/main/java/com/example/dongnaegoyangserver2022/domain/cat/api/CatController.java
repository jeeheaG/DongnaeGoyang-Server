package com.example.dongnaegoyangserver2022.domain.cat.api;

import com.example.dongnaegoyangserver2022.domain.cat.application.CatService;
import com.example.dongnaegoyangserver2022.domain.cat.dto.CatRequest;
import com.example.dongnaegoyangserver2022.domain.cat.dto.CatResponse;
import com.example.dongnaegoyangserver2022.domain.cat.model.CatServiceModel;
import com.example.dongnaegoyangserver2022.domain.member.application.MemberAuthService;
import com.example.dongnaegoyangserver2022.domain.member.domain.Member;
import com.example.dongnaegoyangserver2022.global.common.JsonResponse;
import com.example.dongnaegoyangserver2022.global.config.exception.CustomException;
import com.example.dongnaegoyangserver2022.global.config.exception.RestApiException;
import com.example.dongnaegoyangserver2022.global.config.exception.error.CommonErrorCode;
import com.example.dongnaegoyangserver2022.global.config.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RestController
public class CatController {

    private final JwtTokenProvider jwtTokenProvider;

    private final CatService catService;
    private final MemberAuthService memberAuthService;

    @PostMapping("/v1/cats")
    public ResponseEntity<Object> createCat(HttpServletRequest servletRequest,
                                            @Valid @RequestBody CatRequest.CreateCatRequest request) {
        log.info("[API] createCat");

        List<String> sexValueList = Arrays.asList("여자", "남자", "모름");
        List<String> ageValueList = Arrays.asList("1살 미만", "2살", "3살", "4살", "5살 이상", "모름");
        if(!sexValueList.contains(request.getSex())){
            throw new CustomException(HttpStatus.BAD_REQUEST, "Sex value should be one of 여자, 남자, 모름");
        }
        else if(!ageValueList.contains(request.getAge())){
            throw new CustomException(HttpStatus.BAD_REQUEST, "Age value should be one of 1살 미만, 2살, 3살, 4살, 5살 이상, 모름");
        }

        Member member = memberAuthService.getMemberByHeader(servletRequest);
        Long catIdx = catService.addCat(member, request.toCatServiceModel());
        return ResponseEntity.ok(new JsonResponse(201, "success createCat", catIdx));
    }

    @GetMapping("/v1/cats")
    public ResponseEntity<Object> getCatList(@Valid @RequestBody CatRequest.GetCatListRequest request,
                                             @RequestParam int page) {
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
                                            @Valid @RequestBody CatRequest.UpdateCatRequest request) {
        log.info("[API] updateCat");
        Member member = memberAuthService.getMemberByHeader(servletRequest);
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
