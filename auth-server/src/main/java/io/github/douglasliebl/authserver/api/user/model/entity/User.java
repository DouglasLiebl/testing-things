package io.github.douglasliebl.authserver.api.user.model.entity;

import io.github.douglasliebl.authserver.api.user.dto.UserInsertDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter @Setter @Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String cpf;

    @Column(unique = true)
    private String email;

    private String password;

    @CreatedDate
    private OffsetDateTime createdAt;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "tb_user_roles",
    joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

    public static User of(UserInsertDTO request) {
        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .cpf(request.getCpf())
                .email(request.getEmail())
                .createdAt(OffsetDateTime.now())
                .build();
    }
}
