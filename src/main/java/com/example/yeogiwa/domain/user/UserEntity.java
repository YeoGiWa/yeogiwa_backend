package com.example.yeogiwa.domain.user;

import com.example.yeogiwa.domain.host.HostEntity;
import com.example.yeogiwa.domain.point.PointEntity;
import com.example.yeogiwa.security.Role;
import com.example.yeogiwa.domain.ambassador.AmbassadorEntity;
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
@Table(name = "user")
public class UserEntity {

    /* Keys */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private UUID id;

    /* Columns */
    @NonNull
    @Column(nullable = false)
    private String email;

    @NonNull
    @Column(nullable = false)
    private String password;

    @Column
    private String name;

    @Column(nullable = false, columnDefinition = "VARCHAR(32) DEFAULT 'ROLE_USER'")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.ROLE_USER;

    @Column(nullable = false, insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(nullable = false, insertable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    @Builder.Default
    private Boolean isDeleted = false;

    /* Related */
    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<AmbassadorEntity> ambassadors = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<HostEntity> hosts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<PointEntity> points = new ArrayList<>();
}
