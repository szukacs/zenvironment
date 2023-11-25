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
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GardenService {
    private final ZenService zenService;
    private final GardenRepository gardenRepository;
    private final PlantTypeRepository plantTypeRepository;
    private final PlantRepository plantRepository;

    public Garden createGarden(NewGardenDto newGardenDto) {
        var community = zenService.getCommunityByName(TestBootstrapper.MY_COMMUNITY_NAME);
        var newGarden = new Garden(newGardenDto.getName(), community);
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

    private int getRandomCoordinate() {
        return (int) (Math.random() * 5);
    }

    public Garden getGardenById(String gardenId) {
        return gardenRepository.findById(UUID.fromString(gardenId)).get();
    }
}
