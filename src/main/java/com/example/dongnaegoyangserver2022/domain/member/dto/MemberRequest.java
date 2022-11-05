package com.example.dongnaegoyangserver2022.domain.member.dto;

import com.example.dongnaegoyangserver2022.domain.member.model.MemberServiceModel;
import com.example.dongnaegoyangserver2022.global.common.ModelMapperUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
        private String sido;
        private String gugun;
        private String loginType;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class UpdateMemberTownRequest {
        private String sido;
        private String gugun;

        public MemberServiceModel.MemberUpdateTownModel toServiceModel(){
            return ModelMapperUtil.getModelMapper().map(this, MemberServiceModel.MemberUpdateTownModel.class);
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class UpdateMemberNicknameRequest {

        @NotBlank//(message = "nickname field cannot be blank") //Validation
        private String nickname;

        public MemberServiceModel.MemberUpdateNicknameModel toServiceModel(){
            return ModelMapperUtil.getModelMapper().map(this, MemberServiceModel.MemberUpdateNicknameModel.class);
        }
    }

}
