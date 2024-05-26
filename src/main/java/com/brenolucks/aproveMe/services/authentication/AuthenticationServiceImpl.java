package com.brenolucks.aproveMe.services.authentication;

import com.brenolucks.aproveMe.domain.enums.UserRole;
import com.brenolucks.aproveMe.domain.model.User;
import com.brenolucks.aproveMe.dto.user.UserLoginRequestDTO;
import com.brenolucks.aproveMe.dto.user.UserLoginResponseDTO;
import com.brenolucks.aproveMe.dto.user.UserRegisterRequestDTO;
import com.brenolucks.aproveMe.dto.user.UserRegisterResponseDTO;
import com.brenolucks.aproveMe.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    public AuthenticationServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, TokenService tokenService){
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }
    @Override
    public UserRegisterResponseDTO registerUser(UserRegisterRequestDTO userRegisterRequestDTO) {
        var existUser = userRepository.findUserByLogin(userRegisterRequestDTO.login());

        if(existUser != null) {
            throw new RuntimeException("ERROR: O usuário que está tentando cadastrar já existe");
        }

        var passwordEncrypted = new BCryptPasswordEncoder().encode(userRegisterRequestDTO.password());
        
        var newUser = new User(userRegisterRequestDTO.login(), passwordEncrypted, userRegisterRequestDTO.role());
        var userSaved = userRepository.save(newUser);

        return new UserRegisterResponseDTO(userSaved.getLogin(), userSaved.getPassword(), userSaved.getRole());
    }

    @Override
    public UserLoginResponseDTO loginUser(UserLoginRequestDTO userLoginRequestDTO) {
        var userExist = new UsernamePasswordAuthenticationToken(userLoginRequestDTO.login(), userLoginRequestDTO.password());
        var authenticated = authenticationManager.authenticate(userExist);

        var token = tokenService.generateToken((User) authenticated.getPrincipal());

        return new UserLoginResponseDTO(token);
    }
}
