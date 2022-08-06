package com.hch.demo.service;

import com.hch.demo.model.entity.SecurityUser;
import com.hch.demo.model.entity.User;
import com.hch.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class SecurityUserService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * 유저정보 조회(이메일)
     * @param email the username identifying the user whose data is required.
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> oUser = userRepository.findWithUserRolesByEmailAndDel(email, false);
        if(!oUser.isPresent()) {
            log.info("존재하지 않는 아이디입니다: " + email);
            throw new UsernameNotFoundException(email);
        }
        return new SecurityUser(oUser.get());
    }
}
