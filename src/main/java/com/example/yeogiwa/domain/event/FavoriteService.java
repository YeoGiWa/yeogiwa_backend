package com.example.yeogiwa.domain.event;

import com.example.yeogiwa.domain.host.HostEntity;
import com.example.yeogiwa.domain.host.HostRepository;
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
    private HostRepository hostRepository;

    @Transactional
    public FavoriteEntity addFavorite(UUID hostId, String eventId) {
        Optional<FavoriteEntity> existingFavorite = favoriteRepository.findByHostIdAndEventId(hostId, eventId);

        if (existingFavorite.isPresent()) {
            throw new IllegalArgumentException("This favorite already exists for the host");
        }

        Optional<HostEntity> host = hostRepository.findById(hostId);
        if (host.isPresent()) {
            FavoriteEntity favorite = FavoriteEntity.builder()
                    .host(host.get())
                    .eventId(eventId)
                    .build();
            return favoriteRepository.save(favorite);
        } else {
            throw new IllegalArgumentException("Host not found");
        }
    }


    @Transactional
    public void removeFavorite(UUID hostId, String eventId) {
        favoriteRepository.deleteByHostIdAndEventId(hostId, eventId);
    }

    public List<FavoriteEntity> getFavoritesByHost(UUID hostId) {
        return favoriteRepository.findByHostId(hostId);
    }
}