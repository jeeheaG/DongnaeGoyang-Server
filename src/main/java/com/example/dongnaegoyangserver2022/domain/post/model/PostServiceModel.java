package com.example.dongnaegoyangserver2022.domain.post.model;

import com.example.dongnaegoyangserver2022.domain.cat.domain.Cat;
import com.example.dongnaegoyangserver2022.domain.member.domain.Member;
import com.example.dongnaegoyangserver2022.domain.post.domain.Post;
import com.example.dongnaegoyangserver2022.global.common.ModelMapperUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class PostServiceModel {

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class CreatePostModel {
        private String content;

        public Post toEntity(Member member, Cat cat){
            Post post =  ModelMapperUtil.getModelMapper().map(this, Post.class);
            post.setMember(member);
            post.setCat(cat);
            return post;
        }
    }

}
