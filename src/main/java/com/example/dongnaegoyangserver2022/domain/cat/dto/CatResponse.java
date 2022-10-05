package com.example.dongnaegoyangserver2022.domain.cat.dto;

import com.example.dongnaegoyangserver2022.domain.member.dto.MemberResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class CatDetailResponse {
        private Long catIdx;
        private Boolean isWriter;
        private String name;
        private String sex;
        private String age;
        private String place;
        private String oftenSeen;
        private String note;
        private CatAppearance appearance;
        private List<String> photoList = null;
        private String tnr = null;
        private String feed = null;
        private LocalDateTime modifiedTime;
        private MemberResponse.MemberSimpleResponse writer;
        private List<OtherCatResponse> otherCatList;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class CatDetailBasicResponse {
        private Long catIdx;
        private Boolean isWriter;
        private String name;
        private String sex;
        private String age;
        private String place;
        private String oftenSeen;
        private String note;
        private CatAppearance appearance;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class CatDetailAdditionalResponse {
        private Long catIdx;
        private List<String> photoList = null;
        private String tnr = null;
        private String feed = null;
        private LocalDateTime modifiedTime;
        private MemberResponse.MemberSimpleResponse writer;
        private List<OtherCatResponse> otherCatList;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class OtherCatResponse {
        private Long catIdx;
        private int size;
        private int ear;
        private int color;
        private int tail;
        private int whisker;
    }

}
