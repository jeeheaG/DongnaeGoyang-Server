package com.example.dongnaegoyangserver2022.domain.post.dto;

import com.example.dongnaegoyangserver2022.domain.post.model.PostServiceModel;
import com.example.dongnaegoyangserver2022.global.common.ModelMapperUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.TypeMap;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class PostRequest {

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class CreatePostRequest{

        @NotBlank
        private String content;

        public PostServiceModel.CreatePostModel toServiceModel(){
//            //TODO : 이름이 다른 필드 맵핑 적용.. 더 짧고 간단한 방법 없어??
//            TypeMap typeMap = ModelMapperUtil.getModelMapper()
//                    .createTypeMap(PostRequest.CreatePostRequest.class, PostServiceModel.CreatePostModel.class)
//                    .addMapping(PostRequest.CreatePostRequest::getDate, PostServiceModel.CreatePostModel::setTime);
//
//            return (PostServiceModel.CreatePostModel) typeMap.map(this);

            return ModelMapperUtil.getModelMapper().map(this, PostServiceModel.CreatePostModel.class);
        }
    }
}
