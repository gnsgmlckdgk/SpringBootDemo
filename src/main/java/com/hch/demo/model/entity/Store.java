package com.hch.demo.model.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hch.demo.enums.TimeEnum;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
@Entity(name = "store")
@DynamicUpdate
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, columnDefinition = "INT(11)")
    private Long id;

    @Comment("user아이디")
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_STORE_USER"))
    private User user;

    @Comment("이름")
    @Column(nullable=false, length = 100)
    private String name;

    @Comment("가게업무")
    @Column(length = 30)
    private String storeBusiness;

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

    @Builder
    private Store(User user, String name, String storeBusiness) {
        this.user = user;
        this.name = name;
        this.storeBusiness = storeBusiness;
    }

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
