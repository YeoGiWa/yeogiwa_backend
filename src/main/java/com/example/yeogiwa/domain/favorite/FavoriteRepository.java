package com.example.yeogiwa.domain.favorite;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface FavoriteRepository extends JpaRepository<FavoriteEntity, UUID> {
    Optional<FavoriteEntity> findByUser_IdAndEvent_Id(Long userId, Long eventId);
    Optional<List<FavoriteEntity>> findAllByUser_IdAndEvent_IdIn(Long userId, List<Long> eventIds);
    void deleteAllByUser_IdAndEvent_Id(Long userId, Long eventId);
}