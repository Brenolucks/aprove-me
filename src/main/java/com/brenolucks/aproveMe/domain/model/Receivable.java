package com.brenolucks.aproveMe.domain.model;

import com.brenolucks.aproveMe.dto.receivable.ReceivableRequestDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity(name = "receivable")
@Table(name = "receivable")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Receivable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull(message = "Campo receivableValue está nulo, porfavor preencher o mesmo!")
    @Column(name = "receivable_value")
    private float receivableValue;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "Campo emissionDate está nulo ou possui a formatação errada, verificar se o mesmo está preenchido e se está no formato yyyy-MM-dd!")
    @Column(name = "emission_date")
    private Date emissionDate;

    @Column(name = "assignor")
    private UUID assignorID;
}
