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

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public FavoriteEntity addFavorite(UUID userId, String eventId) {
        Optional<FavoriteEntity> existingFavorite = favoriteRepository.findByUserIdAndEventId(userId, eventId);

        if (existingFavorite.isPresent()) {
            throw new IllegalArgumentException("This favorite already exists for the user");
        }

        UserEntity user = userRepository.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        FavoriteEntity favorite = FavoriteEntity.builder()
                .user(user)
                .eventId(eventId)
                .build();
        return favoriteRepository.save(favorite);
    }

    @Transactional
    public void removeFavorite(UUID userId, String eventId) {
        favoriteRepository.deleteByUserIdAndEventId(userId, eventId);
    }

    public List<FavoriteEntity> getFavoritesByUser(UUID userId) {
        return favoriteRepository.findByUserId(userId);
    }
}


