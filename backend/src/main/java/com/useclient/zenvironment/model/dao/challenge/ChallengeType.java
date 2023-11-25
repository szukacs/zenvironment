package com.useclient.zenvironment.model.dao.challenge;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Function;

import static com.useclient.zenvironment.service.ChallengeService.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ChallengeType {
    OxygenProduction(
            "Oxygen Production",
            "Have your community gardens produce the target amount of oxygen",
            "/challenge/oxygen.jpg",
            "#56b5db",
            SUM_OXYGEN_NAME,
            level -> (20.0 * level)),
    CO2Fixation(
            "CO2 Fixation",
            "Have your community gardens fixate the target amount of CO2",
            "/challenge/co2fixation.jpg",
            "#56b5db",
            SUM_CO2_NAME,
            level -> (20.0 * level)),
    CommunitySize(
            "Community Members",
            "Invite more members into the community",
            "/challenge/communityMembers.jpg",
            "#56b5db",
            COUNT_COMMUNITY_SIZE_NAME,
            level -> (3.0 * level)),
    PlantCount(
            "Number of Plants",
            "Increase the number of plants in your community",
            "/challenge/numberPlants.jpg",
            "#56b5db",
            COUNT_ALL_PLANTS_NAME,
            level -> (10.0 * level)),
    TomatoCount(
            "Number of Tomatoes",
            "Increase the number of tomato plants in your community",
            "/challenge/tomatoPlant.jpg",
            "#56b5db",
            COUNT_TOMATOES_NAME,
            level -> (5.0 * level)),

    PaprikaCount(
            "Number of Paprikas",
            "Increase the number of paprika plants in your community",
            "/challenge/paprikaPlant.jpg",
            "#56b5db",
            COUNT_PAPRIKAS_NAME,
            level -> (5.0 * level)),

    LettuceCount(
            "Number of Lettuce",
            "Increase the number of lettuce plants in your community",
            "/challenge/lettucePlant.jpg",
            "#56b5db",
            COUNT_LETTUCE_NAME,
            level -> (5.0 * level)),

    PeaCount(
            "Number of Peas",
            "Increase the number of pea plants in your community",
            "/challenge/peaPlant.jpg",
            "#56b5db",
            COUNT_PEAS_NAME,
            level -> (5.0 * level)),

    SunFlowerCount(
            "Number of Sunflowers",
            "Increase the number of sunflower plants in your community",
            "/challenge/sunFlowerPlant.jpg",
            "#56b5db",
            COUNT_SUNFLOWER_NAME,
            level -> (5.0 * level)),
    ;

    private final String name;
    private final String description;
    private final String imageUrl;
    private final String color;
    private final String progressFunctionName;
    private final Function<Integer, Double> levelTargetFunction;
}
