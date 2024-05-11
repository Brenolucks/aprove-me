package com.brenolucks.aproveMe.services;

import com.brenolucks.aproveMe.dto.AssignorDTO;
import com.brenolucks.aproveMe.dto.ReceivableAndAssignorRequestDTO;
import com.brenolucks.aproveMe.dto.ReceivableAndAssignorResponseDTO;
import com.brenolucks.aproveMe.dto.ReceivableDTO;
import com.brenolucks.aproveMe.model.Assignor;
import com.brenolucks.aproveMe.model.Receivable;

public interface ReceivableAssignorService {
    ReceivableAndAssignorResponseDTO registerReceivableAndAssignor(ReceivableAndAssignorRequestDTO requestDTO);
    Receivable convertReceivableToModel(ReceivableDTO receivableDTO);
    Assignor convertAssignorToModel(AssignorDTO assignorDTO);
}
