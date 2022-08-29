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

//TODO : 추후 수정 필요
//        private String sido;
//        private String gugun;
    }
}
