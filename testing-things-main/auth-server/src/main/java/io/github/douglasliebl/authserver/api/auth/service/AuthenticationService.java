package io.github.douglasliebl.authserver.api.auth.service;

import io.github.douglasliebl.authserver.api.auth.dto.AuthRequestDTO;
import io.github.douglasliebl.authserver.api.auth.dto.JwtResponseDTO;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthenticationService {

    JwtResponseDTO login(AuthRequestDTO request, HttpServletResponse response);
}
