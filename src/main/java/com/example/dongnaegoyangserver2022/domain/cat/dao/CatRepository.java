package com.example.dongnaegoyangserver2022.domain.cat.dao;

import com.example.dongnaegoyangserver2022.domain.cat.domain.Cat;
import com.example.dongnaegoyangserver2022.domain.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CatRepository extends JpaRepository<Cat, Long> {

    Page<Cat> findBySidoAndGugun(Pageable pageable, String sido, String Gugun);

    //random 값 추출을 위해 native query 사용
    @Query(value = "select * from cat where cat_idx != :currentCatIdx and sido = :sido and gugun = :gugun order by RAND() limit 5", nativeQuery = true)
    List<Cat> findOther5BySidoAndGugunRandom(@Param("sido") String sido, @Param("gugun")  String Gugun, @Param("currentCatIdx") Long currentCatIdx);

    Page<Cat> findByMember(Pageable pageable, Member member);

}
