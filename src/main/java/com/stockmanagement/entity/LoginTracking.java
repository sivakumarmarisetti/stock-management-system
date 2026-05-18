package com.stockmanagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "login_tracking")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginTracking extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String ipAddress;

    @Column(length = 1000)
    private String userAgent;

    @Enumerated(EnumType.STRING)
    private LoginStatus loginStatus;

    private LocalDateTime loginTime;
}