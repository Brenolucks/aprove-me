package com.brenolucks.aproveMe.controller;

import com.brenolucks.aproveMe.dto.AssignorDTO;
import com.brenolucks.aproveMe.dto.ReceivableAndAssignorRequestDTO;
import com.brenolucks.aproveMe.dto.ReceivableAndAssignorResponseDTO;
import com.brenolucks.aproveMe.dto.ReceivableDTO;
import com.brenolucks.aproveMe.model.Assignor;
import com.brenolucks.aproveMe.model.Receivable;
import com.brenolucks.aproveMe.services.ReceivableAssignorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ReceiveAssignorController {

    private final ReceivableAssignorService receivableAssignorService;

    public ReceiveAssignorController(ReceivableAssignorService receivableAssignorService) {
        this.receivableAssignorService = receivableAssignorService;
    }

    @PostMapping("/integrations/payable")
    public ResponseEntity<ReceivableAndAssignorResponseDTO> createReceivableAndAssignor(@RequestBody ReceivableAndAssignorRequestDTO request) {
        System.out.println("Request: " + request);
        ReceivableDTO receivable = request.getReceivable();
        AssignorDTO assignor = request.getAssignor();
        System.out.println("ReceivableRequest: " + receivable.toString());
        System.out.println("AssignorRequest: " +assignor.toString());

        var teste = receivableAssignorService.registerReceivableAndAssignor(request);
        System.out.println("teste: " +teste);
        return ResponseEntity.status(HttpStatus.CREATED).body(teste);
    }
}
