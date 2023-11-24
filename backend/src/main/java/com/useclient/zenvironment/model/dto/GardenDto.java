package com.useclient.zenvironment.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class GardenDto {
    private UUID id;
    private String name;
    private List<PlantDto> plants;
}
