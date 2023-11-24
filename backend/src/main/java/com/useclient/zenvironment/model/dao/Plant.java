package com.useclient.zenvironment.model.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Plant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(optional = false)
    private PlantType plantType;
    @ManyToOne
    private Garden garden;
    private double estimatedProducedOxygenInKilograms;
    private double estimatedFixatedCO2InKilograms;
    private LocalDate plantedAt;
    private LocalDate uprootedAt;
}
