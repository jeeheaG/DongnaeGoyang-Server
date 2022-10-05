package com.example.dongnaegoyangserver2022.global.common;

import org.modelmapper.ModelMapper;

//@Configuration //TODO : ?
public class ModelMapperUtil {
    public static ModelMapper modelMapper = new ModelMapper();

//    @Bean
    public static ModelMapper getModelMapper() {
        return modelMapper;
    }

    public static void setSkipNullEnabled(boolean value){
        modelMapper.getConfiguration().setSkipNullEnabled(value);
    }
}
