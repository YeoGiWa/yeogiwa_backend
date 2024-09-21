package com.example.yeogiwa.domain.favorite;

import com.example.yeogiwa.domain.event.EventEntity;
import com.example.yeogiwa.domain.event.EventRepository;
import com.example.yeogiwa.domain.user.UserEntity;
import com.example.yeogiwa.domain.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    private final UserRepository userRepository;

    private final EventRepository eventRepository;

    public FavoriteService(FavoriteRepository favoriteRepository, UserRepository userRepository, EventRepository eventRepository) {
        this.favoriteRepository = favoriteRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    @Transactional
    public FavoriteEntity addFavorite(Long userId, String eventId) {
        EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found with ID"));

        Optional<FavoriteEntity> existingFavorite = favoriteRepository.findByUserIdAndEvent(userId, event);
        if (existingFavorite.isPresent()) {
            throw new IllegalArgumentException("This favorite already exists for the user");
        }

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID"));

        FavoriteEntity favorite = FavoriteEntity.builder()
                .user(user)
                .event(event)
                .build();

        return favoriteRepository.save(favorite);
    }

    @Transactional
    public void removeFavorite(Long favoriteId) {
        FavoriteEntity favorite = favoriteRepository.findById(favoriteId)
                .orElseThrow(() -> new IllegalArgumentException("Favorite not found with ID"));

        favoriteRepository.delete(favorite);
    }

    public List<FavoriteEntity> getFavoritesByUser(Long userId) {
        return favoriteRepository.findByUserId(userId);
    }

    @Transactional
    public FavoriteEntity getFavorite(Long userId, String eventId) {
        EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found with ID"));

        return favoriteRepository.findByUserIdAndEvent(userId, event)
                .orElseThrow(() -> new IllegalArgumentException("Favorite not found for user and event"));
    }

}



