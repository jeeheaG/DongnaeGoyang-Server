package com.example.dongnaegoyangserver2022.domain.cat.api;

import com.example.dongnaegoyangserver2022.domain.cat.application.CatService;
import com.example.dongnaegoyangserver2022.domain.cat.dto.CatRequest;
import com.example.dongnaegoyangserver2022.domain.cat.dto.CatResponse;
import com.example.dongnaegoyangserver2022.global.common.JsonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CatController {

    private final CatService catService;

    @PostMapping("/v1/cats")
    public ResponseEntity<Object> createCat(HttpServletRequest servletRequest,
                                            @RequestBody CatRequest.CreateCatRequest request) {
        Long catIdx = catService.addCat(servletRequest, request.toCatServiceModel());
        return ResponseEntity.ok(new JsonResponse(201, "success createCat", catIdx));
    }

    @GetMapping("/v1/cats")
    public ResponseEntity<Object> getCatList(HttpServletRequest servletRequest, @RequestParam int page) {
        PageRequest pageRequest = PageRequest.of(page, 20); // Pageable : page와 size
        CatResponse.CatListResponseContainer catListResponseContainer = catService.getCatList(servletRequest, pageRequest);
        return ResponseEntity.ok(new JsonResponse(200, "success getCatList", catListResponseContainer));
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
