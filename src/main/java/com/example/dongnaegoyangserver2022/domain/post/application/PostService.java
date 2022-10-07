package com.example.dongnaegoyangserver2022.domain.post.application;

import com.example.dongnaegoyangserver2022.domain.cat.domain.Cat;
import com.example.dongnaegoyangserver2022.domain.member.domain.Member;
import com.example.dongnaegoyangserver2022.domain.post.dao.PostRepository;
import com.example.dongnaegoyangserver2022.domain.post.domain.Post;
import com.example.dongnaegoyangserver2022.domain.post.model.PostServiceModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

    public final PostRepository postRepository;

    public Long addPost(PostServiceModel.CreatePostModel model, Member member, Cat cat){
        Post post = model.toEntity(member, cat);
        return postRepository.save(post).getPostIdx();
    }


}
