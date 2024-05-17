package com.brenolucks.aproveMe.repositories;

import com.brenolucks.aproveMe.domain.model.Assignor;
import com.brenolucks.aproveMe.dto.assignor.AssignorResponseDTO;
import com.brenolucks.aproveMe.dto.receivable.ReceivableResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AssignorRepository extends JpaRepository<Assignor, UUID> {
    Optional<Assignor> findAssignorById(UUID id);
}
