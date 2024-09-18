package com.example.yeogiwa.domain.event;

import com.example.yeogiwa.domain.user.UserEntity;
import com.example.yeogiwa.domain.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public void removeFavorite(Long userId, String eventId) {
        EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found with ID"));

        favoriteRepository.deleteByUserIdAndEvent(userId, event);
    }

    public List<FavoriteEntity> getFavoritesByUser(Long userId) {

        return favoriteRepository.findByUserId(userId);
    }
}



