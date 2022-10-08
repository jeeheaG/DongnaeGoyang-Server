package com.example.dongnaegoyangserver2022.domain.member.dto;

import com.example.dongnaegoyangserver2022.domain.cat.dto.CatResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class MemberMyInfoResponse{
        private String kakaoId;
        private String nickname;
        private String loginType;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class MemberMyCatListResponse{
        private List<CatResponse.SimpleCatResponse> catList;
    }
}
