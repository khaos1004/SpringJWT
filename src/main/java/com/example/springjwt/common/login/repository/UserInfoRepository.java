package com.example.springjwt.common.login.repository;

import com.example.springjwt.entity.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfoEntity, Integer> {

    UserInfoEntity findByNAME(String name);


}
