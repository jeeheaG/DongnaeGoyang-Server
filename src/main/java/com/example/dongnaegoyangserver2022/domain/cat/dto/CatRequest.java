package com.example.dongnaegoyangserver2022.domain.cat.dto;

import com.example.dongnaegoyangserver2022.domain.cat.model.CatServiceModel;
import com.example.dongnaegoyangserver2022.global.common.ModelMapperUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.domain.PageRequest;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class CatRequest {


    @NoArgsConstructor
    @AllArgsConstructor
    @Data //Equivalent to @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
    public static class CreateCatRequest {

        @NotBlank
        private String name;

        @NotBlank
        @Range(min=1, max=7)
        private int color;

        @NotBlank
        @Range(min=1, max=3)
        private int size;

        @NotBlank
        @Range(min=1, max=3)
        private int ear;

        @NotBlank
        @Range(min=1, max=2)
        private int tail;

        @NotBlank
        @Range(min=1, max=2)
        private int whisker;

        @NotBlank
        private String oftenSeen;

        @NotBlank
        private String sex;

        @NotBlank
        private String age;
        @NotBlank
        private String note;
        @NotBlank
        private String sido;
        @NotBlank
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


    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class GetCatListRequest {

        @NotBlank
        private String sido;

        @NotBlank
        private String gugun;

        public CatServiceModel.GetCatListModel toCatServiceModel(PageRequest pageRequest) {
            CatServiceModel.GetCatListModel model = ModelMapperUtil.getModelMapper().map(this, CatServiceModel.GetCatListModel.class);
            model.setPageRequest(pageRequest);
            return model;
        }
    }


}
