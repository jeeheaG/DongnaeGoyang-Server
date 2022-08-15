package com.example.dongnaegoyangserver2022.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class CatRequest {

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class createRequest{
        private Long catIdx;
    }
}
