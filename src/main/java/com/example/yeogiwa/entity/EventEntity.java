package com.example.yeogiwa.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Table(name = "event")
public class EventEntity {
    /* Keys */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "event_id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_id")
    private HostEntity host;

    /* Columns */
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String place;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private int ratio;

    @Column(nullable = false, insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    /* Related */
    @OneToMany(mappedBy = "event")
    @Builder.Default
    private List<AmbassadorEntity> ambassadors = new ArrayList<>();

    @OneToMany(mappedBy = "event")
    @Builder.Default
    private List<PromotedEntity> promotes = new ArrayList<>();

    @OneToMany(mappedBy = "event")
    @Builder.Default
    private List<FundEntity> funds = new ArrayList<>();
}
