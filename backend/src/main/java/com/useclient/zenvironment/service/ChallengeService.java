package com.useclient.zenvironment.service;

import com.useclient.zenvironment.model.dao.Community;
import com.useclient.zenvironment.model.dao.Garden;
import com.useclient.zenvironment.model.dao.Plant;
import com.useclient.zenvironment.model.dao.PlantType;
import com.useclient.zenvironment.model.dao.challenge.Challenge;
import com.useclient.zenvironment.repository.ChallengeRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.mapstruct.Named;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChallengeService {
    public static final String SUM_OXYGEN_NAME = "summarizeOxygenProduction";
    public static final String SUM_CO2_NAME = "summarizeCO2Fixation";
    public static final String COUNT_TOMATOES_NAME = "countTomatoes";
    public static final String COUNT_COMMUNITY_SIZE_NAME = "countCommunitySize";
    public static final String COUNT_ALL_PLANTS_NAME = "countAllPlants";

    private final ChallengeRepository challengeRepository;

    public List<Challenge> getChallengesByCommunity(Community community) {
        return challengeRepository.getAllByCommunity(community).stream()
                .peek(this::calculateCurrentProgress)
                .collect(Collectors.toList());
    }

    @SneakyThrows
    private void calculateCurrentProgress(Challenge challenge) {
        var progressFunctionName = challenge.getChallengeType().getProgressFunctionName();
        var currentProgress = (Double) this.getClass().getMethod(progressFunctionName, Community.class)
                .invoke(this, challenge.getCommunity());
        challenge.setCurrentProgress(currentProgress);
    }

    @Named(SUM_OXYGEN_NAME)
    public double summarizeOxygenProduction(Community community) {
        return community.getGardens().stream()
                .map(Garden::getPlants)
                .flatMap(List::stream)
                .map(Plant::getAllProducedOxygenInKilograms)
                .reduce(0.0, Double::sum);
    }

    @Named(SUM_CO2_NAME)
    public double summarizeCO2Fixation(Community community) {
        return community.getGardens().stream()
                .map(Garden::getPlants)
                .flatMap(List::stream)
                .map(Plant::getAllFixatedCO2InKilograms)
                .reduce(0.0, Double::sum);
    }

    @Named(COUNT_COMMUNITY_SIZE_NAME)
    public static double countCommunitySize(Community community) {
        return community.getGardens().size();
    }

    @Named(COUNT_ALL_PLANTS_NAME)
    public double countAllPlants(Community community) {
        return community.getGardens().stream()
                .map(Garden::getPlants)
                .mapToLong(List::size)
                .sum();
    }

    @Named(COUNT_TOMATOES_NAME)
    public double countTomatoes(Community community) {
        return countPlantsOfType(community, "tomato");
    }

    public double countPlantsOfType(Community community, String countedPlantName) {
        return community.getGardens().stream()
                .map(Garden::getPlants)
                .flatMap(List::stream)
                .map(Plant::getPlantType)
                .map(PlantType::getName)
                .filter(plantName -> plantName.toLowerCase().contains(countedPlantName))
                .count();
    }
}
