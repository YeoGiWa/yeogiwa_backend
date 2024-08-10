package com.example.yeogiwa.service.ambassador.repository;

import com.example.yeogiwa.entity.UserEntity;
import com.example.yeogiwa.service.ambassador.entity.AmbassadorEntity;
import com.example.yeogiwa.service.event.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AmbassadorRepository extends JpaRepository<AmbassadorEntity, UUID> {
    List<AmbassadorEntity> findAllByUser(UserEntity user);

    List<AmbassadorEntity> findAllByEvent(EventEntity event);
}
