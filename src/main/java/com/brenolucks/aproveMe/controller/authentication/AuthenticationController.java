package com.brenolucks.aproveMe.controller.authentication;

import com.brenolucks.aproveMe.dto.user.UserLoginRequestDTO;
import com.brenolucks.aproveMe.dto.user.UserLoginResponseDTO;
import com.brenolucks.aproveMe.dto.user.UserRegisterRequestDTO;
import com.brenolucks.aproveMe.dto.user.UserRegisterResponseDTO;
import com.brenolucks.aproveMe.services.authentication.AuthenticationServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    private final AuthenticationServiceImpl authenticationService;
    public AuthenticationController(AuthenticationServiceImpl authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDTO> userLogin(@RequestBody @Valid UserLoginRequestDTO userLoginRequestDTO){
        var userLogin = authenticationService.loginUser(userLoginRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(userLogin);
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponseDTO> userRegister(@RequestBody @Valid UserRegisterRequestDTO userRegisterRequestDTO){
        var userSaved = authenticationService.registerUser(userRegisterRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userSaved);
    }
}
