package com.example.dongnaegoyangserver2022.global.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Setter
@Getter //mapper 사용을 위해 필요
@MappedSuperclass // 이 클래스를 상속한 엔티티들이 이 클래스의 필드들도 자신의 컬럼으로 인식하도록 함
@EntityListeners(AuditingEntityListener.class) //Auditing 기능
public abstract class BaseTimeEntity {

    @CreatedDate //Entity 생성 시 시각 자동 저장
    private LocalDateTime createdTime;

    @LastModifiedDate //Entity 값 변경 시 시각 자동 저장
    private LocalDateTime modifiedTime;
}
