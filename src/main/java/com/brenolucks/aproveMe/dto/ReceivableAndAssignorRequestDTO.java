package com.brenolucks.aproveMe.dto;

import com.brenolucks.aproveMe.dto.assignor.AssignorRequestDTO;
import com.brenolucks.aproveMe.dto.receivable.ReceivableRequestDTO;

public record ReceivableAndAssignorRequestDTO (ReceivableRequestDTO receivableRequestDTO, AssignorRequestDTO assignorRequestDTO){}
