package com.hch.demo.model.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
@Entity(name = "store")
@DynamicUpdate
public class Store extends BaseEntity {

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

    @Builder
    private Store(User user, String name, String storeBusiness) {
        this.user = user;
        this.name = name;
        this.storeBusiness = storeBusiness;
    }

}
