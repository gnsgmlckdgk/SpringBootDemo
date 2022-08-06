package com.hch.demo.repository;

import com.hch.demo.model.entity.User;
import com.hch.demo.model.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    Set<UserRole> findByUserAndDel(User user, boolean del);

}
