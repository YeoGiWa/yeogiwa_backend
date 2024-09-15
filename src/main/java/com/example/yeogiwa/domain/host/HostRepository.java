package com.example.yeogiwa.domain.host;

import com.example.yeogiwa.domain.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface HostRepository extends JpaRepository<HostEntity, Long> {
    Optional<HostEntity> findByUser(UserEntity user);
}
