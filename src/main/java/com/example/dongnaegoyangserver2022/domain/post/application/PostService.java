package com.example.dongnaegoyangserver2022.domain.post.application;

import com.example.dongnaegoyangserver2022.domain.cat.domain.Cat;
import com.example.dongnaegoyangserver2022.domain.member.domain.Member;
import com.example.dongnaegoyangserver2022.domain.post.dao.PostRepository;
import com.example.dongnaegoyangserver2022.domain.post.domain.Post;
import com.example.dongnaegoyangserver2022.domain.post.dto.PostResponse;
import com.example.dongnaegoyangserver2022.domain.post.model.PostServiceModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

    public final PostRepository postRepository;

    public Long addPost(PostServiceModel.CreatePostModel model, Member member, Cat cat){
        log.info("[SERVICE] addPost");
        Post post = model.toEntity(member, cat);
        return postRepository.save(post).getPostIdx();
    }

    public PostResponse.GetPostListResponseContainer getPostList(Long kakaoId, Long catIdx, PageRequest pageRequest){
        log.info("[SERVICE] getPostList");
        Page<Post> postListPage = postRepository.findByCatIdxPageable(pageRequest, catIdx);
        List<Post> postList = postListPage.getContent();

        List<PostResponse.GetPostListResponse> postListResponses = postList
                .stream()
                .map(post ->
                        post.toGetPostListResponse(kakaoId))
                .collect(Collectors.toList());

        return PostResponse.GetPostListResponseContainer.builder()
                .postList(postListResponses)
                .build();
    }

}
