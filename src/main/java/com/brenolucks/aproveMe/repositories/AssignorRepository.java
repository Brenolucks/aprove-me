package com.brenolucks.aproveMe.repositories;

import com.brenolucks.aproveMe.model.Assignor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AssignorRepository extends JpaRepository<Assignor, UUID> {
}
