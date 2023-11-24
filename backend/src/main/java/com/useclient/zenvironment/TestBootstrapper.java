package com.useclient.zenvironment;

import com.useclient.zenvironment.model.dao.*;
import com.useclient.zenvironment.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
@RequiredArgsConstructor
public class TestBootstrapper {
    public static final String MY_GARDEN_NAME = "Gergely's Balcony";
    public static final String MY_GARDEN_NAME_2 = "Marci's much nicer Balcony";
    public static final String MY_COMMUNITY_NAME = "Your Brotherly Neighborhood";

    private final PlantTypeRepository plantTypeRepository;
    private final GardenRepository gardenRepository;
    private final PlantRepository plantRepository;
    private final HarvestRepository harvestRepository;
    private final CommunityRepository communityRepository;

    @Bean
    public String bootstrapTestData() {
        harvestRepository.deleteAll();
        plantRepository.deleteAll();
        communityRepository.deleteAll();
        gardenRepository.deleteAll();
        plantTypeRepository.deleteAll();

        var tomato = PlantType.builder()
            .name("tomato")
            .imageUrl( "https://images.unsplash.com/photo-1592841200221-a6898f307baa")
            .averageOxygenProductionInKilogramsPerDay(0.0085)
            .averageCO2FixationInKilogramsPerDay(0.125)
            .waterInLiterPerWeek(6)
            .averageCropPerCycle(3.5)
            .averageMonthsUntilHarvest(3)
            .harvestUnit("kg")
            .build();

        var onion = PlantType.builder()
            .name("onion")
            .imageUrl( "https://images.unsplash.com/photo-1587049633312-d628ae50a8ae")
            .averageOxygenProductionInKilogramsPerDay(0.006)
            .averageCO2FixationInKilogramsPerDay(0.0075)
            .waterInLiterPerWeek(2.5)
            .averageCropPerCycle(0.75)
            .averageMonthsUntilHarvest(3.5)
            .harvestUnit("kg")
            .build();

        var lettuce = PlantType.builder()
            .name("lettuce")
            .imageUrl( "https://images.unsplash.com/photo-1622206151226-18ca2c9ab4a1")
            .averageOxygenProductionInKilogramsPerDay(0.0065)
            .averageCO2FixationInKilogramsPerDay(0.0075)
            .waterInLiterPerWeek(3.5)
            .averageCropPerCycle(0.35)
            .averageMonthsUntilHarvest(1.5)
            .harvestUnit("kg")
            .build();

        var paprika = PlantType.builder()
            .name("paprika")
            .imageUrl( "https://images.unsplash.com/photo-1614260025937-b4ecb6eb9165")
            .averageOxygenProductionInKilogramsPerDay(0.0075)
            .averageCO2FixationInKilogramsPerDay(0.125)
            .waterInLiterPerWeek(4.5)
            .averageCropPerCycle(1.5)
            .averageMonthsUntilHarvest(2.5)
            .harvestUnit("kg")
            .build();

        var spinach = PlantType.builder()
            .name("spinach")
            .imageUrl( "https://images.unsplash.com/photo-1576045057995-568f588f82fb")
            .averageOxygenProductionInKilogramsPerDay(0.0085)
            .averageCO2FixationInKilogramsPerDay(0.125)
            .waterInLiterPerWeek(3.5)
            .averageCropPerCycle(0.2)
            .averageMonthsUntilHarvest(1)
            .harvestUnit("kg")
            .build();

        var wheat = PlantType.builder()
            .name("wheat")
            .imageUrl( "https://images.unsplash.com/photo-1564493031643-4be2c4347a17")
            .averageOxygenProductionInKilogramsPerDay(0.009)
            .averageCO2FixationInKilogramsPerDay(0.175)
            .waterInLiterPerWeek(7.5)
            .averageCropPerCycle(0.75)
            .averageMonthsUntilHarvest(3)
            .harvestUnit("kg")
            .build();

        var cucumber = PlantType.builder()
            .name("cucumber")
            .imageUrl( "https://images.unsplash.com/photo-1449300079323-02e209d9d3a6")
            .averageOxygenProductionInKilogramsPerDay(0.009)
            .averageCO2FixationInKilogramsPerDay(0.175)
            .waterInLiterPerWeek(7)
            .averageCropPerCycle(1.5)
            .averageMonthsUntilHarvest(2)
            .harvestUnit("kg")
            .build();

        var sunFlower = PlantType.builder()
            .name("sunflower")
            .imageUrl( "https://images.unsplash.com/photo-1597848212624-a19eb35e2651")
            .averageOxygenProductionInKilogramsPerDay(0.009)
            .averageCO2FixationInKilogramsPerDay(0.25)
            .waterInLiterPerWeek(5)
            .averageCropPerCycle(0.75)
            .averageMonthsUntilHarvest(3)
            .harvestUnit("kg")
            .build();


        tomato = plantTypeRepository.save(tomato);
        sunFlower = plantTypeRepository.save(sunFlower);
        cucumber = plantTypeRepository.save(cucumber);
        paprika = plantTypeRepository.save(paprika);
        wheat = plantTypeRepository.save(wheat);
        onion = plantTypeRepository.save(onion);
        spinach = plantTypeRepository.save(spinach);
        lettuce = plantTypeRepository.save(lettuce);

        var community = new Community(MY_COMMUNITY_NAME);
        community = communityRepository.save(community);

        var garden = new Garden(MY_GARDEN_NAME, community);
        garden = gardenRepository.save(garden);

        var garden2 = new Garden(MY_GARDEN_NAME_2, community);
        garden2 = gardenRepository.save(garden2);

        var tomatoPlant1 = Plant.builder()
            .plantType(tomato)
            .garden(garden)
            .plantedAt(LocalDate.now().minusDays(60))
            .build();

        var tomatoPlant2 = Plant.builder()
            .plantType(tomato)
            .garden(garden2)
            .plantedAt(LocalDate.now().minusDays(120))
            .build();

        var onionPlant1 = Plant.builder()
            .plantType(onion)
            .garden(garden)
            .plantedAt(LocalDate.now().minusDays(50))
            .build();


        var onionPlant2 = Plant.builder()
            .plantType(onion)
            .garden(garden2)
            .plantedAt(LocalDate.now().minusDays(40))
            .build();

        var lettucePlant = Plant.builder()
            .plantType(lettuce)
            .garden(garden)
            .plantedAt(LocalDate.now().minusDays(30))
            .build();

        var lettucePlant2 = Plant.builder()
            .plantType(lettuce)
            .garden(garden2)
            .plantedAt(LocalDate.now().minusDays(50))
            .build();

        var paprikaPlant = Plant.builder()
            .plantType(paprika)
            .garden(garden)
            .plantedAt(LocalDate.now().minusDays(35))
            .build();

        var paprikaPlant2 = Plant.builder()
            .plantType(paprika)
            .garden(garden2)
            .plantedAt(LocalDate.now().minusDays(40))
            .build();

        var spinachPlant = Plant.builder()
            .plantType(spinach)
            .garden(garden)
            .plantedAt(LocalDate.now().minusDays(20))
            .build();

        var spinachPlant2 = Plant.builder()
            .plantType(spinach)
            .garden(garden2)
            .plantedAt(LocalDate.now().minusDays(40))
            .build();

        var sunFlowerPlant = Plant.builder()
            .plantType(sunFlower)
            .garden(garden)
            .plantedAt(LocalDate.now().minusDays(50))
            .build();

        var sunFlowerPlant2 = Plant.builder()
            .plantType(sunFlower)
            .garden(garden2)
            .plantedAt(LocalDate.now().minusDays(20))
            .build();

        var wheatPlant = Plant.builder()
            .plantType(wheat)
            .garden(garden)
            .plantedAt(LocalDate.now().minusDays(10))
            .build();

        var wheatPlant2 = Plant.builder()
            .plantType(wheat)
            .garden(garden2)
            .plantedAt(LocalDate.now().minusDays(25))
            .build();

        var cucumberPlant = Plant.builder()
            .plantType(cucumber)
            .garden(garden)
            .plantedAt(LocalDate.now().minusDays(35))
            .build();

        var cucumberPlant2 = Plant.builder()
            .plantType(cucumber)
            .garden(garden2)
            .plantedAt(LocalDate.now().minusDays(15))
            .build();

        harvestRepository.save(new Harvest(null, tomatoPlant1, 1, LocalDate.now().minusDays(5)));
        harvestRepository.save(new Harvest(null, tomatoPlant2, 2, LocalDate.now().minusDays(15)));
        harvestRepository.save(new Harvest(null, tomatoPlant2, 2.5, LocalDate.now().minusDays(3)));
        harvestRepository.save(new Harvest(null, cucumberPlant, 0.5, LocalDate.now().minusDays(3)));
        harvestRepository.save(new Harvest(null, cucumberPlant2, 1.5, LocalDate.now().minusDays(16)));
        harvestRepository.save(new Harvest(null, cucumberPlant2, 1.5, LocalDate.now().minusDays(3)));
        harvestRepository.save(new Harvest(null, wheatPlant, 1.5, LocalDate.now().minusDays(2)));
        harvestRepository.save(new Harvest(null, wheatPlant2, 2.5, LocalDate.now().minusDays(7)));
        harvestRepository.save(new Harvest(null, paprikaPlant, 1.5, LocalDate.now().minusDays(16)));
        harvestRepository.save(new Harvest(null, paprikaPlant, 1.0, LocalDate.now().minusDays(1)));
        harvestRepository.save(new Harvest(null, paprikaPlant2, 1.75, LocalDate.now().minusDays(3)));
        harvestRepository.save(new Harvest(null, lettucePlant, 0.1, LocalDate.now().minusDays(5)));
        harvestRepository.save(new Harvest(null, lettucePlant2, 0.1, LocalDate.now().minusDays(6)));
        harvestRepository.save(new Harvest(null, lettucePlant2, 0.05, LocalDate.now().minusDays(1)));
        harvestRepository.save(new Harvest(null, spinachPlant, 1.5, LocalDate.now().minusDays(15)));
        harvestRepository.save(new Harvest(null, spinachPlant2, 2.5, LocalDate.now().minusDays(10)));
        harvestRepository.save(new Harvest(null, sunFlowerPlant, 0.5, LocalDate.now().minusDays(12)));
        harvestRepository.save(new Harvest(null, sunFlowerPlant2, 0.25, LocalDate.now().minusDays(7)));
        harvestRepository.save(new Harvest(null, onionPlant1, 1.5, LocalDate.now().minusDays(15)));
        harvestRepository.save(new Harvest(null, onionPlant2, 2.0, LocalDate.now().minusDays(10)));

        return "hello!";
    }
}
