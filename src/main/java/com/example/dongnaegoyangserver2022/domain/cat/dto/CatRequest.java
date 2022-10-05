package com.example.dongnaegoyangserver2022.domain.cat.dto;

import com.example.dongnaegoyangserver2022.domain.cat.model.CatServiceModel;
import com.example.dongnaegoyangserver2022.global.common.ModelMapperUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class CatRequest {


    @NoArgsConstructor
    @AllArgsConstructor
    @Data //Equivalent to @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
    public static class CreateCatRequest {

//        @Autowired
//        private ModelMapper modelMapper;

        private String name;
        private int color;
        private int size;
        private int ear;
        private int tail;
        private int whisker;
        private String oftenSeen;
        private String sex;
        private String age;
        private String note;
        private String sido;
        private String gugun;
        private String tnr = null;
        private String feed = null;
        private List<String> photoList = null;

        public CatServiceModel.CreateCatModel toCatServiceModel() {
            CatServiceModel.CreateCatModel model = ModelMapperUtil.getModelMapper().map(this, CatServiceModel.CreateCatModel.class);
            return model;
        }

    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class UpdateCatRequest {
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

        public CatServiceModel.UpdateCatModel toCatServiceModel() {
            CatServiceModel.UpdateCatModel model = ModelMapperUtil.getModelMapper().map(this, CatServiceModel.UpdateCatModel.class);
            return model;
        }

    }


}
