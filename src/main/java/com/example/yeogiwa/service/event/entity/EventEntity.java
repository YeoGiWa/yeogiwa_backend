package com.example.yeogiwa.service.event.entity;

import com.example.yeogiwa.service.ambassador.entity.AmbassadorEntity;
import com.example.yeogiwa.service.fund.entity.FundEntity;
import com.example.yeogiwa.service.host.entity.HostEntity;
import com.example.yeogiwa.service.promoted.entity.PromotedEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@SQLDelete(sql = "UPDATE event SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@Table(name = "event")
public class EventEntity {
    /* Keys */
    @Id
    @Column(name = "event_id")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_id")
    private HostEntity host;

    /* Columns */
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String place;

//    @Column(nullable = false)
//    private Date date;

    @Column(nullable = false)
    private int ratio;

    @Column(nullable = false, insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    private String startAt;

    private String endAt;

    private String address;

    private String imageUrl;

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
