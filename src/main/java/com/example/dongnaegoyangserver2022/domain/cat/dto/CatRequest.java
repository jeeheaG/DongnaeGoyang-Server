package com.example.dongnaegoyangserver2022.domain.cat.dto;

import com.example.dongnaegoyangserver2022.domain.cat.model.CatServiceModel;
import com.example.dongnaegoyangserver2022.domain.image.model.ImageServiceModel;
import com.example.dongnaegoyangserver2022.global.common.ModelMapperUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class CatRequest {


    @NoArgsConstructor
    @AllArgsConstructor
    @Data //Equivalent to @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
    public static class CreateRequest {

//        @Autowired
//        private ModelMapper modelMapper;

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

        public CatServiceModel.CreateModel toCatServiceModel() {
            CatServiceModel.CreateModel model = ModelMapperUtil.getModelMapper().map(this, CatServiceModel.CreateModel.class);
            return model;
        }

    }


}
