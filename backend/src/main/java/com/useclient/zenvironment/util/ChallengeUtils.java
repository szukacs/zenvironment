package com.useclient.zenvironment.util;

import com.useclient.zenvironment.model.dao.Community;
import com.useclient.zenvironment.model.dao.Garden;
import com.useclient.zenvironment.model.dao.Plant;
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


}
