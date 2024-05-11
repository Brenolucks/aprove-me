package com.brenolucks.aproveMe.repositories;

import com.brenolucks.aproveMe.model.Receivable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReceivableRepository extends JpaRepository<Receivable, UUID> {
}
