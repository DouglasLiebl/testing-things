package io.github.douglasliebl.authserver.api.user.service;

import io.github.douglasliebl.authserver.api.user.dto.UserDTO;
import io.github.douglasliebl.authserver.api.user.dto.UserInsertDTO;

public interface UserService {

    UserDTO register(UserInsertDTO request);
}
