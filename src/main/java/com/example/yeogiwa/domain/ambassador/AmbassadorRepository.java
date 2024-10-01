package com.example.yeogiwa.domain.ambassador;

import com.example.yeogiwa.domain.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AmbassadorRepository extends JpaRepository<AmbassadorEntity, UUID> {
    List<AmbassadorEntity> findAllByUser(UserEntity user);

    List<AmbassadorEntity> findAllByEvent_Id(Long eventId);
    Boolean existsByUser_IdAndEvent_Id(Long userId, Long eventId);
    Optional<AmbassadorEntity> findByUser_IdAndEvent_Id(Long userId, Long eventId);
}
