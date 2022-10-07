package com.example.dongnaegoyangserver2022.global.common;

import com.example.dongnaegoyangserver2022.domain.post.dto.PostRequest;
import com.example.dongnaegoyangserver2022.domain.post.model.PostServiceModel;
import org.modelmapper.ModelMapper;

//@Configuration //TODO : ?
public class ModelMapperUtil {
    private static ModelMapper modelMapper = new ModelMapper();
//            .addMapping(PostRequest.CreatePostRequest::getDate, PostServiceModel.CreatePostModel::setTime);

//    @Bean
    public static ModelMapper getModelMapper() {
        return modelMapper;
    }

    public static void setSkipNullEnabled(boolean value){
        modelMapper.getConfiguration().setSkipNullEnabled(value);
    }
}
