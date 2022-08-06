package com.hch.demo.model.entity;

import com.hch.demo.enums.TimeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;


@Slf4j
@Getter @Setter
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, columnDefinition = "INT(11)")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createTimestamp;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    protected Date updateTimestamp;

    @Column(nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0", length = 1)
    private boolean del;

    @PrePersist
    protected void onCreate() {
        // hibernate로 insert 할때는 UTC라 9시간 더해줌(hibernate UTC 설정안하면 조회 시 계속 9시간 전으로 응답함)
        createTimestamp = Timestamp.valueOf(LocalDateTime.now().plusHours(TimeEnum.TZ_ASIA_SEOUAL.getTimeDiff()));
        log.info("createTimestamp = {}", createTimestamp);
    }

    @PreUpdate
    protected void onUpdate() {
        // hibernate로 update 할때는 UTC라 9시간 더해줌(hibernate UTC 설정안하면 조회 시 계속 9시간 전으로 응답함)
        updateTimestamp = Timestamp.valueOf(LocalDateTime.now().plusHours(TimeEnum.TZ_ASIA_SEOUAL.getTimeDiff()));
        log.info("updateTimestamp = {}", updateTimestamp);
    }

}
