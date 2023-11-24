package com.useclient.zenvironment.model.dao.challenge;

import com.useclient.zenvironment.model.dao.Community;
import com.useclient.zenvironment.util.ChallengeUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Function;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ChallengeType {
    OxygenProduction(
            "Oxygen Production",
            "Have your community gardens produce the target amount of oxygen",
            ChallengeUtils::summarizeOxygenProduction,
            level -> (20.0 * level)),
    CO2Fixation(
            "CO2 Fixation",
            "Have your community gardens fixate the target amount of CO2",
            ChallengeUtils::summarizeCO2Fixation,
            level -> (20.0 * level)),
    PlantCount(
            "Number of Plants",
            "Increase the number of plants in your community",
            ChallengeUtils::countAllPlants,
            level -> (10.0 * level)),
    CommunitySize(
            "Community Members",
            "Invite more members into the community",
            ChallengeUtils::getCommunitySize,
            level -> (3.0 * level)),
    TomatoCount(
            "Number of Tomatoes",
            "Increase the number of tomato plants in your community",
            community -> ChallengeUtils.countPlantsOfType(community, "tomato"),
            level -> (5.0 * level))
    ;

    private final String name;
    private final String description;
    private final Function<Community, Double> progressFunction;
    private final Function<Integer, Double> levelTargetFunction;
}
