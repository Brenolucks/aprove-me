package com.brenolucks.aproveMe.services.user;

import com.brenolucks.aproveMe.dto.user.UserResponseDTO;

public interface UserService {
    UserResponseDTO getUserByLogin(String login);
}
