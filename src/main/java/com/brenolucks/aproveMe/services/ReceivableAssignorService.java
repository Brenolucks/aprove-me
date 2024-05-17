package com.brenolucks.aproveMe.services;

import com.brenolucks.aproveMe.dto.*;
import com.brenolucks.aproveMe.dto.assignor.AssignorRequestDTO;
import com.brenolucks.aproveMe.dto.assignor.AssignorResponseDTO;
import com.brenolucks.aproveMe.dto.receivable.ReceivableRequestDTO;
import com.brenolucks.aproveMe.dto.receivable.ReceivableResponseDTO;
import com.brenolucks.aproveMe.domain.model.Assignor;
import com.brenolucks.aproveMe.domain.model.Receivable;

import java.util.Optional;
import java.util.UUID;

public interface ReceivableAssignorService {
    ReceivableAndAssignorResponseDTO registerReceivableAndAssignor(ReceivableAndAssignorRequestDTO requestDTO);
    ReceivableResponseDTO getReceivableById(UUID id);
    AssignorResponseDTO getAssignorById(UUID id);
    ReceivableResponseDTO updateReceivable(UUID id, ReceivableRequestDTO receivableRequestDTO);
    AssignorResponseDTO updateAssignor(UUID id, AssignorRequestDTO assignorRequestDTO);
    void deleteAssignor(UUID id);
    void deleteReceivable(UUID id);
    ReceivableResponseDTO registerReceivable(ReceivableRequestDTO receivableRequestDTO);
    AssignorResponseDTO registerAssignor(AssignorRequestDTO assignorRequestDTO);
}
