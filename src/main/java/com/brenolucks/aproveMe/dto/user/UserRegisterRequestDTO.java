package com.brenolucks.aproveMe.dto.user;

import com.brenolucks.aproveMe.domain.enums.UserRole;

public record UserRegisterRequestDTO(String login, String password, UserRole role) {
}
