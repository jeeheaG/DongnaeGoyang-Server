package com.example.dongnaegoyangserver2022.domain.cat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class CatResponse {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class CatListResponseContainer {
        private List<CatListResponse> catList;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class CatListResponse {
        private Long catIdx;
        private String name;
        private CatAppearance appearance;


    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class CatAppearance {
        private int size;
        private int ear;
        private int color;
        private int tail;
        private int whisker;
    }

}
