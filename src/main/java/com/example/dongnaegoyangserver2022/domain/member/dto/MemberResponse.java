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

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class MemberTownResponse{
        private String sido;
        private String gugun;
    }
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class MemberNicknameResponse{
        private String nickname;
    }
}
