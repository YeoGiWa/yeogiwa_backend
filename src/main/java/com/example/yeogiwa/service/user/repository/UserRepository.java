package com.example.yeogiwa.service.user.repository;

import com.example.yeogiwa.service.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Boolean existsById(UUID id);
    Boolean existsByEmail(String email);

    UserEntity findById(UUID id);
    UserEntity findByEmail(String email);
}
