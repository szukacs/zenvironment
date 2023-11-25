package com.useclient.zenvironment.util;

import com.useclient.zenvironment.model.dao.Community;
import com.useclient.zenvironment.model.dao.Garden;
import com.useclient.zenvironment.model.dao.Plant;
import com.useclient.zenvironment.model.dao.PlantType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChallengeUtils {

    public static double summarizeOxygenProduction(Community community) {
        return community.getGardens().stream()
                .map(Garden::getPlants)
                .flatMap(List::stream)
                .map(Plant::getAllProducedOxygenInKilograms)
                .reduce(0.0, Double::sum);
    }

    public static double summarizeCO2Fixation(Community community) {
        return community.getGardens().stream()
                .map(Garden::getPlants)
                .flatMap(List::stream)
                .map(Plant::getAllFixatedCO2InKilograms)
                .reduce(0.0, Double::sum);
    }

    public static double countPlantsOfType(Community community, String countedPlantName) {
        return community.getGardens().stream()
                .map(Garden::getPlants)
                .flatMap(List::stream)
                .map(Plant::getPlantType)
                .map(PlantType::getName)
                .filter(plantName -> plantName.toLowerCase().contains(countedPlantName))
                .count();
    }

    public static double getCommunitySize(Community community) {
        return community.getGardens().size();
    }

    public static double countAllPlants(Community community) {
        return community.getGardens().stream()
                .map(Garden::getPlants)
                .mapToLong(List::size)
                .sum();
    }
}
