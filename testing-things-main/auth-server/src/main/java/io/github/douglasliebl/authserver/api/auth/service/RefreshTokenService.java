package io.github.douglasliebl.authserver.api.auth.service;

import io.github.douglasliebl.authserver.api.auth.model.entity.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {

    String generateRefreshToken(String email);

    Optional<RefreshToken> findByToken(String refreshToken);

    RefreshToken verifyExpiration(RefreshToken refreshToken);

}
