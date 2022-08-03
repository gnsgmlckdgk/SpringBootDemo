package com.hch.demo.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;


@Slf4j
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
@DynamicUpdate
@DynamicInsert
public class User extends BaseEntity {

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

    @Singular("userRoles")
    @JsonIgnoreProperties({"createTimestamp", "updateTimestamp", "del"})
    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    @Where(clause = "del = false")
    private Set<UserRole> userRoles;

}
