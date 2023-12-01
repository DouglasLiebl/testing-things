package io.github.douglasliebl.authserver.api.auth.resource;

import io.github.douglasliebl.authserver.api.auth.dto.AuthRequestDTO;
import io.github.douglasliebl.authserver.api.auth.security.JwtService;
import io.github.douglasliebl.authserver.api.auth.service.AuthenticationService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthRequestDTO request, HttpServletResponse response) {
        var r = authenticationService.login(request, response);
        return ResponseEntity.ok(r);
    }

    @PostMapping("/test")
    public ResponseEntity test(@RequestBody AuthRequestDTO request) {
        var r = jwtService.extractAllClaims(request.getEmail());
        return ResponseEntity.ok(r);
    }

}
