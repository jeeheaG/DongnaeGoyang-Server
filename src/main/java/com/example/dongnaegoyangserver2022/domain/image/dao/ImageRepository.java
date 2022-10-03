package com.example.dongnaegoyangserver2022.domain.image.dao;


import com.example.dongnaegoyangserver2022.domain.image.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
