package com.useclient.zenvironment.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class NewPlantDto {
    private UUID plantTypeId;
    private LocalDate plantedAt;
}
