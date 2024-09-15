package com.example.yeogiwa.domain.event;

import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;



public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Long> {

    List<FavoriteEntity> findByUserId(UUID userId);

    Optional<FavoriteEntity> findByUserIdAndEventId(UUID userId, String eventId);

    @Modifying
    @Query("DELETE FROM FavoriteEntity f WHERE f.eventId = :eventId AND f.user.id = :userId")
    void deleteByUserIdAndEventId(@Param("userId") UUID userId, @Param("eventId") String eventId);
}