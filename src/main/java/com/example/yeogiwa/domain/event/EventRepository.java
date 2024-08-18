package com.example.yeogiwa.domain.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, String> {
    List<EventEntity> findAllByIdIn(List<Long> ids);
}