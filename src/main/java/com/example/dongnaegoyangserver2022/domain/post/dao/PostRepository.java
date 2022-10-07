package com.example.dongnaegoyangserver2022.domain.post.dao;

import com.example.dongnaegoyangserver2022.domain.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "select p from Post p where p.cat.catIdx = :catIdx")
    Page<Post> findByCatIdxPageable(Pageable pageable, @Param("catIdx") Long catIdx);

}
