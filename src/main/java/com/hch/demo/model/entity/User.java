package com.hch.demo.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;


@Slf4j
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
@DynamicUpdate
@DynamicInsert
public class User extends BaseEntity {

    @ColumnDefault(value = "0")
    @Column(nullable = false, length = 1)
    private String type;

    @Comment("이메일")
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Comment("이름")
    @Column(nullable = false, length = 50)
    private String name;

    @ColumnDefault(value = "1")
    @Comment("성별")
    @Column(nullable = false, length = 1)
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

    @Singular("userRoles")
    @JsonIgnoreProperties({"createTimestamp", "updateTimestamp", "del"})
    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    @Where(clause = "del = false")
    private Set<UserRole> userRoles;

}
