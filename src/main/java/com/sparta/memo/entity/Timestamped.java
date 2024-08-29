package com.sparta.memo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass // 엔티티 클래스 상속(이 클래스를 상속받으면 부모자식처럼 필드 사용가능)
@EntityListeners(AuditingEntityListener.class) //이 클래스의 Auditing 기능 넣어줌
public abstract class Timestamped {

    @CreatedDate // 최초 생성 시간이 자동으로 저장 됨
    @Column(updatable = false) // 업데이트는 안됨(최초 생성시간만 변경되어야함으로)
    @Temporal(TemporalType.TIMESTAMP) // 날짜,시간 데이터를 매핑할때 사용함 (enum)
    private LocalDateTime createdAt;

    @LastModifiedDate // 마지막 변경된 시간 저장
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime modifiedAt;
}