package com.useclient.zenvironment.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class NewHarvestDto {
    private double amount;
    private LocalDate harvestDate;
}
