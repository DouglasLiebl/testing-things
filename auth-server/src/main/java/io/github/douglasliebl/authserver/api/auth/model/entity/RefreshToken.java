package io.github.douglasliebl.authserver.api.auth.model.entity;

import io.github.douglasliebl.authserver.api.user.model.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter @Setter @Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_refresh_tokens")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String refreshToken;
    private Instant expiryDate;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
