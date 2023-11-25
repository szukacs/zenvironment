package com.useclient.zenvironment.service;

import com.useclient.zenvironment.mapper.MainMapper;
import com.useclient.zenvironment.model.dao.*;
import com.useclient.zenvironment.model.dto.HarvestSummary;
import com.useclient.zenvironment.model.dto.NewHarvestDto;
import com.useclient.zenvironment.model.dto.NewPlantDto;
import com.useclient.zenvironment.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ZenService {
    private final MainMapper mapper;
    private final GardenRepository gardenRepository;
    private final PlantTypeRepository plantTypeRepository;
    private final CommunityRepository communityRepository;
    private final PlantRepository plantRepository;
    private final HarvestRepository harvestRepository;

    public Garden getGardenByName(String gardenName) {
        return gardenRepository.getByName(gardenName);
    }

    public List<PlantType> getAllPlantTypes() {
        return plantTypeRepository.findAll();
    }

    public Community getCommunityByName(String communityName) {
        return communityRepository.getByName(communityName);
    }

    public Plant addPlant(Garden garden, NewPlantDto newPlantDto) {
        var newPlant = mapper.toEntity(newPlantDto, garden);
        return plantRepository.save(newPlant);
    }

    public Plant getPlantById(UUID plantId) {
        return plantRepository.getPlantById(plantId);
    }

    public Harvest harvestPlant(Plant harvestedPlant, NewHarvestDto newHarvestDto) {
        var newHarvest = new Harvest(null, harvestedPlant, newHarvestDto.getAmount(), newHarvestDto.getHarvestDate());
        return harvestRepository.save(newHarvest);
    }

    public List<HarvestSummary> getHarvestSummaryByGarden(Garden myGarden) {
        return harvestRepository.getHarvestSummaryByGarden(myGarden);
    }
}
