package com.useclient.zenvironment.model.dao;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static java.time.temporal.ChronoUnit.DAYS;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        name = "plant",
        indexes = {
                @Index(name = "idx_unique_location_in_garden", columnList = "garden_id, x, y", unique = true)
        }
)
public class Plant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(optional = false)
    private PlantType plantType;
    @ManyToOne
    private Garden garden;
    private int x;
    private int y;
    private LocalDate plantedAt;
    private LocalDate uprootedAt;
    @OneToMany(mappedBy = "plant")
    private List<Harvest> harvests = new ArrayList<>();

    public double getAllProducedOxygenInKilograms(){
        return this.plantType.getAverageOxygenProductionInKilogramsPerDay() * DAYS.between(plantedAt, LocalDate.now());
    }

    public double getAllFixatedCO2InKilograms(){
        return this.plantType.getAverageCO2FixationInKilogramsPerDay() * DAYS.between(plantedAt, LocalDate.now());
    }

    public double getAllWaterConsumptionInLiters(){
        return this.plantType.getWaterInLiterPerWeek() / 7 * DAYS.between(plantedAt, LocalDate.now());
    }

    public int getDaysTillHarvest(){
        return (int) ((int) Math.round(this.plantType.getAverageMonthsUntilHarvest() * 30) - DAYS.between(plantedAt, LocalDate.now()));
    }

    public double getAllHarvestedAmount() {
        if (CollectionUtils.isEmpty(harvests)) return 0.0;
        return harvests.stream()
                .map(Harvest::getAmount)
                .reduce(0.0, Double::sum);
    }
}
