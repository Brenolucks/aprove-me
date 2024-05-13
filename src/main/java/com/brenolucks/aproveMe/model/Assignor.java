package com.brenolucks.aproveMe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Data
@Table(name = "assignor")
public class Assignor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonIgnore
    private UUID id;

    @NotNull(message = "Campo document está nulo, porfavor preencher o mesmo!")
    @Column(name = "document")
    private String document;

    @NotNull(message = "Campo email está nulo, porfavor preencher o mesmo!")
    @Column(name = "email")
    private String email;

    @NotNull(message = "Campo phone está nulo, porfavor preencher o mesmo!")
    @Column(name = "phone")
    private String phone;

    @NotNull(message = "Campo name está nulo, porfavor preencher o mesmo!")
    @Column(name = "name")
    private String name;
}
