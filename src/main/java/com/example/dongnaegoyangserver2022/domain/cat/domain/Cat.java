package com.example.dongnaegoyangserver2022.domain.cat.domain;

import com.example.dongnaegoyangserver2022.domain.cat.dto.CatResponse;
import com.example.dongnaegoyangserver2022.domain.image.domain.Image;
import com.example.dongnaegoyangserver2022.domain.member.domain.Member;
import com.example.dongnaegoyangserver2022.global.common.BaseTimeEntity;
import com.example.dongnaegoyangserver2022.global.common.ModelMapperUtil;
import lombok.*;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public void setIsPhoto(boolean isPhoto) {
        this.isPhoto = isPhoto;
    }
    public void setIsPhoto(List<String> photoList) {
        this.isPhoto = photoList != null && !photoList.isEmpty();
    }


    //-- response DTO 변환 메서드 --//
//    public static CatResponse.CatListResponseContainer toCatListResponseContainer(List<Cat> catList) {
//        ArrayList<CatResponse.CatListResponse> catListResponses = new ArrayList<>();
//        for(Cat cat : catList) {
//            catListResponses.add(cat.toCatListResponse());
//        }
//        return CatResponse.CatListResponseContainer.builder()
//                .catList(catListResponses)
//                .build();
//    }

//    public List<CatResponse.OtherCatResponse> toOtherCatResponse(List<Cat> catList) {
//        ArrayList<CatResponse.OtherCatResponse> otherCatResponses = new ArrayList<>();
//        for(Cat cat : catList) {
//            otherCatResponses.add(ModelMapperUtil.getModelMapper().map(cat, CatResponse.OtherCatResponse.class));
//        }
//        return otherCatResponses;
//    }


    public CatResponse.CatListResponse toCatListResponse() {
        return CatResponse.CatListResponse.builder()
                .catIdx(this.catIdx)
                .name(this.name)
                .appearance(toCatAppearance())
                .build();
    }


    public CatResponse.CatDetailResponse toCatDetailResponse(Long kakaoId, List<Image> imageList, List<Cat> otherCatList) {
        CatResponse.CatDetailResponse catDetailResponse = ModelMapperUtil.getModelMapper().map(this, CatResponse.CatDetailResponse.class);

        catDetailResponse.setHealthInfoCount(countHealthInfo());
        catDetailResponse.setIsWriter(this.member.getKakaoId().equals(kakaoId));
        catDetailResponse.setPlace(this.sido + " " + this.gugun);
        catDetailResponse.setAppearance(toCatAppearance());
        catDetailResponse.setPhotoList(imageList.stream().map(image -> image.toStringSimpleResponse()).collect(Collectors.toList()));
        catDetailResponse.setWriter(this.member.toMemberSimpleResponse());
        catDetailResponse.setOtherCatList(toOtherCatResponse(otherCatList));

        return catDetailResponse;
    }

    public CatResponse.CatDetailBasicResponse toCatDetailBasicResponse(Long kakaoId) {
        CatResponse.CatDetailBasicResponse catDetailBasicResponse = ModelMapperUtil.getModelMapper().map(this, CatResponse.CatDetailBasicResponse.class);

        catDetailBasicResponse.setIsWriter(this.member.getKakaoId().equals(kakaoId));
        catDetailBasicResponse.setPlace(this.sido + " " + this.gugun);
        catDetailBasicResponse.setAppearance(toCatAppearance());

        return catDetailBasicResponse;
    }

    public CatResponse.CatDetailAdditionalResponse toCatDetailAdditionalResponse(List<Image> imageList, List<Cat> otherCatList) {
        CatResponse.CatDetailAdditionalResponse catDetailAdditionalResponse = ModelMapperUtil.getModelMapper().map(this, CatResponse.CatDetailAdditionalResponse.class);

        catDetailAdditionalResponse.setPhotoList(imageList.stream().map(image -> image.toStringSimpleResponse()).collect(Collectors.toList()));
        catDetailAdditionalResponse.setHealthInfoCount(countHealthInfo());
        catDetailAdditionalResponse.setWriter(this.member.toMemberSimpleResponse());
        catDetailAdditionalResponse.setOtherCatList(toOtherCatResponse(otherCatList));

        return catDetailAdditionalResponse;
    }

    //-- function --//
    public CatResponse.CatAppearance toCatAppearance() {
        return ModelMapperUtil.getModelMapper().map(this, CatResponse.CatAppearance.class);
    }

     public List<CatResponse.OtherCatResponse> toOtherCatResponse(List<Cat> catList) {
         return catList
                 .stream().map(cat ->
                         ModelMapperUtil.getModelMapper().map(cat, CatResponse.OtherCatResponse.class)
                 )
                 .collect(Collectors.toList());
    }

    private int countHealthInfo() {
        int count = 0;
        if(this.tnr != null) {
            count += 1;
        }
        if(this.feed != null) {
            count += 1;
        }
        return count;
    }
}
