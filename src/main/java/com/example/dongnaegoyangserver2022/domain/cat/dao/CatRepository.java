package com.example.dongnaegoyangserver2022.domain.cat.dao;

import com.example.dongnaegoyangserver2022.domain.cat.domain.Cat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



public interface CatRepository extends JpaRepository<Cat, Long> {

    Page<Cat> findBySidoAndGugun(Pageable pageable, String sido, String Gugun);
}
