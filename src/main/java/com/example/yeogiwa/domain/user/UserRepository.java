package com.example.yeogiwa.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Boolean existsByEmail(String email);

    Optional<UserEntity> findById(Long id);
    UserEntity findByEmail(String email);
    Optional<UserEntity> findByOauth2Id(String oauth2Id);
}
