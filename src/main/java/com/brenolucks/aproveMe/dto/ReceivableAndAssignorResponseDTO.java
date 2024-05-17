package com.brenolucks.aproveMe.dto;

import com.brenolucks.aproveMe.dto.assignor.AssignorResponseDTO;
import com.brenolucks.aproveMe.dto.receivable.ReceivableResponseDTO;

public record ReceivableAndAssignorResponseDTO(ReceivableResponseDTO receivableResponseDTO, AssignorResponseDTO assignorResponseDTO){}
