package com.example.dongnaegoyangserver2022.domain.cat.model;

import com.example.dongnaegoyangserver2022.domain.cat.domain.Cat;
import com.example.dongnaegoyangserver2022.global.common.ModelMapperUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class CatServiceModel {

//    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data //Equivalent to @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
    public static class CreateModel {
        private String name;
        private int color;
        private int size;
        private int ear;
        private int tail;
        private int whisker;
        private String mainHabitat;
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
}
