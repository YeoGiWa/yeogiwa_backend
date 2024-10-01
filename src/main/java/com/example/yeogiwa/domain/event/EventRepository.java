package com.example.yeogiwa.domain.event;

import com.example.yeogiwa.domain.event.dto.EventDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {
    List<EventEntity> findAllByHost_Id(Long hostId);

    List<EventEntity> findAllByHost_IdAndUpperEvent_Id(Long hostId, Long upperEventId);
}
