package com.example.yeogiwa.domain.event;

import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;



public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Long> {

    List<FavoriteEntity> findByHostId(UUID hostId);

    Optional<FavoriteEntity> findByHostIdAndEventId(UUID hostId, String eventId);

    @Modifying
    @Query("DELETE FROM FavoriteEntity f WHERE f.eventId = :eventId AND f.host.id = :hostId")
    void deleteByHostIdAndEventId(@Param("hostId") UUID hostId, @Param("eventId") String eventId);
}