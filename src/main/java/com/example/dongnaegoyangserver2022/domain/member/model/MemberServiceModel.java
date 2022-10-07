package com.example.dongnaegoyangserver2022.domain.member.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class MemberServiceModel {

//    @AllArgsConstructor
//    @NoArgsConstructor
//    @Data
//    public static class MemberBasicModel{
//    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class MemberUpdateTownModel{
        private String sido;
        private String gugun;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class MemberUpdateNicknameModel{
        private String nickname;
    }
}
