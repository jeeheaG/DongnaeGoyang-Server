package com.example.dongnaegoyangserver2022.domain.image.application;

import com.example.dongnaegoyangserver2022.domain.cat.domain.Cat;
import com.example.dongnaegoyangserver2022.domain.image.dao.ImageRepository;
import com.example.dongnaegoyangserver2022.domain.image.domain.Image;
import com.example.dongnaegoyangserver2022.domain.image.model.ImageServiceModel.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ImageService {
    private final ImageRepository imageRepository;

    public void createImage(CreateModel createModel) {
        List<Image> imageList = createModel.toEntityList();

        imageRepository.saveAll(imageList);
    }
}
