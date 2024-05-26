package com.brenolucks.aproveMe.dto.user;

import com.brenolucks.aproveMe.domain.enums.UserRole;

public record UserRegisterResponseDTO(String login, String password, UserRole role) {
}
