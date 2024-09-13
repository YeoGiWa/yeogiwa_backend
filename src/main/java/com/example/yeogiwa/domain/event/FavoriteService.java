package com.example.yeogiwa.domain.event;

import com.example.yeogiwa.domain.host.HostEntity;
import com.example.yeogiwa.domain.host.HostRepository;
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

    @Autowired
    private EventRepository eventRepository;
    public FavoriteEntity addFavorite(Long hostId, String eventId) {
        Optional<HostEntity> host = hostRepository.findById(hostId);
        Optional<EventEntity> event = eventRepository.findById(eventId);

        if (host.isPresent() && event.isPresent()) {
            FavoriteEntity favorite = FavoriteEntity.builder()
                    .host(host.get())
                    .event(event.get())
                    .build();
            return favoriteRepository.save(favorite);
        } else {
            throw new IllegalArgumentException("Event or Host not found");
        }
    }

    public void removeFavorite(Long favoriteId) {
        favoriteRepository.deleteById(favoriteId);
    }

    public List<FavoriteEntity> getFavoritesByHost(UUID hostId) {
        return favoriteRepository.findByHostId(hostId);
    }
}