package com.brenolucks.aproveMe.domain.mapper;


import com.brenolucks.aproveMe.domain.model.Assignor;
import com.brenolucks.aproveMe.dto.assignor.AssignorRequestDTO;
import com.brenolucks.aproveMe.dto.assignor.AssignorResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AssignorMapper {
    Assignor toAssignor(AssignorRequestDTO assignorRequestDTO);
    AssignorResponseDTO toAssignorResponseDTO(Assignor assignor);
}
