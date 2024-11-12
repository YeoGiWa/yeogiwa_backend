package com.example.yeogiwa.domain.event;

import com.example.yeogiwa.domain.ambassador.AmbassadorEntity;
import com.example.yeogiwa.domain.host.HostEntity;
import com.example.yeogiwa.domain.promoted.PromotedEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SQLDelete(sql = "UPDATE event SET is_deleted = true WHERE event_id = ?")
@SQLRestriction("is_deleted = false")
@Table(name = "event")
public class EventEntity {
    /* Keys */
    @Id
    @Column(name = "event_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "host_id", insertable = false, updatable = false)
    private HostEntity host;

    @Column(name = "host_id")
    private Long hostId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "upper_event_id", insertable = false, updatable = false)
    private EventEntity upperEvent;


    @Column(name = "upper_event_id")
    private Long upperEventId;

    /* Columns */
    @Column
    private String title;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 1")
    @Builder.Default
    private Integer round = 1; // 회차

    @Column(nullable = false)
    private Integer ratio;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isApplicable = false;

    @Column(nullable = false, insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(nullable = false, insertable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    @Builder.Default
    private Boolean isDeleted = false;

    /* Related */
    @OneToMany(mappedBy = "event")
    @Builder.Default
    private List<AmbassadorEntity> ambassadors = new ArrayList<>();

    @OneToMany(mappedBy = "event")
    @Builder.Default
    private List<PromotedEntity> promotes = new ArrayList<>();

    @OneToMany(mappedBy = "id")
    @Builder.Default
    private List<EventEntity> lowerEvents = new ArrayList<>();
}
