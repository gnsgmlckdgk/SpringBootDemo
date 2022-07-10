package com.hch.demo.model.entity;

import com.hch.demo.enums.TimeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;


@Slf4j
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
@DynamicUpdate
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, columnDefinition = "INT(11)")
    private Long id;

    @Column(nullable = false, length = 1, columnDefinition = "CHAR(1) DEFAULT '0'")
    private String type;

    @Comment("이메일")
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Comment("이름")
    @Column(nullable = false, length = 50)
    private String name;

    @Comment("성별")
    @Column(nullable = false, length = 1, columnDefinition = "CHAR(1) DEFAULT '1'")
    private String sex;

    @Comment("생일")
    @Column(nullable = false, length = 6)
    private String birthDate;

    @Comment("전화번호")
    @Column(nullable = false, length = 20)
    private String phoneNumber;

    @Comment("비밀번호")
    @Column(nullable = false, length = 150)
    private String password;

    @Comment("삭제여부")
    @Column(nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0", length = 1)
    private boolean del;

    @Comment("생성시간")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createTimestamp;

    @Comment("변경시간")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    protected Date updateTimestamp;


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
