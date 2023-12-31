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
import java.time.LocalDateTime;

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
    private final ExchangeRepository exchangeRepository;

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
            .name("Tomato")
            .imageUrl( "/fruit/tomato.png")
                .averageOxygenProductionInKilogramsPerDay(0.08)
                .averageCO2FixationInKilogramsPerDay(0.09)
            .waterInLiterPerWeek(6)
            .averageCropPerCycle(3.5)
            .averageMonthsUntilHarvest(3)
            .harvestUnit("kg")
            .build();

        var lettuce = PlantType.builder()
            .name("Lettuce")
            .imageUrl( "/fruit/lettuce.png")
            .averageOxygenProductionInKilogramsPerDay(0.011)
            .averageCO2FixationInKilogramsPerDay(0.013)
            .waterInLiterPerWeek(3.5)
            .averageCropPerCycle(0.35)
            .averageMonthsUntilHarvest(1.5)
            .harvestUnit("kg")
            .build();

        var paprika = PlantType.builder()
            .name("Paprika")
            .imageUrl( "/fruit/paprika.png")
            .averageOxygenProductionInKilogramsPerDay(0.023)
            .averageCO2FixationInKilogramsPerDay(0.012)
            .waterInLiterPerWeek(4.5)
            .averageCropPerCycle(1.5)
            .averageMonthsUntilHarvest(2.5)
            .harvestUnit("kg")
            .build();

        var pea = PlantType.builder()
            .name("Pea")
            .imageUrl( "/fruit/pea.png")
            .averageOxygenProductionInKilogramsPerDay(0.032)
            .averageCO2FixationInKilogramsPerDay(0.009)
            .waterInLiterPerWeek(7)
            .averageCropPerCycle(1.5)
            .averageMonthsUntilHarvest(2)
            .harvestUnit("kg")
            .build();

        var sunFlower = PlantType.builder()
            .name("Sunflower")
            .imageUrl( "/fruit/sunflower.png")
            .averageOxygenProductionInKilogramsPerDay(0.245)
            .averageCO2FixationInKilogramsPerDay(0.026)
            .waterInLiterPerWeek(5)
            .averageCropPerCycle(0.75)
            .averageMonthsUntilHarvest(3)
            .harvestUnit("kg")
            .build();


        tomato = plantTypeRepository.save(tomato);
        sunFlower = plantTypeRepository.save(sunFlower);
        pea = plantTypeRepository.save(pea);
        paprika = plantTypeRepository.save(paprika);
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

        Exchange exchange = Exchange.builder()
            .garden(garden2)
            .description("I have 3 free 1 month old tomato plants. Feel free to reach out")
            .productImageUrl("/fruit/tomato.png")
            .build();

        Exchange exchange2 = Exchange.builder()
            .garden(garden)
            .description("I have 4 paprikas. Come and get them.")
            .productImageUrl("/fruit/paprika.png")
            .build();

        exchangeRepository.save(exchange);
        exchangeRepository.save(exchange2);

        var tomatoPlant1 = Plant.builder()
            .plantType(tomato)
            .garden(garden)
            .x(3)
            .y(2)
            .plantedAt(LocalDateTime.now().minusDays(60))
            .build();

        var tomatoPlant3 = Plant.builder()
            .plantType(tomato)
            .garden(garden)
            .x(4)
            .y(2)
            .plantedAt(LocalDateTime.now().minusDays(60))
            .build();

        var tomatoPlant2 = Plant.builder()
            .plantType(tomato)
            .garden(garden2)
            .x(4)
            .y(1)
            .plantedAt(LocalDateTime.now().minusDays(120))
            .build();

        var lettucePlant = Plant.builder()
            .plantType(lettuce)
            .garden(garden)
            .x(2)
            .y(4)
            .plantedAt(LocalDateTime.now().minusDays(30))
            .build();

        var lettucePlant2 = Plant.builder()
            .plantType(lettuce)
            .garden(garden2)
            .x(1)
            .y(2)
            .plantedAt(LocalDateTime.now().minusDays(50))
            .build();

        var paprikaPlant = Plant.builder()
            .plantType(paprika)
            .garden(garden)
            .x(1)
            .y(2)
            .plantedAt(LocalDateTime.now().minusDays(35))
            .build();

        var paprikaPlant2 = Plant.builder()
            .plantType(paprika)
            .garden(garden2)
            .x(2)
            .y(4)
            .plantedAt(LocalDateTime.now().minusDays(40))
            .build();

        var sunFlowerPlant = Plant.builder()
            .plantType(sunFlower)
            .garden(garden)
            .x(0)
            .y(0)
            .plantedAt(LocalDateTime.now().minusDays(50))
            .build();

        var sunFlowerPlant2 = Plant.builder()
            .plantType(sunFlower)
            .garden(garden2)
            .x(3)
            .y(3)
            .plantedAt(LocalDateTime.now().minusDays(20))
            .build();

        var peaPlant = Plant.builder()
            .plantType(pea)
            .garden(garden)
            .x(0)
            .y(3)
            .plantedAt(LocalDateTime.now().minusDays(35))
            .build();

        var peaPlant2 = Plant.builder()
            .plantType(pea)
            .garden(garden2)
            .x(0)
            .y(2)
            .plantedAt(LocalDateTime.now().minusDays(15))
            .build();


        plantRepository.save(tomatoPlant1);
        plantRepository.save(tomatoPlant2);
        plantRepository.save(tomatoPlant3);
        plantRepository.save(peaPlant);
        plantRepository.save(peaPlant2);
        plantRepository.save(sunFlowerPlant2);
        plantRepository.save(sunFlowerPlant);
        plantRepository.save(lettucePlant);
        plantRepository.save(lettucePlant2);
        plantRepository.save(paprikaPlant);
        plantRepository.save(paprikaPlant2);

        harvestRepository.save(new Harvest(null, tomatoPlant1, 1, LocalDate.now().minusDays(5)));
        harvestRepository.save(new Harvest(null, tomatoPlant2, 2, LocalDate.now().minusDays(15)));
        harvestRepository.save(new Harvest(null, tomatoPlant2, 2.5, LocalDate.now().minusDays(3)));
        harvestRepository.save(new Harvest(null, peaPlant, 0.5, LocalDate.now().minusDays(3)));
        harvestRepository.save(new Harvest(null, peaPlant2, 1.5, LocalDate.now().minusDays(16)));
        harvestRepository.save(new Harvest(null, peaPlant2, 1.5, LocalDate.now().minusDays(3)));
        harvestRepository.save(new Harvest(null, paprikaPlant, 1.5, LocalDate.now().minusDays(16)));
        harvestRepository.save(new Harvest(null, paprikaPlant, 1.0, LocalDate.now().minusDays(1)));
        harvestRepository.save(new Harvest(null, paprikaPlant2, 1.75, LocalDate.now().minusDays(3)));
        harvestRepository.save(new Harvest(null, lettucePlant, 0.1, LocalDate.now().minusDays(5)));
        harvestRepository.save(new Harvest(null, lettucePlant2, 0.1, LocalDate.now().minusDays(6)));
        harvestRepository.save(new Harvest(null, lettucePlant2, 0.05, LocalDate.now().minusDays(1)));
        harvestRepository.save(new Harvest(null, sunFlowerPlant, 0.5, LocalDate.now().minusDays(12)));
        harvestRepository.save(new Harvest(null, sunFlowerPlant2, 0.25, LocalDate.now().minusDays(7)));

        return "hello!";
    }
}
