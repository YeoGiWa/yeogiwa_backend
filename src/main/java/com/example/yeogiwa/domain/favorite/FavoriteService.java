package com.example.yeogiwa.domain.favorite;

import com.example.yeogiwa.domain.event.EventEntity;
import com.example.yeogiwa.domain.event.EventRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final EventRepository eventRepository;

    public UUID addFavorite(Long userId, Long eventId) {
        Optional<EventEntity> event = eventRepository.findById(eventId);
        if (event.isEmpty()) throw new HttpClientErrorException(HttpStatusCode.valueOf(404));

        Optional<FavoriteEntity> favorite = favoriteRepository.findByUser_IdAndEvent_Id(userId, eventId);
        if (favorite.isPresent()) throw new HttpClientErrorException(HttpStatusCode.valueOf(409));

        FavoriteEntity newFavorite = FavoriteEntity.builder()
            .userId(userId)
            .eventId(event.get().getId())
            .build();
        return favoriteRepository.save(newFavorite).getId();
    }

    public void removeFavorite(Long userId, Long eventId) {
        favoriteRepository.deleteAllByUser_IdAndEvent_Id(userId, eventId);
    }

    public Boolean getIsFavorite(Long userId, Long eventId) {
        Optional<FavoriteEntity> favorite = favoriteRepository.findByUser_IdAndEvent_Id(userId, eventId);
        return favorite.isPresent();
    }

    public Map<Long, Boolean> getIsFavorites(Long userId, List<Long> eventIds) {
        Map<Long, Boolean> isFavorites = new HashMap<>();
        for (Long eventId: eventIds) { isFavorites.put(eventId, false); }
        Optional<List<FavoriteEntity>> favorites = favoriteRepository.findAllByUser_IdAndEvent_IdIn(userId, eventIds);
        if (favorites.isEmpty()) return isFavorites;
        for (FavoriteEntity favorite: favorites.get()) {
            isFavorites.put(favorite.getEventId(), true);
        }
        return isFavorites;
    }
}




