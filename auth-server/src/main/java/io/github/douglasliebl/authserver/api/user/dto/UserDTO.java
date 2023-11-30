package io.github.douglasliebl.authserver.api.user.dto;

import io.github.douglasliebl.authserver.api.user.model.entity.Role;
import io.github.douglasliebl.authserver.api.user.model.entity.User;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.Set;

@Getter @Setter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String cpf;
    private String email;
    private String password;
    private OffsetDateTime createdAt;
    private Set<Role> roles;

    public static UserDTO of(User entity) {
        return UserDTO.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .cpf(entity.getCpf())
                .email(entity.getEmail())
                .password("******************")
                .createdAt(entity.getCreatedAt())
                .roles(entity.getRoles()).build();
    }
}
