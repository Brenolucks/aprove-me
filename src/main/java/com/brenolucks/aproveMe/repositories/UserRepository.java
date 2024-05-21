package com.brenolucks.aproveMe.repositories;

import com.brenolucks.aproveMe.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UUID, User> {
    UserDetails findUserByLogin(String login);
}
