package com.example.dongnaegoyangserver2022.repository;

import com.example.dongnaegoyangserver2022.domain.Cat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatRepository extends JpaRepository<Cat, Long> {

}
