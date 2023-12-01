package io.github.douglasliebl.authserver.api.auth.service.impl;

import io.github.douglasliebl.authserver.api.auth.dto.AuthRequestDTO;
import io.github.douglasliebl.authserver.api.auth.dto.JwtResponseDTO;
import io.github.douglasliebl.authserver.api.auth.security.CustomUserDetails;
import io.github.douglasliebl.authserver.api.auth.security.JwtService;
import io.github.douglasliebl.authserver.api.auth.service.AuthenticationService;
import io.github.douglasliebl.authserver.api.auth.service.RefreshTokenService;
import io.github.douglasliebl.authserver.api.user.model.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    @Override
    public JwtResponseDTO login(AuthRequestDTO request, HttpServletResponse response) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()));

        if (authentication.isAuthenticated()) {

            UserDetails userDetails = new CustomUserDetails(userRepository.findByEmail(request.getEmail())
                    .orElseThrow());

            var token = JwtResponseDTO.builder()
                    .accessToken(jwtService.generateToken(userDetails))
                    .refreshToken(refreshTokenService.generateRefreshToken(request.getEmail()))
                    .build();

            ResponseCookie cookie = ResponseCookie.from("accessToken", token.getAccessToken())
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .maxAge(600000)
                    .build();
            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

            return token;
        } else {
            throw new RuntimeException();
        }

    }
}
