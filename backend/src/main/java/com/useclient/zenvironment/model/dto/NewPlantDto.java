package com.useclient.zenvironment.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class NewPlantDto {
    private int x;
    private int y;
    private UUID plantTypeId;
    private LocalDateTime plantedAt;
}
