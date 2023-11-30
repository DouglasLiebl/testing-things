package io.github.douglasliebl.authserver.api.user.dto;

import lombok.*;

@Getter @Setter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInsertDTO {

    private String firstName;
    private String lastName;
    private String cpf;
    private String email;
    private String password;
}
