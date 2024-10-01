package com.example.yeogiwa.domain.favorite;

import com.example.yeogiwa.domain.event.EventEntity;
import com.example.yeogiwa.domain.user.UserEntity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "favorite")
public class FavoriteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "favorite_id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    private UserEntity user;

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false, insertable = false, updatable = false)
    private EventEntity event;

    @Column(name = "event_id")
    private Long eventId;

    @Column(nullable = false, insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;
}
