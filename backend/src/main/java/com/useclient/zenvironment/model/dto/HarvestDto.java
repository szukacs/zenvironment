package com.useclient.zenvironment.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class HarvestDto {
    private UUID id;
    private UUID plantId;
    private double amount;
    private String harvestUnit;
    private LocalDate harvestDate;
}
