package com.brenolucks.aproveMe.controller.authentication;

import com.brenolucks.aproveMe.domain.model.User;
import com.brenolucks.aproveMe.dto.user.UserRequestDTO;
import com.brenolucks.aproveMe.repositories.UserRepository;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public AuthenticationController(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid UserRequestDTO userRequestDTO){
        //return ResponseEntity.status(HttpStatus.OK).body(authenticationService.getNamePassAuthed(userRequestDTO.login(), userRequestDTO.password()))

        var username = new UsernamePasswordAuthenticationToken(userRequestDTO.login(), userRequestDTO.password());
        var auth = this.authenticationManager.authenticate(username);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity register(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        var existUser = userRepository.findUserByLogin(userRequestDTO.login());
        if(existUser != null) {
            return ResponseEntity.badRequest().build();
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(userRequestDTO.password());
        User newUser = new User(userRequestDTO.login(), userRequestDTO.password(), userRequestDTO.role());

        //this.userRepository.save(newUser);
        
        return ResponseEntity.ok().build();

    }
}
