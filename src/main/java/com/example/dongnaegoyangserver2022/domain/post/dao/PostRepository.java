package com.example.dongnaegoyangserver2022.domain.post.dao;

import com.example.dongnaegoyangserver2022.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
