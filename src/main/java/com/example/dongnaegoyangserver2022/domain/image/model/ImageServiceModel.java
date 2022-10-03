package com.example.dongnaegoyangserver2022.domain.image.model;

import com.example.dongnaegoyangserver2022.domain.cat.domain.Cat;
import com.example.dongnaegoyangserver2022.domain.image.domain.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public class ImageServiceModel {

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class CreateImageModel {
        private List<String> photoList;
        private Cat cat;

        public List<Image> toEntityList() {
            ArrayList<Image> imageList = new ArrayList<>();
             for (String photo : photoList) {
                 imageList.add(Image.builder()
                                     .url(photo)
                                     .cat(this.cat)
                                     .build());
             }
             return imageList;
        }
    }


}
