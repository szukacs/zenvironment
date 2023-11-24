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
    public static final String MY_COMMUNITY_NAME = "Your Friendly Neighborhood";

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

        var tomatoOxygenProductionPerDay = 0.15;
        var tomatoCO2FixationPerDay = 0.1;
        var tomato = new PlantType(
                null,
                "Tomato",
                "https://images.unsplash.com/photo-1592841200221-a6898f307baa",
                tomatoOxygenProductionPerDay,
                tomatoCO2FixationPerDay, "kg");
        tomato = plantTypeRepository.save(tomato);

        var community = new Community(MY_COMMUNITY_NAME);
        community = communityRepository.save(community);

        var garden = new Garden(MY_GARDEN_NAME, community);
        garden = gardenRepository.save(garden);

        var tomatoPlant1 = new Plant(null, tomato, garden, 60 * tomatoOxygenProductionPerDay, 60 * tomatoCO2FixationPerDay, LocalDate.now().minusDays(60), null);
        tomatoPlant1 = plantRepository.save(tomatoPlant1);

        var tomatoPlant2 = new Plant(null, tomato, garden, 120 * tomatoOxygenProductionPerDay, 120 * tomatoCO2FixationPerDay, LocalDate.now().minusDays(120), null);
        tomatoPlant2 = plantRepository.save(tomatoPlant2);

        harvestRepository.save(new Harvest(null, tomatoPlant1, 1, LocalDate.now().minusDays(5)));
        harvestRepository.save(new Harvest(null, tomatoPlant2, 2, LocalDate.now().minusDays(15)));
        harvestRepository.save(new Harvest(null, tomatoPlant2, 2.5, LocalDate.now().minusDays(3)));

        return "hello!";
    }
}
