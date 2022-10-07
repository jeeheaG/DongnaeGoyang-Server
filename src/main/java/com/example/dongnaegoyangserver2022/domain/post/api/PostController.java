package com.example.dongnaegoyangserver2022.domain.post.api;

import com.example.dongnaegoyangserver2022.domain.cat.application.CatService;
import com.example.dongnaegoyangserver2022.domain.cat.model.CatServiceModel;
import com.example.dongnaegoyangserver2022.domain.member.application.MemberService;
import com.example.dongnaegoyangserver2022.domain.member.domain.Member;
import com.example.dongnaegoyangserver2022.domain.post.application.PostService;
import com.example.dongnaegoyangserver2022.domain.post.dto.PostRequest;
import com.example.dongnaegoyangserver2022.domain.post.dto.PostResponse;
import com.example.dongnaegoyangserver2022.domain.post.model.PostServiceModel;
import com.example.dongnaegoyangserver2022.global.common.JsonResponse;
import com.example.dongnaegoyangserver2022.global.common.ModelMapperUtil;
import com.example.dongnaegoyangserver2022.global.config.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PostController {
    private final JwtTokenProvider jwtTokenProvider;

    private final PostService postService;

    private final MemberService memberService;

    private final CatService catService;

    @PostMapping("/v1/cats/{catIdx}/posts")
    public ResponseEntity<Object> createPost(HttpServletRequest servletRequest,
                                             @RequestBody PostRequest.CreatePostRequest request,
                                             @PathVariable Long catIdx){
        log.info("[API] createPost");
        Long postIdx = postService.addPost(
                request.toServiceModel(),
                memberService.getMemberByHeader(servletRequest),
                catService.getCatByIdx(catIdx));

        return ResponseEntity.ok(new JsonResponse(201, "success createPost", postIdx));
    }

    @GetMapping("/v1/cats/{catIdx}/posts")
    public ResponseEntity<Object> getPostList(HttpServletRequest servletRequest,
                                              @PathVariable Long catIdx,
                                              @RequestParam int page){
        log.info("[API] getPostList");
        Long kakaoId = jwtTokenProvider.getUserPKByServlet(servletRequest);
        PageRequest pageRequest = PageRequest.of(page, 30);
        PostResponse.GetPostListResponseContainer container = postService.getPostList(kakaoId, catIdx, pageRequest);
        return ResponseEntity.ok(new JsonResponse(200, "success getPostList", container));
    }


}
