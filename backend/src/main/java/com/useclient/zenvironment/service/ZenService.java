package com.useclient.zenvironment.service;

import com.useclient.zenvironment.model.dao.Community;
import com.useclient.zenvironment.model.dao.Garden;
import com.useclient.zenvironment.model.dao.PlantType;
import com.useclient.zenvironment.model.dao.challenge.Challenge;
import com.useclient.zenvironment.repository.ChallengeRepository;
import com.useclient.zenvironment.repository.CommunityRepository;
import com.useclient.zenvironment.repository.GardenRepository;
import com.useclient.zenvironment.repository.PlantTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ZenService {
    private final GardenRepository gardenRepository;
    private final PlantTypeRepository plantTypeRepository;
    private final CommunityRepository communityRepository;
    private final ChallengeRepository challengeRepository;

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
}
