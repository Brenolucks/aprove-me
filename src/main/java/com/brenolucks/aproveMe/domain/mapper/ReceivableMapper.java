package com.brenolucks.aproveMe.domain.mapper;

import com.brenolucks.aproveMe.domain.model.Receivable;
import com.brenolucks.aproveMe.dto.receivable.ReceivableRequestDTO;
import com.brenolucks.aproveMe.dto.receivable.ReceivableResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReceivableMapper {
    Receivable toReceivable(ReceivableRequestDTO receivableRequestDTO);
    ReceivableResponseDTO toReceivableResponseDTO(Receivable receivable);
}
