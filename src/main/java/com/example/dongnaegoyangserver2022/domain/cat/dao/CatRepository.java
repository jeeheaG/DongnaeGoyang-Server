package com.example.dongnaegoyangserver2022.domain.cat.dao;

import com.example.dongnaegoyangserver2022.domain.cat.domain.Cat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatRepository extends JpaRepository<Cat, Long> {

}
