package com.brenolucks.aproveMe.repositories;

import com.brenolucks.aproveMe.domain.model.Receivable;
import com.brenolucks.aproveMe.dto.receivable.ReceivableResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

public interface ReceivableRepository extends JpaRepository<Receivable, UUID> {
    Optional<Receivable> findReceivableById(UUID id);
}
