package com.example.dongnaegoyangserver2022.domain.post.api;

import com.example.dongnaegoyangserver2022.domain.cat.application.CatService;
import com.example.dongnaegoyangserver2022.domain.member.application.MemberAuthService;
import com.example.dongnaegoyangserver2022.domain.post.application.PostService;
import com.example.dongnaegoyangserver2022.domain.post.dto.PostRequest;
import com.example.dongnaegoyangserver2022.domain.post.dto.PostResponse;
import com.example.dongnaegoyangserver2022.global.common.JsonResponse;
import com.example.dongnaegoyangserver2022.global.config.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PostController {
    private final JwtTokenProvider jwtTokenProvider;

    private final PostService postService;

    private final MemberAuthService memberAuthService;

    private final CatService catService;

    @PostMapping("/v1/cats/{catIdx}/posts")
    public ResponseEntity<Object> createPost(HttpServletRequest servletRequest,
                                             @Valid @RequestBody PostRequest.CreatePostRequest request,
                                             @PathVariable Long catIdx){
        log.info("[API] createPost");
        Long postIdx = postService.addPost(
                request.toServiceModel(),
                memberAuthService.getMemberByHeader(servletRequest),
                catService.getCatByIdx(catIdx));

        return ResponseEntity.ok(new JsonResponse(201, "success createPost", postIdx));
    }

    @GetMapping("/v1/cats/{catIdx}/posts")
    public ResponseEntity<Object> getPostList(HttpServletRequest servletRequest,
                                              @PathVariable Long catIdx,
                                              @RequestParam int page){
        log.info("[API] getPostList");
        catService.getCatByIdx(catIdx); //없는 고양이이면 exception 발생
        Long kakaoId = jwtTokenProvider.getUserPKByServlet(servletRequest);
        PageRequest pageRequest = PageRequest.of(page, 30);
        PostResponse.GetPostListResponseContainer container = postService.getPostList(kakaoId, catIdx, pageRequest);
        return ResponseEntity.ok(new JsonResponse(200, "success getPostList", container));
    }

    @DeleteMapping("/v1/posts/{postIdx}")
    public ResponseEntity<Object> deletePost(HttpServletRequest servletRequest,
                                            @PathVariable Long postIdx){
        log.info("[API] deleteCat");
        Long kakaoId = jwtTokenProvider.getUserPKByServlet(servletRequest);
        postService.deletePost(kakaoId, postIdx);
        return ResponseEntity.ok(new JsonResponse(204, "success deletPost", null));
    }

}
