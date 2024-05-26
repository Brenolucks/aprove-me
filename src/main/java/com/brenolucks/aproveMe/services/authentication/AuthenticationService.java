package com.brenolucks.aproveMe.services.authentication;

import com.brenolucks.aproveMe.dto.user.UserLoginRequestDTO;
import com.brenolucks.aproveMe.dto.user.UserLoginResponseDTO;
import com.brenolucks.aproveMe.dto.user.UserRegisterRequestDTO;
import com.brenolucks.aproveMe.dto.user.UserRegisterResponseDTO;

public interface AuthenticationService {
    UserRegisterResponseDTO registerUser(UserRegisterRequestDTO userRegisterRequestDTO);
    UserLoginResponseDTO loginUser(UserLoginRequestDTO userLoginRequestDTO);
}
