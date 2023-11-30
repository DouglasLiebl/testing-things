package io.github.douglasliebl.authserver.api.user.service.impl;

import io.github.douglasliebl.authserver.api.user.dto.UserDTO;
import io.github.douglasliebl.authserver.api.user.dto.UserInsertDTO;
import io.github.douglasliebl.authserver.api.user.model.entity.User;
import io.github.douglasliebl.authserver.api.user.model.repository.RoleRepository;
import io.github.douglasliebl.authserver.api.user.model.repository.UserRepository;
import io.github.douglasliebl.authserver.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO register(UserInsertDTO request) {
        var user = User.of(request);
        user.setRoles(Set.of(roleRepository.findByName("ROLE_CLIENT")
                .orElseThrow()));
        user.setPassword(passwordEncoder
                .encode(request.getPassword()));

        return UserDTO.of(userRepository.save(user));
    }
}
