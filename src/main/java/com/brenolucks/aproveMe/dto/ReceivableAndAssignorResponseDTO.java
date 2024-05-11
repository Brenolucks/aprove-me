package com.brenolucks.aproveMe.dto;

import com.brenolucks.aproveMe.model.Assignor;
import com.brenolucks.aproveMe.model.Receivable;
import lombok.Data;

@Data
public class ReceivableAndAssignorResponseDTO {
    private Receivable receivable;
    private Assignor assignor;
}
