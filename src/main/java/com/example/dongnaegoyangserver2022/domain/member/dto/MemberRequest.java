package com.example.dongnaegoyangserver2022.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class MemberRequest {


    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class LoginRequest {
        private String loginType;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class SignUpRequest {
        private String si;
        private String gu;
        private String dong;

//TODO : 추후 수정 필요
//        private String sido;
//        private String gugun;
    }
}
