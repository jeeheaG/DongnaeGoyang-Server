package com.example.dongnaegoyangserver2022.domain.cat.model;

import com.example.dongnaegoyangserver2022.domain.cat.domain.Cat;
import com.example.dongnaegoyangserver2022.global.common.ModelMapperUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class CatServiceModel {

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class CreateCatModel {
        private String name;
        private int color;
        private int size;
        private int ear;
        private int tail;
        private int whisker;
        private String sex;
        private String age;
        private String note;
        private String sido;
        private String gugun;
        private String oftenSeen;
        private String tnr = null;
        private String feed = null;
        private List<String> photoList = null;

        public Cat toEntity() {
            Cat cat = ModelMapperUtil.getModelMapper().map(this, Cat.class);
            return cat;
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class UpdateCatModel {
        private String name = null;
        private Integer color = null;
        private Integer size = null;
        private Integer ear = null;
        private Integer tail = null;
        private Integer whisker = null;
        private String oftenSeen = null;
        private String sex = null;
        private String age = null;
        private String note = null;
        private String sido = null;
        private String gugun = null;
        private String tnr = null;
        private String feed = null;
        private List<Long> deletePhotoList = null;
        private List<String> createPhotoList = null;
    }
}
