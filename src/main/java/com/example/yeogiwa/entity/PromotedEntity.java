package com.example.yeogiwa.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

import java.util.Date;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Table(name = "promoted")
public class PromotedEntity {
    /* Keys */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "promoted_id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ambassador_id")
    private AmbassadorEntity ambassador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private EventEntity event;

    /* Columns */
    @NonNull
    @Column(nullable = false)
    private String qr;

    @NonNull
    @Column(nullable = false)
    private int balance;

    @NonNull
    @Column(nullable = false)
    private boolean authenticated;

    @Column(nullable = false, insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    /* Related */
}
