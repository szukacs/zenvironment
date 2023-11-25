package com.useclient.zenvironment.service;

import com.useclient.zenvironment.mapper.MainMapper;
import com.useclient.zenvironment.model.dao.Community;
import com.useclient.zenvironment.model.dao.Garden;
import com.useclient.zenvironment.model.dao.Plant;
import com.useclient.zenvironment.model.dao.PlantType;
import com.useclient.zenvironment.model.dao.challenge.Challenge;
import com.useclient.zenvironment.model.dto.NewPlantDto;
import com.useclient.zenvironment.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ZenService {
    private final MainMapper mapper;
    private final GardenRepository gardenRepository;
    private final PlantTypeRepository plantTypeRepository;
    private final CommunityRepository communityRepository;
    private final ChallengeRepository challengeRepository;
    private final PlantRepository plantRepository;

    public Garden getGardenByName(String gardenName) {
        return gardenRepository.getByName(gardenName);
    }

    public List<PlantType> getAllPlantTypes() {
        return plantTypeRepository.findAll();
    }

    public Community getCommunityByName(String communityName) {
        return communityRepository.getByName(communityName);
    }

    public List<Challenge> getChallengesByCommunity(Community community) {
        return challengeRepository.getAllByCommunity(community);
    }

    public Plant addPlant(Garden garden, NewPlantDto newPlantDto) {
        var newPlant = mapper.toEntity(newPlantDto, garden);
        return plantRepository.save(newPlant);
    }
}
