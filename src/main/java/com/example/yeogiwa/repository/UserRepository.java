package com.example.yeogiwa.repository;

import com.example.yeogiwa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Boolean existsById(int id);
    Boolean existsByEmail(String email);

    UserEntity findById(int id);
    UserEntity findByEmail(String email);
}
