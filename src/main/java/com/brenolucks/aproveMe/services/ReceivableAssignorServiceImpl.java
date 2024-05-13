package com.brenolucks.aproveMe.services;

import com.brenolucks.aproveMe.dto.*;
import com.brenolucks.aproveMe.model.Assignor;
import com.brenolucks.aproveMe.model.Receivable;
import com.brenolucks.aproveMe.repositories.AssignorRepository;
import com.brenolucks.aproveMe.repositories.ReceivableRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.expression.spel.ast.Assign;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;
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
        assignorRepository.save(convertedAssignor);

        Receivable convertedReceivable = convertReceivableToModel(requestDTO.getReceivable());
        convertedReceivable.setAssignorID(convertedAssignor.getId());
        receivableRepository.save(convertedReceivable);

        ReceivableAndAssignorResponseDTO responseDTO = new ReceivableAndAssignorResponseDTO();
        responseDTO.setAssignor(convertedAssignor);
        responseDTO.setReceivable(convertedReceivable);

        return responseDTO;

    }

    @Override
    public Optional<Receivable> getReceivableById(UUID id){
        Optional<Receivable> receivableById = receivableRepository.findById(id);

        receivableById
                .map(mensagem -> "Mensagem: " + mensagem)
                .orElse("Não existe recebível para este id: " + id);

        return receivableById;
    }

    @Override
    public Optional<Assignor> getAssignorById(UUID id){
        Optional<Assignor> assignorById = assignorRepository.findById(id);

        assignorById
                .map(mensagem -> "Mensagem: " + mensagem)
                .orElse("Não existe cedente para este id: " + id);

        return assignorById;
    }

    @Override
    public Receivable updateReceivable(UUID id, Receivable receivable){
        var existReceivableById = receivableRepository.findById(id);

        if(existReceivableById.isPresent()){
            Receivable receivableUpdate = existReceivableById.get();
            receivableUpdate.setReceivableValue(receivable.getReceivableValue());
            receivableUpdate.setEmissionDate(receivable.getEmissionDate());

            return receivableUpdate;
        } else {
            throw new EntityNotFoundException("Não foi encontrado nenhum recebível com este id: " + id);
        }
    }

    @Override
    public Assignor updateAssignor(UUID id, Assignor assignor){
        var existAssignorById = assignorRepository.findById(id);

        if(existAssignorById.isPresent()){
            Assignor assignorUpdate = existAssignorById.get();
            assignorUpdate.setDocument(assignor.getDocument());
            assignorUpdate.setEmail(assignor.getEmail());
            assignorUpdate.setPhone(assignor.getPhone());
            assignorUpdate.setName(assignor.getName());

            return assignorUpdate;
        } else {
            throw new EntityNotFoundException("Não foi encontrado nenhum cedente com este id: " + id);
        }
    }

    @Override
    public String deleteReceivable(UUID id) {
        var existReceivable = receivableRepository.findById(id);

        if(existReceivable.isPresent()){
            receivableRepository.deleteById(id);
            return "Recebível deletado com sucesso!";
        } else {
            return "Recebível não encontrado";
        }
    }

    @Override
    public String deleteAssignor(UUID id) {
        var existAssignor = assignorRepository.findById(id);

        if(existAssignor.isPresent()){
            assignorRepository.deleteById(id);
            return "Cedente deletado com sucesso!";
        } else {
            return "Cedente não encontrado";
        }
    }
    @Override
    public ReceivableResponseDTO registerReceivable(ReceivableDTO receivableDTO){
        Receivable receivable = convertReceivableToModel(receivableDTO);
        receivableRepository.save(receivable);

        ReceivableResponseDTO receivableResponseDTO = new ReceivableResponseDTO();
        receivableResponseDTO.setReceivableValue(receivable.getReceivableValue());
        receivableResponseDTO.setEmissionDate(receivable.getEmissionDate());

        return receivableResponseDTO;
    }

    @Override
    public AssignorResponseDTO registerAssignor(AssignorDTO assignorDTO){
        Assignor assignor = convertAssignorToModel(assignorDTO);
        assignorRepository.save(assignor);

        AssignorResponseDTO assignorResponseDTO = new AssignorResponseDTO();
        assignorResponseDTO.setDocument(assignor.getDocument());
        assignorResponseDTO.setEmail(assignor.getEmail());
        assignorResponseDTO.setName(assignor.getName());
        assignorResponseDTO.setPhone(assignor.getPhone());

        return assignorResponseDTO;
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
