package com.example.yeogiwa.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Boolean existsByEmail(String email);

    Optional<UserEntity> findById(Long id);
    UserEntity findByEmail(String email);
    UserEntity findByEmailOrOauth2Id(String email, String oauth2Id);
    Optional<UserEntity> findByOauth2Id(String oauth2Id);
}
