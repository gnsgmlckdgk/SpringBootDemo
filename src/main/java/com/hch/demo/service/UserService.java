package com.hch.demo.service;

import com.hch.demo.model.entity.User;
import com.hch.demo.model.value.UserValue;
import com.hch.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    /**
     * 유저정보 조회
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * 유저정보 저장
     * @param value
     * @return
     */
    @Transactional
    public User save(UserValue value) {

        User user = User.builder()
                .type(value.getType())
                .email(value.getEmail())
                .birthDate(value.getBirthDate())
                .name(value.getName())
                .password(value.getPassword())
                .phoneNumber(value.getPhoneNumber())
                .sex(value.getSex())
                .build();

        log.info("insert user = {}", user);

        return userRepository.save(user);
    }

    /**
     * 유저정보 변경
     * @param id
     * @param value
     * @return
     */
    @Transactional
    public int patch(long id, UserValue value) {

        Optional<User> oUser = userRepository.findById(id);

        if(oUser.isPresent()) {
            User user = oUser.get();

            if(StringUtils.isNotBlank(value.getType()))
                user.setType(value.getType());
            if(StringUtils.isNotBlank(value.getEmail()))
                user.setEmail(value.getEmail());
            if(StringUtils.isNotBlank(value.getBirthDate()))
                user.setBirthDate(value.getBirthDate());
            if(StringUtils.isNotBlank(value.getName()))
                user.setName(value.getName());
            if(StringUtils.isNotBlank(value.getPassword()))
                user.setPassword(value.getPassword());
            if(StringUtils.isNotBlank(value.getPhoneNumber()))
                user.setPhoneNumber(value.getPhoneNumber());
            if(StringUtils.isNotBlank(value.getSex()))
                user.setSex(value.getSex());

            userRepository.save(user);

            return 1;
        }
        return 0;
    }

    /**
     * 유저정보 삭제
     * @param id
     * @return
     */
    @Transactional
    public int delete(long id) {
        Optional<User> oUser = userRepository.findById(id);
        if(oUser.isPresent()) {
            userRepository.delete(oUser.get());
            return 1;
        }
        return 0;
    }

    /**
     * 유저정보 전체조회
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true)
    public List<User> findAll(Pageable pageable) {
        return userRepository.findAllByDelOrderByIdDesc(false, pageable);
    }

}
