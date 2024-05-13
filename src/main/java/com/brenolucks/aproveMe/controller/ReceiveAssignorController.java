package com.brenolucks.aproveMe.controller;

import com.brenolucks.aproveMe.dto.*;
import com.brenolucks.aproveMe.model.Assignor;
import com.brenolucks.aproveMe.model.Receivable;
import com.brenolucks.aproveMe.services.ReceivableAssignorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class ReceiveAssignorController {

    private final ReceivableAssignorService receivableAssignorService;

    public ReceiveAssignorController(ReceivableAssignorService receivableAssignorService) {
        this.receivableAssignorService = receivableAssignorService;
    }

    @PostMapping("/integrations/payable")
    public ResponseEntity<ReceivableAndAssignorResponseDTO> createReceivableAndAssignor(@RequestBody ReceivableAndAssignorRequestDTO request) {
        ReceivableDTO receivable = request.getReceivable();
        AssignorDTO assignor = request.getAssignor();

        return ResponseEntity.status(HttpStatus.CREATED).body(receivableAssignorService.registerReceivableAndAssignor(request));
    }

    @GetMapping("/integrations/payable/{id}")
    public ResponseEntity<Optional<Receivable>> getReceivableById(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(receivableAssignorService.getReceivableById(id));
    }

    @GetMapping("/integrations/assignor/{id}")
    public ResponseEntity<Optional<Assignor>> getAssignorById(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(receivableAssignorService.getAssignorById(id));
    }

    @PutMapping("/integrations/receivable/{id}")
    public ResponseEntity<Receivable> updateReceivable(@RequestBody Receivable request, @PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(receivableAssignorService.updateReceivable(id, request));
    }

    @PutMapping("/integrations/assignor/{id}")
    public ResponseEntity<Assignor> updateAssignor(@RequestBody Assignor request, @PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(receivableAssignorService.updateAssignor(id, request));
    }

    @DeleteMapping("/integrations/receivable/{id}")
    public ResponseEntity<String> deleteReceivable(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(receivableAssignorService.deleteReceivable(id));
    }

    @DeleteMapping("/integrations/assignor/{id}")
    public ResponseEntity<String> deleteAssignor(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(receivableAssignorService.deleteAssignor(id));
    }

    @PostMapping("/integrations/receivable")
    public ResponseEntity<ReceivableResponseDTO> registerReceivable(@RequestBody ReceivableDTO request){
        return ResponseEntity.status(HttpStatus.CREATED).body(receivableAssignorService.registerReceivable(request));
    }

    @PostMapping("/integrations/assignor")
    public ResponseEntity<AssignorResponseDTO> registerAssignor(@RequestBody AssignorDTO request){
        return ResponseEntity.status(HttpStatus.CREATED).body(receivableAssignorService.registerAssignor(request));
    }
}