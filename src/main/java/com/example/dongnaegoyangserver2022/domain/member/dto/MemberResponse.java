package com.example.dongnaegoyangserver2022.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class MemberResponse {

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class loginResponse{
        private String token;
        private String nickname;
        private String sido;
        private String gugun;
    }
}
