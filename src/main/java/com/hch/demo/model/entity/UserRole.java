package com.hch.demo.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Slf4j
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicUpdate
public class UserRole extends BaseEntity implements GrantedAuthority {

    @Comment("user아이디")
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_USER_ROLE_USER"))
    private User user;

    @Comment("권한명")
    @Column(name="role_name", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private RoleType roleName;

    public enum RoleType {
        ROLE_ADMIN, ROLE_VIEW
    }

    @JsonIgnore
    @Override
    public String getAuthority() {
        return this.roleName.name();
    }
}
