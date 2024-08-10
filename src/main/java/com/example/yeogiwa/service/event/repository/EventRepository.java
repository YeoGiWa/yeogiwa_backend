package com.example.yeogiwa.service.event.repository;

import com.example.yeogiwa.service.event.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, String> {
    List<EventEntity> findAllByIdIn(List<String> ids);
}