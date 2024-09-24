package com.example.yeogiwa.domain.event;

import com.example.yeogiwa.domain.ambassador.AmbassadorEntity;
import com.example.yeogiwa.domain.host.HostEntity;
import com.example.yeogiwa.domain.promoted.PromotedEntity;
import com.example.yeogiwa.domain.session.SessionEntity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import java.time.LocalDate;
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
    @JoinColumn(name = "host_id")
    private HostEntity host;

    /* Columns */
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String place;

    @Column(nullable = false)
    private int ratio;

    @Column(nullable = false, insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(nullable = false, insertable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    @Builder.Default
    private Boolean isDeleted = false;

    private LocalDate startAt;

    private LocalDate endAt;

    private String imageUrl;

    private String region;

    private Integer totalFund;

    /* Related */
    @OneToMany(mappedBy = "event")
    @Builder.Default
    private List<AmbassadorEntity> ambassadors = new ArrayList<>();

    @OneToMany(mappedBy = "event")
    @Builder.Default
    private List<PromotedEntity> promotes = new ArrayList<>();

    @OneToMany(mappedBy = "event")
    @Builder.Default
    private List<SessionEntity> sessions = new ArrayList<>();
}
