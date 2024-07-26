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
@Table(name = "ambassador")
public class AmbassadorEntity {

    /* Keys */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ambassador_id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private EventEntity event;

    /* Columns */
    @NonNull
    @Column(nullable = false)
    private String qr;

    @Column(nullable = false, insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    /* Related */
    @OneToMany(mappedBy = "ambassador")
    @Builder.Default
    private List<PromotedEntity> promotes = new ArrayList<>();
}
