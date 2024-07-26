package com.example.yeogiwa.repository;

import com.example.yeogiwa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Boolean existsById(UUID id);
    Boolean existsByEmail(String email);

    UserEntity findById(UUID id);
    UserEntity findByEmail(String email);
}
