package com.hch.demo.repository;


import com.hch.demo.model.entity.Store;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    Optional<List<Store>> findAllByDel(boolean del);

    Optional<Store> findByIdAndDel(Long id, boolean del);

    Optional<Store> findByIdAndUserIdAndDel(long storeId, long userId, boolean del);

    Optional<List<Store>> findAllByUserIdAndDel(long userId, boolean del);

    List<Store> findAllByDelOrderByIdDesc(boolean del, Pageable pageable);
}

