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
import java.time.Duration;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

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
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        ZonedDateTime created = this.getCreatedTime();

        //년도가 같고 해당 년도 안에서의 지나온 날 수가 같다면, 같은 날짜임
        if(created.getYear() == now.getYear() && created.getDayOfYear() == now.getDayOfYear()) {
            getPostListResponse.setCreatedTime(created.format(DateTimeFormatter.ofPattern("HH:mm"))); // 시:분
        }
        else { //다른 날짜임
            getPostListResponse.setCreatedTime(created.format(DateTimeFormatter.ofPattern("MM/dd"))); // 월/일
        }

        getPostListResponse.setIsWriter(this.member.getKakaoId().equals(kakaoId));
        getPostListResponse.setWriter(this.member.toMemberSimpleResponse());
        return getPostListResponse;
    }

    //-- function --//
    public boolean checkIsWriter(Long kakaoId){
        return member.getKakaoId().equals(kakaoId);
    }
}
