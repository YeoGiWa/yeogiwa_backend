package com.example.yeogiwa.domain.host;

import com.example.yeogiwa.domain.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HostRepository extends JpaRepository<HostEntity, Long> {
    Boolean existsByUser_Id(Long userId);

    List<HostEntity> findAllByUser(UserEntity user);

    Optional<HostEntity> findAllByUserAndId(UserEntity user, Long id);
}
