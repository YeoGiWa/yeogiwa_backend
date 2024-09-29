package com.example.yeogiwa.domain.ambassador;

import com.example.yeogiwa.domain.user.UserEntity;
import com.example.yeogiwa.domain.event.OldEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AmbassadorRepository extends JpaRepository<AmbassadorEntity, UUID> {
    List<AmbassadorEntity> findAllByUser(UserEntity user);

    List<AmbassadorEntity> findAllByEvent_Id(Long eventId);
}
