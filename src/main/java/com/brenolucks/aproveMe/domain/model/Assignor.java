package com.brenolucks.aproveMe.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Entity(name = "assignor")
@Table(name = "assignor")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Assignor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
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
