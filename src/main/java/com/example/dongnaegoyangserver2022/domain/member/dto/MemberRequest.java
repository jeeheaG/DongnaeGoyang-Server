package com.example.dongnaegoyangserver2022.domain.member.dto;

import com.example.dongnaegoyangserver2022.domain.member.model.MemberServiceModel;
import com.example.dongnaegoyangserver2022.global.common.ModelMapperUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

public class MemberRequest {


    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class LoginRequest {
        @NotBlank
        private String loginType;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class SignUpRequest {

        @NotBlank
        private String sido;

        @NotBlank
        private String gugun;

        @NotBlank
        private String loginType;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class UpdateMemberTownRequest {

        @NotBlank
        private String sido;

        @NotBlank
        private String gugun;

        public MemberServiceModel.MemberUpdateTownModel toServiceModel(){
            return ModelMapperUtil.getModelMapper().map(this, MemberServiceModel.MemberUpdateTownModel.class);
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class UpdateMemberNicknameRequest {

        @NotBlank //Validation
        private String nickname;

        public MemberServiceModel.MemberUpdateNicknameModel toServiceModel(){
            return ModelMapperUtil.getModelMapper().map(this, MemberServiceModel.MemberUpdateNicknameModel.class);
        }
    }

}
