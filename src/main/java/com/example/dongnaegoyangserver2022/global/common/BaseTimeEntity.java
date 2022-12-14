package com.example.dongnaegoyangserver2022.global.common;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Slf4j
@Setter
@Getter //mapper 사용을 위해 필요
@MappedSuperclass // 이 클래스를 상속한 엔티티들이 이 클래스의 필드들도 자신의 컬럼으로 인식하도록 함
@EntityListeners(AuditingEntityListener.class) //Auditing 기능
public abstract class BaseTimeEntity {

    private ZonedDateTime createdTime;

    private ZonedDateTime modifiedTime;

    @PrePersist
    public void prePersist() {
        this.createdTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        this.modifiedTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
    }

    @PreUpdate
    public void preUpdate() {
        this.modifiedTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
    }

//
//    @CreatedDate //Entity 생성 시 시각 자동 저장
//    private LocalDateTime createdTime;
//
//    @LastModifiedDate //Entity 값 변경 시 시각 자동 저장
//    private LocalDateTime modifiedTime;

}
