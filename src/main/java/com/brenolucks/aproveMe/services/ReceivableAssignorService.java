package com.brenolucks.aproveMe.services;

import com.brenolucks.aproveMe.dto.*;
import com.brenolucks.aproveMe.model.Assignor;
import com.brenolucks.aproveMe.model.Receivable;

import java.util.Optional;
import java.util.UUID;

public interface ReceivableAssignorService {
    ReceivableAndAssignorResponseDTO registerReceivableAndAssignor(ReceivableAndAssignorRequestDTO requestDTO);
    Receivable convertReceivableToModel(ReceivableDTO receivableDTO);
    Assignor convertAssignorToModel(AssignorDTO assignorDTO);
    Optional<Receivable> getReceivableById(UUID id);
    Optional<Assignor> getAssignorById(UUID id);
    Receivable updateReceivable(UUID id, Receivable receivable);
    Assignor updateAssignor(UUID id, Assignor assignor);
    String deleteAssignor(UUID id);
    String deleteReceivable(UUID id);
    ReceivableResponseDTO registerReceivable(ReceivableDTO receivableDTO);
    AssignorResponseDTO registerAssignor(AssignorDTO assignorDTO);
}
