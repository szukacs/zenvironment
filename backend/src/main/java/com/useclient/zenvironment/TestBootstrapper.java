package com.useclient.zenvironment;

import com.useclient.zenvironment.model.dao.Garden;
import com.useclient.zenvironment.model.dao.Plant;
import com.useclient.zenvironment.model.dao.PlantType;
import com.useclient.zenvironment.repository.GardenRepository;
import com.useclient.zenvironment.repository.PlantRepository;
import com.useclient.zenvironment.repository.PlantTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
@RequiredArgsConstructor
public class TestBootstrapper {
    public static final String MY_GARDEN_NAME = "Gergely's Balcony";

    private final PlantTypeRepository plantTypeRepository;
    private final GardenRepository gardenRepository;
    private final PlantRepository plantRepository;

    @Bean
    public String bootstrapTestData() {
        plantRepository.deleteAll();
        gardenRepository.deleteAll();
        plantTypeRepository.deleteAll();

        var tomato = new PlantType(null, "Tomato", "https://images.unsplash.com/photo-1592841200221-a6898f307baa", 0.15, 0.1, "kg");
        tomato = plantTypeRepository.save(tomato);

        var garden = new Garden(null, MY_GARDEN_NAME);
        garden = gardenRepository.save(garden);

        var tomatoPlant1 = new Plant(null, tomato, garden, LocalDate.now(), null);
        tomatoPlant1 = plantRepository.save(tomatoPlant1);

        var tomatoPlant2 = new Plant(null, tomato, garden, LocalDate.now().minusMonths(2), null);
        tomatoPlant2 = plantRepository.save(tomatoPlant2);

        return "hello!";
    }
}
