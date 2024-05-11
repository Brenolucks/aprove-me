package com.brenolucks.aproveMe.services;

import com.brenolucks.aproveMe.dto.AssignorDTO;
import com.brenolucks.aproveMe.dto.ReceivableAndAssignorRequestDTO;
import com.brenolucks.aproveMe.dto.ReceivableAndAssignorResponseDTO;
import com.brenolucks.aproveMe.dto.ReceivableDTO;
import com.brenolucks.aproveMe.model.Assignor;
import com.brenolucks.aproveMe.model.Receivable;
import com.brenolucks.aproveMe.repositories.AssignorRepository;
import com.brenolucks.aproveMe.repositories.ReceivableRepository;
import jakarta.transaction.Transactional;
import org.springframework.expression.spel.ast.Assign;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
public class ReceivableAssignorServiceImpl implements ReceivableAssignorService {

    private final ReceivableRepository receivableRepository;
    private final AssignorRepository assignorRepository;

    public ReceivableAssignorServiceImpl(ReceivableRepository receivableRepository, AssignorRepository assignorRepository) {
        this.receivableRepository = receivableRepository;
        this.assignorRepository = assignorRepository;
    }

    @Override
    public ReceivableAndAssignorResponseDTO registerReceivableAndAssignor(ReceivableAndAssignorRequestDTO requestDTO){
        Assignor convertedAssignor = convertAssignorToModel(requestDTO.getAssignor());
        System.out.println("convertedAssignor: " + convertedAssignor.toString());
        assignorRepository.save(convertedAssignor);
        Receivable convertedReceivable = convertReceivableToModel(requestDTO.getReceivable());
        System.out.println("ConvertedReceivable: " +convertedReceivable.toString());
        convertedReceivable.setAssignorID(convertedAssignor.getId());
        receivableRepository.save(convertedReceivable);
        System.out.println("ConvertedReceivable2: " +convertedReceivable);



        ReceivableAndAssignorResponseDTO receivableAndAssignorResponseDTO = new ReceivableAndAssignorResponseDTO();
        receivableAndAssignorResponseDTO.setAssignor(convertedAssignor);
        receivableAndAssignorResponseDTO.setReceivable(convertedReceivable);
        System.out.println("ReceivableAndAssignorResponseDTO: " +receivableAndAssignorResponseDTO.toString());
        return receivableAndAssignorResponseDTO;

    }

    @Override
    public Receivable convertReceivableToModel(ReceivableDTO receivableDTO){
        Receivable receivable = new Receivable();
        receivable.setReceivableValue(receivableDTO.getReceivableValue());
        receivable.setEmissionDate(receivableDTO.getEmissionDate());
        return receivable;
    }

    @Override
    public Assignor convertAssignorToModel(AssignorDTO assignorDTO){
        Assignor assignor = new Assignor();
        assignor.setDocument(assignorDTO.getDocument());
        assignor.setEmail(assignorDTO.getEmail());
        assignor.setPhone(assignorDTO.getPhone());
        assignor.setName(assignorDTO.getName());
        return assignor;
    }
}
