package com.brenolucks.aproveMe.services;

import com.brenolucks.aproveMe.domain.mapper.AssignorMapper;
import com.brenolucks.aproveMe.domain.mapper.ReceivableMapper;
import com.brenolucks.aproveMe.dto.*;
import com.brenolucks.aproveMe.dto.assignor.AssignorRequestDTO;
import com.brenolucks.aproveMe.dto.assignor.AssignorResponseDTO;
import com.brenolucks.aproveMe.dto.receivable.ReceivableRequestDTO;
import com.brenolucks.aproveMe.dto.receivable.ReceivableResponseDTO;
import com.brenolucks.aproveMe.domain.model.Assignor;
import com.brenolucks.aproveMe.domain.model.Receivable;
import com.brenolucks.aproveMe.repositories.AssignorRepository;
import com.brenolucks.aproveMe.repositories.ReceivableRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Null;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ReceivableAssignorServiceImpl implements ReceivableAssignorService {
    private final ReceivableRepository receivableRepository;
    private final AssignorRepository assignorRepository;
    private final AssignorMapper assignorMapper;
    private final ReceivableMapper receivableMapper;

    public ReceivableAssignorServiceImpl(ReceivableRepository receivableRepository, AssignorRepository assignorRepository,
                                         ReceivableMapper receivableMapper, AssignorMapper assignorMapper) {
        this.receivableRepository = receivableRepository;
        this.assignorRepository = assignorRepository;
        this.receivableMapper = receivableMapper;
        this.assignorMapper = assignorMapper;
    }

    @Override
    @Transactional
    public ReceivableAndAssignorResponseDTO registerReceivableAndAssignor(ReceivableAndAssignorRequestDTO receivableAndAssignorRequestDTO) {
        if (receivableAndAssignorRequestDTO == null || receivableAndAssignorRequestDTO.assignorRequestDTO() == null || receivableAndAssignorRequestDTO.receivableRequestDTO() == null) {
            throw new IllegalArgumentException("Os campos do request não podem ser nulos.");
        }

        Assignor assignor = assignorRepository.save(assignorMapper.toAssignor(receivableAndAssignorRequestDTO.assignorRequestDTO()));
        Receivable receivable = receivableMapper.toReceivable(receivableAndAssignorRequestDTO.receivableRequestDTO());

        receivable.setAssignorID(assignor.getId());
        var receivableResponse = receivableMapper.toReceivableResponseDTO(receivableRepository.save(receivable));
        var assignorResponse = assignorMapper.toAssignorResponseDTO(assignor);

        return new ReceivableAndAssignorResponseDTO(receivableResponse, assignorResponse);
    }

    @Override
    public ReceivableResponseDTO getReceivableById(UUID id) {
        return receivableMapper.toReceivableResponseDTO(receivableRepository.findReceivableById(id)
                .orElseThrow(() -> new EntityNotFoundException("Não existe recebível para este id: " + id)));
    }

    @Override
    public AssignorResponseDTO getAssignorById(UUID id) {
        return assignorMapper.toAssignorResponseDTO(assignorRepository.findAssignorById(id)
                .orElseThrow(() -> new EntityNotFoundException("Não existe cedente para este id: " + id)));
    }

    @Override
    public ReceivableResponseDTO updateReceivable(UUID id, ReceivableRequestDTO receivableRequestDTO) {
        Receivable receivableUpdated = receivableRepository.findReceivableById(id)
                .orElseThrow(() -> new EntityNotFoundException("Não foi encontrado nenhum recebível com este id: " + id));

        receivableUpdated.setReceivableValue(receivableRequestDTO.receivableValue());
        receivableUpdated.setEmissionDate(receivableRequestDTO.emissionDate());

        return receivableMapper.toReceivableResponseDTO(receivableRepository.save(receivableUpdated));

    }

    @Override
    public AssignorResponseDTO updateAssignor(UUID id, AssignorRequestDTO assignorRequestDTO) {
        Assignor assignorUpdated = assignorRepository.findAssignorById(id)
                .orElseThrow(() -> new EntityNotFoundException("Não foi encontrado nenhum cedente com este id: " + id));

        assignorUpdated.setDocument(assignorRequestDTO.document());
        assignorUpdated.setEmail(assignorRequestDTO.email());
        assignorUpdated.setPhone(assignorRequestDTO.phone());
        assignorUpdated.setName(assignorRequestDTO.name());

        return assignorMapper.toAssignorResponseDTO(assignorRepository.save(assignorUpdated));
    }

    @Override
    public void deleteReceivable(UUID id) {
        var existReceivable = receivableRepository.findReceivableById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recebível não encontrado"));

        receivableRepository.deleteById(id);
    }

    @Override
    public void deleteAssignor(UUID id) {
        var existAssignor = assignorRepository.findAssignorById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cedente não encontrado"));

        assignorRepository.deleteById(id);
    }

    @Override
    public ReceivableResponseDTO registerReceivable(ReceivableRequestDTO receivableRequestDTO) {
        var receivable = receivableMapper.toReceivable(receivableRequestDTO);
        return receivableMapper.toReceivableResponseDTO(receivableRepository.save(receivable));
    }

    @Override
    public AssignorResponseDTO registerAssignor(AssignorRequestDTO assignorRequestDTO) throws RuntimeException {
        return assignorMapper.toAssignorResponseDTO(assignorRepository.save(assignorMapper.toAssignor(assignorRequestDTO)));
    }
}
