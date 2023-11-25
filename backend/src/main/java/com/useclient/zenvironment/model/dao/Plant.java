package com.useclient.zenvironment.model.dao;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.SECONDS;

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
    private LocalDateTime plantedAt = LocalDateTime.now();
    @OneToMany(mappedBy = "plant")
    private List<Harvest> harvests = new ArrayList<>();

    public double getAllProducedOxygenInKilograms(){
        return (this.plantType.getAverageOxygenProductionInKilogramsPerDay() / 24.0 / 60.0 / 60.0) * SECONDS.between(plantedAt, LocalDateTime.now());
    }

    public double getAllFixatedCO2InKilograms(){
        return (this.plantType.getAverageCO2FixationInKilogramsPerDay() / 24.0 / 60.0 / 60.0) * SECONDS.between(plantedAt, LocalDateTime.now());
    }

    public double getAllWaterConsumptionInLiters(){
        return this.plantType.getWaterInLiterPerWeek() / 7 * DAYS.between(plantedAt, LocalDateTime.now());
    }

    public int getDaysTillHarvest(){
        return (int) ((int) Math.round(this.plantType.getAverageMonthsUntilHarvest() * 30) - DAYS.between(plantedAt, LocalDateTime.now()));
    }

    public double getAllHarvestedAmount() {
        if (CollectionUtils.isEmpty(harvests)) return 0.0;
        return harvests.stream()
                .map(Harvest::getAmount)
                .reduce(0.0, Double::sum);
    }
}
