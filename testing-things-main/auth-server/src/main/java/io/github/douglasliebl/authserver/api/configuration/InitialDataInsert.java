package io.github.douglasliebl.authserver.api.configuration;

import io.github.douglasliebl.authserver.api.user.model.entity.Role;
import io.github.douglasliebl.authserver.api.user.model.entity.User;
import io.github.douglasliebl.authserver.api.user.model.repository.RoleRepository;
import io.github.douglasliebl.authserver.api.user.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class InitialDataInsert implements ApplicationRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (userRepository.count() != 0) return;
        if (roleRepository.count() != 0) return;

        Role roleAdmin = Role.builder()
                .name("ROLE_ADMIN").build();

        Role roleClient = Role.builder()
                .name("ROLE_CLIENT").build();

        roleAdmin = roleRepository.save(roleAdmin);
        roleClient = roleRepository.save(roleClient);

        User admin = User.builder()
                .firstName("ADMIN")
                .lastName("ADMIN")
                .cpf("***********")
                .email("admin@email.com")
                .password(passwordEncoder.encode("admin"))
                .roles(Set.of(roleAdmin, roleClient))
                .createdAt(OffsetDateTime.now())
                .build();

        User client = User.builder()
                .firstName("CLIENT")
                .lastName("CLIENT")
                .cpf("###########")
                .email("client@email.com")
                .password(passwordEncoder.encode("client"))
                .roles(Set.of(roleClient))
                .createdAt(OffsetDateTime.now())
                .build();

        userRepository.save(admin);
        userRepository.save(client);
    }
}
