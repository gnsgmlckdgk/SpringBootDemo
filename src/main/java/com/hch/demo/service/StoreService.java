package com.hch.demo.service;

import java.util.List;
import java.util.Optional;

import com.hch.demo.model.entity.Store;
import com.hch.demo.model.value.StoreValue;
import com.hch.demo.repository.StoreRepository;
import com.hch.demo.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class StoreService {

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    @Autowired
    public StoreService(StoreRepository storeRepository, UserRepository userRepository) {
        this.storeRepository = storeRepository;
        this.userRepository = userRepository;
    }

    public Optional<Store> findById(Long id) {
        return storeRepository.findById(id);
    }

    @Transactional
    public Store save(StoreValue value) {

        Store store = Store.builder()
                .user(userRepository.findById(value.getUserId()).get())
                .name(value.getName())
                .storeBusiness(value.getStoreBusiness()).build();		// FIXME userId -> user

        return storeRepository.save(store);
    }

    @Transactional
    public void patch(Store store, StoreValue value) {
        if(StringUtils.isNotBlank(value.getName()))
            store.setName(value.getName());
        if(StringUtils.isNotBlank(value.getStoreBusiness()))
            store.setStoreBusiness(value.getStoreBusiness());
        // user
    }

    @Transactional
    public void delete(Store store) {
//		store.setDel(true);
        storeRepository.delete(store);
    }

    public List<Store> findAll(Pageable pageable) {
        return storeRepository.findAllByDelOrderByIdDesc(false, pageable);
    }
}