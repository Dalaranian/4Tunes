package com.multi.fourtunes.model.jpa.repository;

import com.multi.fourtunes.model.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByUserId(String userId);

    UserEntity findByUserNo(Integer userNo);
}
