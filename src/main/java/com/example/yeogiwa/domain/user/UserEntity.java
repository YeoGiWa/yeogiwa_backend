package com.example.yeogiwa.domain.user;

import com.example.yeogiwa.domain.host.HostEntity;
import com.example.yeogiwa.domain.point.PointEntity;
import com.example.yeogiwa.enums.Role;
import com.example.yeogiwa.domain.ambassador.AmbassadorEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@SQLDelete(sql = "UPDATE user SET is_deleted = true WHERE user_id = ?")
@SQLRestriction("is_deleted = false")
@Table(name = "user")
public class UserEntity {

    /* Keys */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column
    private String oauth2Id;

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
    private Role role = Role.USER;

    @Column(nullable = false, insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

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
