package com.useclient.zenvironment.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class CommunityDto {
    private UUID id;
    private String name;
    private double allProducedOxygenInKilograms;
    private double allFixatedCO2InKilograms;
    private List<GardenDto> gardens;
}
