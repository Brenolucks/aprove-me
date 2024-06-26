package com.brenolucks.aproveMe.controller;

import com.brenolucks.aproveMe.domain.mapper.AssignorMapper;
import com.brenolucks.aproveMe.domain.mapper.ReceivableMapper;
import com.brenolucks.aproveMe.dto.*;
import com.brenolucks.aproveMe.dto.assignor.AssignorRequestDTO;
import com.brenolucks.aproveMe.dto.assignor.AssignorResponseDTO;
import com.brenolucks.aproveMe.dto.receivable.ReceivableRequestDTO;
import com.brenolucks.aproveMe.dto.receivable.ReceivableResponseDTO;
import com.brenolucks.aproveMe.services.ReceivableAssignorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("integrations")
public class ReceiveAssignorController {
    private final ReceivableAssignorService receivableAssignorService;

    public ReceiveAssignorController(ReceivableAssignorService receivableAssignorService, ReceivableMapper receivableMapper, AssignorMapper assignorMapper) {
        this.receivableAssignorService = receivableAssignorService;
    }

    @PostMapping("/payable")
    public ResponseEntity<ReceivableAndAssignorResponseDTO> createReceivableAndAssignor(@RequestBody ReceivableAndAssignorRequestDTO request) {
        ReceivableRequestDTO receivable = request.receivableRequestDTO();
        AssignorRequestDTO assignor = request.assignorRequestDTO();

        return ResponseEntity.status(HttpStatus.CREATED).body(receivableAssignorService.registerReceivableAndAssignor(request));
    }

    @GetMapping("/receivable/{id}")
    public ResponseEntity<ReceivableResponseDTO> getReceivableById(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(receivableAssignorService.getReceivableById(id));
    }

    @GetMapping("/assignor/{id}")
    public ResponseEntity<AssignorResponseDTO> getAssignorById(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(receivableAssignorService.getAssignorById(id));
    }

    @PutMapping("/edit/receivable/{id}")
    public ResponseEntity<ReceivableResponseDTO> updateReceivable(@RequestBody ReceivableRequestDTO receivableRequestDTO, @PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(receivableAssignorService.updateReceivable(id, receivableRequestDTO));
    }

    @PutMapping("/edit/assignor/{id}")
    public ResponseEntity<AssignorResponseDTO> updateAssignor(@RequestBody AssignorRequestDTO assignorRequestDTO, @PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(receivableAssignorService.updateAssignor(id, assignorRequestDTO));
    }

    @DeleteMapping("/delete/receivable/{id}")
    public ResponseEntity<String> deleteReceivable(@PathVariable UUID id){
        receivableAssignorService.deleteReceivable(id);
        return ResponseEntity.status(HttpStatus.OK).body("Recebível deletado com sucesso!");
    }

    @DeleteMapping("/delete/assignor/{id}")
    public ResponseEntity<String> deleteAssignor(@PathVariable UUID id){
        receivableAssignorService.deleteAssignor(id);
        return ResponseEntity.status(HttpStatus.OK).body("Cedente deletado com sucesso!");
    }

    @PostMapping("/register/receivable")
    public ResponseEntity<ReceivableResponseDTO> registerReceivable(@RequestBody ReceivableRequestDTO request){
        return ResponseEntity.status(HttpStatus.CREATED).body(receivableAssignorService.registerReceivable(request));
    }

    @PostMapping("/register/assignor")
    public ResponseEntity<AssignorResponseDTO> registerAssignor(@RequestBody AssignorRequestDTO request){
        return ResponseEntity.status(HttpStatus.CREATED).body(receivableAssignorService.registerAssignor(request));
    }
}