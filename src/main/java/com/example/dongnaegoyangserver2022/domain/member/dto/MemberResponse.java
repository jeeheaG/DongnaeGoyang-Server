package com.example.dongnaegoyangserver2022.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class MemberResponse {

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class LoginResponse {
        private String token;
        private String nickname;
        private String sido;
        private String gugun;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class MemberSimpleResponse{
        private Long kakaoId;
        private String nickname;
    }
}
