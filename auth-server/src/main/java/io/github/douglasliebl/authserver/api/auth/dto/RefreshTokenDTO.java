package io.github.douglasliebl.authserver.api.auth.dto;

import lombok.*;

@Getter @Setter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenDTO {

    private String refreshToken;
}
