package com.example.yeogiwa.domain.event;

import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;



public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Long> {

    List<FavoriteEntity> findByUserId(UUID userId);

    Optional<FavoriteEntity> findByUserIdAndEvent(UUID userId, EventEntity event);

    @Modifying
    @Query("DELETE FROM FavoriteEntity f WHERE f.event = :event AND f.user.id = :userId")
    void deleteByUserIdAndEvent(@Param("userId") UUID userId, @Param("event") EventEntity event);
}