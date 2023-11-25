package com.useclient.zenvironment.service;

import com.useclient.zenvironment.TestBootstrapper;
import com.useclient.zenvironment.model.dao.Garden;
import com.useclient.zenvironment.model.dao.Plant;
import com.useclient.zenvironment.model.dao.PlantType;
import com.useclient.zenvironment.model.dto.NewGardenDto;
import com.useclient.zenvironment.repository.GardenRepository;
import com.useclient.zenvironment.repository.PlantRepository;
import com.useclient.zenvironment.repository.PlantTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GardenService {
    private static final List<String> GARDEN_SUFFIXES = List.of(
            "'s Garden",
            "'s Balcony",
            "'s Little Pasture",
            "'s Jungle",
            "'s Green Hell",
            "'s Plant Station",
            "'s Green Maze",
            "'s Meadow",
            "'s Startup Forest",
            "'s Up & Coming Rainforest",
            "'s Eco Hub",
            "'s Neighborhood Amazonas",
            "'s Cute Cottage",
            "'s Window Rainforest",
            "'s Balcony Garden",
            "'s Garden",
            "'s Eco Station"
    );

    private final ZenService zenService;
    private final GardenRepository gardenRepository;
    private final PlantTypeRepository plantTypeRepository;
    private final PlantRepository plantRepository;

    public Garden createGarden(NewGardenDto newGardenDto) {
        var community = zenService.getCommunityByName(TestBootstrapper.MY_COMMUNITY_NAME);
        var gardenOwnerName = newGardenDto.getName();
        var randomSuffix = GARDEN_SUFFIXES.get(getRandomInt(GARDEN_SUFFIXES.size()));
        var newGarden = new Garden(gardenOwnerName + randomSuffix, community);
        newGarden = gardenRepository.save(newGarden);
        var firstPlant = createFirstPlant(newGarden);
        newGarden.getPlants().add(firstPlant);
        return newGarden;
    }

    private Plant createFirstPlant(Garden newGarden) {
        var plantType = getRandomPlantType();
        int randomX = getRandomCoordinate();
        int randomY = getRandomCoordinate();
        var newPlant = new Plant(null, plantType, newGarden, randomX, randomY, LocalDate.now(), null, new ArrayList<>());
        return plantRepository.save(newPlant);
    }

    private PlantType getRandomPlantType() {
        var plantTypes = plantTypeRepository.findAll();
        return plantTypes.get((int) (Math.random() * plantTypes.size()));
    }


    private int getRandomInt(int excludedMaximum) {
        return (int) (Math.random() * excludedMaximum);
    }

    private int getRandomCoordinate() {
        return getRandomInt(5);
    }

    public Garden getGardenById(String gardenId) {
        return gardenRepository.findById(UUID.fromString(gardenId)).get();
    }
}
