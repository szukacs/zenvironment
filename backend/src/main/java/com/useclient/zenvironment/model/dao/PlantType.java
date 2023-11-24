package com.useclient.zenvironment.model.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlantType {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    @Column(length = 1000)
    private String imageUrl;
    private double averageOxygenProductionInKilogramsPerDay;
    private double averageCO2FixationInKilogramsPerDay;
    private String harvestUnit;

}
