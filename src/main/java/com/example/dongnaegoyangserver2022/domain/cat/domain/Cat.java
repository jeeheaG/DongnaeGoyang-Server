package com.example.dongnaegoyangserver2022.domain.cat.domain;

import com.example.dongnaegoyangserver2022.domain.cat.dto.CatResponse;
import com.example.dongnaegoyangserver2022.domain.member.domain.Member;
import com.example.dongnaegoyangserver2022.global.common.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter //ModelMapper 사용에 필요
//@DynamicUpdate
//@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "cat")
@Entity
public class Cat extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long catIdx;

    @Column(nullable = false)
    private int size;

    @Column(nullable = false)
    private int color;

    @Column(nullable = false)
    private int ear;

    @Column(nullable = false)
    private int tail;

    @Column(nullable = false)
    private int whisker;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(nullable = false)
    private String sido;

    @Column(nullable = false)
    private String gugun;

    @Column(nullable = false)
    private String oftenSeen;

    @Column(length = 100, nullable = false)
    private int sex;

    @Column(length = 100, nullable = false)
    private String age;

    @Column(length = 100, nullable = false)
    private String note;

    @Column(length = 100)
    private String tnr;

    @Column(length = 100)
    private String feed;

    @Column(nullable = false)
    private boolean isPhoto;

    @JoinColumn(name = "member_idx", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    //-- 연관관계 설정 메서드 --//
    public void setMember(Member member) {
        this.member = member;
    }


    //-- 값 설정 메서드 --//
    public void setIsPhoto(List<String> photoList) {
        this.isPhoto = photoList != null && !photoList.isEmpty();
    }


    //-- response DTO 변환 메서드 --//
    public static CatResponse.CatListResponseContainer toCatListResponseContainer(List<Cat> catList) {
        ArrayList<CatResponse.CatListResponse> catListResponses = new ArrayList<>();
        for(Cat cat : catList) {
            catListResponses.add(cat.toCatListResponse());
        }
        return CatResponse.CatListResponseContainer.builder()
                .catList(catListResponses)
                .build();
    }

    public CatResponse.CatListResponse toCatListResponse() {
        return CatResponse.CatListResponse.builder()
                .catIdx(this.catIdx)
                .name(this.name)
                .appearance(this.toCatAppearance())
                .build();
    }

    public CatResponse.CatAppearance toCatAppearance() {
        //ModelMapper사용 가능
        return CatResponse.CatAppearance.builder()
                .color(this.color)
                .ear(this.ear)
                .size(this.size)
                .tail(this.tail)
                .whisker(this.whisker)
                .build();
    }
}
