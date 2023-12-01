package io.github.douglasliebl.authserver.api.auth.service.impl;

import io.github.douglasliebl.authserver.api.auth.model.entity.RefreshToken;
import io.github.douglasliebl.authserver.api.auth.model.repository.RefreshTokenRepository;
import io.github.douglasliebl.authserver.api.auth.service.RefreshTokenService;
import io.github.douglasliebl.authserver.api.user.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;


    @Override
    public String generateRefreshToken(String email) {
        RefreshToken refreshToken = RefreshToken.builder()
                .user(userRepository.findByEmail(email)
                        .orElseThrow())
                .refreshToken(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(600000))
                .build();

        return refreshTokenRepository
                .save(refreshToken).getRefreshToken();
    }

    @Override
    public Optional<RefreshToken> findByToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken refreshToken) {
        if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException("Token expired");
        }

        return refreshToken;
    }


}
