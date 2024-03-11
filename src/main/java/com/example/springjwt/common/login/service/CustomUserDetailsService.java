package com.example.springjwt.common.login.service;

import com.example.springjwt.common.login.dto.CustomUserDetails;
import com.example.springjwt.common.login.repository.UserInfoRepository;
import com.example.springjwt.entity.UserInfoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserInfoRepository userInfoRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

        UserInfoEntity userData = userInfoRepository.findByNAME(id);
        System.out.println(userData);

        // userData가 null이 아니면 CustomUserDetails 객체 반환
        if (userData != null) {
            return new CustomUserDetails(userData);
        } else {
            // userData가 null인 경우 UsernameNotFoundException 예외 던짐
            throw new UsernameNotFoundException("User not found with username: " + id);
        }
    }
}
