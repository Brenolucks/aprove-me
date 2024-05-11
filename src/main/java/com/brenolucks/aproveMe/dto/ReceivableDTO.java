package com.brenolucks.aproveMe.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ReceivableDTO {
    private float receivableValue;
    private Date emissionDate;
}
