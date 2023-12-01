package io.github.douglasliebl.authserver.resource;

import io.github.douglasliebl.authserver.dto.AuthRequestDTO;
import io.github.douglasliebl.authserver.dto.JwtResponseDTO;
import io.github.douglasliebl.authserver.dto.RefreshToken;
import io.github.douglasliebl.authserver.dto.RefreshTokenDTO;
import io.github.douglasliebl.authserver.security.JwtService;
import io.github.douglasliebl.authserver.service.RefreshTokenService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService tokenService;
    @Value("${jwt.cookieExpiry}")
    private int cookieExpiry;


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthRequestDTO authRequestDTO, HttpServletResponse response) {
        Authentication authentication = authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));

        if(authentication.isAuthenticated()) {
            var token = JwtResponseDTO.builder()
                    .accessToken(jwtService.generateToken(authRequestDTO.getUsername()))
                    .token(tokenService.create(authRequestDTO.getUsername()).getToken())
                    .build();
            ResponseCookie cookie = ResponseCookie.from("accessToken", token.getAccessToken())
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .maxAge(cookieExpiry)
                    .build();
            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

            return ResponseEntity.ok(token);

        } else {
            throw  new RuntimeException();
        }

    }

    @PostMapping("/refreshToken")
    public ResponseEntity refreshToken(@RequestBody RefreshTokenDTO refreshToken) {
        var response = tokenService.findByToken(refreshToken.getToken())
                .map(tokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = jwtService.generateToken(user.getUsername());
                    var newRefreshToken = tokenService.create(user.getUsername());
                    return JwtResponseDTO.builder()
                            .accessToken(accessToken)
                            .token(newRefreshToken.getToken()).build();
                });

        return ResponseEntity.ok(response);
    }
}
