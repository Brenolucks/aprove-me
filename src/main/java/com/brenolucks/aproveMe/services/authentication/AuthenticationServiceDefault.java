package com.brenolucks.aproveMe.services.authentication;

import com.brenolucks.aproveMe.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceDefault implements UserDetailsService {
    private final UserRepository userRepository;

    public AuthenticationServiceDefault(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findUserByLogin(username);
        System.out.println("User found: " + user.getUsername() + ", roles: " + user.getAuthorities());
        return user;
    }
}
