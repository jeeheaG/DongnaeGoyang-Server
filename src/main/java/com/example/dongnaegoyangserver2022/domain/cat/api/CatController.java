package com.example.dongnaegoyangserver2022.domain.cat.api;

import com.example.dongnaegoyangserver2022.global.common.JsonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CatController {
//    private final CatService catService;



    @GetMapping("/v1/cats/hello")
    public ResponseEntity<Object> hello() {
        return ResponseEntity.ok(new JsonResponse(200, "hello 고영", "test"));
    }

    @GetMapping("/v1/cats/hello-string")
    public String helloString() {
        return "hello";
    }



}
