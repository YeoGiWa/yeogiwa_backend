package com.example.yeogiwa.domain.ambassador;

import com.example.yeogiwa.domain.event.EventEntity;
import com.example.yeogiwa.domain.promoted.PromotedEntity;
import com.example.yeogiwa.domain.user.UserEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SQLDelete(sql = "UPDATE event SET is_deleted = true WHERE ambassador_id = ?")
@SQLRestriction("is_deleted = false")
@Table(name = "ambassador")
public class AmbassadorEntity {

    /* Keys */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ambassador_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserEntity user;

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", insertable = false, updatable = false)
    private EventEntity event;

    @Column(name = "event_id")
    private Long eventId;

    /* Columns */
    @NonNull
    @Column(nullable = false)
    private byte[] qr;

    @Column(nullable = false, insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(nullable = false, insertable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    @Builder.Default
    private Boolean isDeleted = false;

    /* Related */
    @OneToMany(mappedBy = "ambassador")
    @Builder.Default
    private List<PromotedEntity> promotes = new ArrayList<>();
}
