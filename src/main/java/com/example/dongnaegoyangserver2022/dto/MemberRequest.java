package com.example.dongnaegoyangserver2022.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class MemberRequest {

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class signUpRequest{
        private String si;
        private String gu;
        private String dong;
    }
}
