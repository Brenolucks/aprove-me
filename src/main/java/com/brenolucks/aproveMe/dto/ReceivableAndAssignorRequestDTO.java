package com.brenolucks.aproveMe.dto;


import com.brenolucks.aproveMe.model.Assignor;
import com.brenolucks.aproveMe.model.Receivable;
import lombok.Data;

@Data
public class ReceivableAndAssignorRequestDTO {

    private ReceivableDTO receivable;
    private AssignorDTO assignor;
}
