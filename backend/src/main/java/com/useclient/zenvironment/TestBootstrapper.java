package com.useclient.zenvironment;

import com.useclient.zenvironment.model.dao.*;
import com.useclient.zenvironment.model.dao.challenge.Challenge;
import com.useclient.zenvironment.model.dao.challenge.ChallengeType;
import com.useclient.zenvironment.repository.*;
import jakarta.transaction.Transactional;
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
    private final ChallengeRepository challengeRepository;

    @Bean
    @Transactional
    public String bootstrapTestData() {
        harvestRepository.deleteAll();
        plantRepository.deleteAll();
        communityRepository.deleteAll();
        challengeRepository.deleteAll();
        gardenRepository.deleteAll();
        plantTypeRepository.deleteAll();

        var tomato = PlantType.builder()
            .name("tomato")
            .imageUrl( "/fruit/tomato.png")
            .averageOxygenProductionInKilogramsPerDay(0.0085)
            .averageCO2FixationInKilogramsPerDay(0.125)
            .waterInLiterPerWeek(6)
            .averageCropPerCycle(3.5)
            .averageMonthsUntilHarvest(3)
            .harvestUnit("kg")
            .build();

        var onion = PlantType.builder()
            .name("onion")
            .imageUrl( "/fruit/onion.jpg")
            .averageOxygenProductionInKilogramsPerDay(0.006)
            .averageCO2FixationInKilogramsPerDay(0.0075)
            .waterInLiterPerWeek(2.5)
            .averageCropPerCycle(0.75)
            .averageMonthsUntilHarvest(3.5)
            .harvestUnit("kg")
            .build();

        var lettuce = PlantType.builder()
            .name("lettuce")
            .imageUrl( "/fruit/lettuce.jpg")
            .averageOxygenProductionInKilogramsPerDay(0.0065)
            .averageCO2FixationInKilogramsPerDay(0.0075)
            .waterInLiterPerWeek(3.5)
            .averageCropPerCycle(0.35)
            .averageMonthsUntilHarvest(1.5)
            .harvestUnit("kg")
            .build();

        var paprika = PlantType.builder()
            .name("paprika")
            .imageUrl( "/fruit/paprika.jpg")
            .averageOxygenProductionInKilogramsPerDay(0.0075)
            .averageCO2FixationInKilogramsPerDay(0.125)
            .waterInLiterPerWeek(4.5)
            .averageCropPerCycle(1.5)
            .averageMonthsUntilHarvest(2.5)
            .harvestUnit("kg")
            .build();

        var spinach = PlantType.builder()
            .name("spinach")
            .imageUrl( "/fruit/spinach.jpg")
            .averageOxygenProductionInKilogramsPerDay(0.0085)
            .averageCO2FixationInKilogramsPerDay(0.125)
            .waterInLiterPerWeek(3.5)
            .averageCropPerCycle(0.2)
            .averageMonthsUntilHarvest(1)
            .harvestUnit("kg")
            .build();

        var wheat = PlantType.builder()
            .name("wheat")
            .imageUrl( "/fruit/wheat.jpg")
            .averageOxygenProductionInKilogramsPerDay(0.009)
            .averageCO2FixationInKilogramsPerDay(0.175)
            .waterInLiterPerWeek(7.5)
            .averageCropPerCycle(0.75)
            .averageMonthsUntilHarvest(3)
            .harvestUnit("kg")
            .build();

        var cucumber = PlantType.builder()
            .name("cucumber")
            .imageUrl( "/fruit/cucumber.jpg")
            .averageOxygenProductionInKilogramsPerDay(0.009)
            .averageCO2FixationInKilogramsPerDay(0.175)
            .waterInLiterPerWeek(7)
            .averageCropPerCycle(1.5)
            .averageMonthsUntilHarvest(2)
            .harvestUnit("kg")
            .build();

        var sunFlower = PlantType.builder()
            .name("sunflower")
            .imageUrl( "/fruit/sunFlower.jpg")
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
        for (ChallengeType challengeType : ChallengeType.values()) {
            challengeRepository.save(new Challenge(community, challengeType));
        }

        var garden = new Garden(MY_GARDEN_NAME, community);
        garden.setImageUrl("/profile/gardenProfile.jpg");
        garden = gardenRepository.save(garden);

        var garden2 = new Garden(MY_GARDEN_NAME_2, community);
        garden2.setImageUrl("/profile/garden2Profile.jpg");
        garden2 = gardenRepository.save(garden2);

        var tomatoPlant1 = Plant.builder()
            .plantType(tomato)
            .garden(garden)
            .x(3)
            .y(2)
            .plantedAt(LocalDate.now().minusDays(60))
            .build();

        var tomatoPlant3 = Plant.builder()
            .plantType(tomato)
            .garden(garden)
            .x(4)
            .y(2)
            .plantedAt(LocalDate.now().minusDays(60))
            .build();

        var tomatoPlant2 = Plant.builder()
            .plantType(tomato)
            .garden(garden2)
            .x(4)
            .y(1)
            .plantedAt(LocalDate.now().minusDays(120))
            .build();

        var onionPlant1 = Plant.builder()
            .plantType(onion)
            .garden(garden)
            .x(3)
            .y(4)
            .plantedAt(LocalDate.now().minusDays(50))
            .build();

        var onionPlant2 = Plant.builder()
            .plantType(onion)
            .garden(garden2)
            .x(4)
            .y(0)
            .plantedAt(LocalDate.now().minusDays(40))
            .build();

        var lettucePlant = Plant.builder()
            .plantType(lettuce)
            .garden(garden)
            .x(2)
            .y(4)
            .plantedAt(LocalDate.now().minusDays(30))
            .build();

        var lettucePlant2 = Plant.builder()
            .plantType(lettuce)
            .garden(garden2)
            .x(1)
            .y(2)
            .plantedAt(LocalDate.now().minusDays(50))
            .build();

        var paprikaPlant = Plant.builder()
            .plantType(paprika)
            .garden(garden)
            .x(1)
            .y(2)
            .plantedAt(LocalDate.now().minusDays(35))
            .build();

        var paprikaPlant2 = Plant.builder()
            .plantType(paprika)
            .garden(garden2)
            .x(2)
            .y(4)
            .plantedAt(LocalDate.now().minusDays(40))
            .build();

        var spinachPlant = Plant.builder()
            .plantType(spinach)
            .garden(garden)
            .x(1)
            .y(1)
            .plantedAt(LocalDate.now().minusDays(20))
            .build();

        var spinachPlant2 = Plant.builder()
            .plantType(spinach)
            .garden(garden2)
            .x(1)
            .y(1)
            .plantedAt(LocalDate.now().minusDays(40))
            .build();

        var sunFlowerPlant = Plant.builder()
            .plantType(sunFlower)
            .garden(garden)
            .x(0)
            .y(0)
            .plantedAt(LocalDate.now().minusDays(50))
            .build();

        var sunFlowerPlant2 = Plant.builder()
            .plantType(sunFlower)
            .garden(garden2)
            .x(3)
            .y(3)
            .plantedAt(LocalDate.now().minusDays(20))
            .build();

        var wheatPlant = Plant.builder()
            .plantType(wheat)
            .garden(garden)
            .x(1)
            .y(3)
            .plantedAt(LocalDate.now().minusDays(10))
            .build();

        var wheatPlant2 = Plant.builder()
            .plantType(wheat)
            .garden(garden2)
            .x(2)
            .y(2)
            .plantedAt(LocalDate.now().minusDays(25))
            .build();

        var cucumberPlant = Plant.builder()
            .plantType(cucumber)
            .garden(garden)
            .x(0)
            .y(3)
            .plantedAt(LocalDate.now().minusDays(35))
            .build();

        var cucumberPlant2 = Plant.builder()
            .plantType(cucumber)
            .garden(garden2)
            .x(0)
            .y(2)
            .plantedAt(LocalDate.now().minusDays(15))
            .build();

        plantRepository.save(tomatoPlant1);
        plantRepository.save(tomatoPlant2);
        plantRepository.save(tomatoPlant3);
        plantRepository.save(cucumberPlant);
        plantRepository.save(cucumberPlant2);
        plantRepository.save(sunFlowerPlant2);
        plantRepository.save(sunFlowerPlant);
        plantRepository.save(wheatPlant);
        plantRepository.save(wheatPlant2);
        plantRepository.save(spinachPlant);
        plantRepository.save(spinachPlant2);
        plantRepository.save(lettucePlant);
        plantRepository.save(lettucePlant2);
        plantRepository.save(paprikaPlant);
        plantRepository.save(paprikaPlant2);
        plantRepository.save(onionPlant1);
        plantRepository.save(onionPlant2);

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

    @Bean
    @Transactional
    public String postProcessTestData(String bootstrapTestData) {
        challengeRepository.findAll()
                .stream().peek(Challenge::calibrateChallengeLevel)
                .forEach(challengeRepository::save);
        return bootstrapTestData + " world!";
    }
}
