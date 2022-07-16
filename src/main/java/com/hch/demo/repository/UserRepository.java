package com.hch.demo.repository;

import com.hch.demo.model.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 유저 조회
     * @param id must not be {@literal null}.
     * @return
     */
    @Override
    Optional<User> findById(Long id);

    /**
     * 유저정보 전체 조회
     * @param del
     * @param pageable
     * @return
     */
    List<User> findAllByDelOrderByIdDesc(boolean del, Pageable pageable);

}
