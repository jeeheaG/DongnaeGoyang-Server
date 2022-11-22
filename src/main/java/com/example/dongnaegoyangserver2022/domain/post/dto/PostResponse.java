package com.example.dongnaegoyangserver2022.domain.post.dto;

import com.example.dongnaegoyangserver2022.domain.member.dto.MemberResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

public class PostResponse {

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class GetPostListResponseContainer{
        private List<GetPostListResponse> postList;


    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class GetPostListResponse{
        private Long postIdx;
//        private ZonedDateTime createdTime;
        private String createdTime;
        private MemberResponse.MemberSimpleResponse writer;
        private Boolean isWriter;
        private String content;
    }
}
