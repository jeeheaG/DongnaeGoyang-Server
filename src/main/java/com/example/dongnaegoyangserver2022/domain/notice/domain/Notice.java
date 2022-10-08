package com.example.dongnaegoyangserver2022.domain.notice.domain;

import com.example.dongnaegoyangserver2022.domain.member.domain.Member;
import com.example.dongnaegoyangserver2022.domain.notice.dto.NoticeResponse;
import com.example.dongnaegoyangserver2022.global.common.BaseTimeEntity;
import com.example.dongnaegoyangserver2022.global.common.ModelMapperUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@Setter //ModelMapper 사용에 필요
//@DynamicUpdate
//@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "notice")
@Entity
public class Notice extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeIdx;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    //TODO : 추후 기능 만들기
//    //관리자가 탈퇴하더라도 공지사항이 삭제되면 안될 것 같아서 casecade 설정 하지 않음.
//    //set null 로 하고 싶은데 JPA로는 못하고 컬럼들 다 찾아화서 하나하나 null로 바꿔줘야 한다고 함
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Member member;

    public NoticeResponse.GetNoticeListResponse toGetNoticeListResponse(){
        return ModelMapperUtil.getModelMapper().map(this, NoticeResponse.GetNoticeListResponse.class);
    }
}
