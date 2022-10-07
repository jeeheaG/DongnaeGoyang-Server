package com.example.dongnaegoyangserver2022.domain.post.domain;

import com.example.dongnaegoyangserver2022.domain.cat.domain.Cat;
import com.example.dongnaegoyangserver2022.domain.member.domain.Member;
import com.example.dongnaegoyangserver2022.domain.post.dto.PostResponse;
import com.example.dongnaegoyangserver2022.global.common.BaseTimeEntity;
import com.example.dongnaegoyangserver2022.global.common.ModelMapperUtil;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
//@DynamicUpdate
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "post")
@Entity
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postIdx;

    @Column(nullable = false)
    private String content;

    @JoinColumn(name = "cat_idx", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Cat cat;

    @JoinColumn(name = "member_idx", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    //-- method to DTO --//
    public PostResponse.GetPostListResponse toGetPostListResponse(Long kakaoId){
        PostResponse.GetPostListResponse getPostListResponse = ModelMapperUtil.getModelMapper().map(this, PostResponse.GetPostListResponse.class);
        getPostListResponse.setIsWriter(this.member.getKakaoId().equals(kakaoId));
        getPostListResponse.setWriter(this.member.toMemberSimpleResponse());
        return getPostListResponse;
    }

    //-- function --//
    public boolean checkIsWriter(Long kakaoId){
        return member.getKakaoId().equals(kakaoId);
    }
}
