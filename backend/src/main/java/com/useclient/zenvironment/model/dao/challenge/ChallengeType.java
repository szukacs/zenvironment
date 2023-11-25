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
    PlantCount(
            "Number of Plants",
            "Increase the number of plants in your community",
            "/challenge/numberPlants.jpg",
            "#56b5db",
            COUNT_ALL_PLANTS_NAME,
            level -> (10.0 * level)),
    CommunitySize(
            "Community Members",
            "Invite more members into the community",
            "/challenge/communityMembers.jpg",
            "#56b5db",
            COUNT_COMMUNITY_SIZE_NAME,
            level -> (3.0 * level)),
    TomatoCount(
            "Number of Tomatoes",
            "Increase the number of tomato plants in your community",
            "/challenge/tomatoPlant.jpg",
            "#56b5db",
            COUNT_TOMATOES_NAME,
            level -> (5.0 * level))
            community -> ChallengeUtils.countPlantsOfType(community, "tomato"),
            level -> (5.0 * level)),

    PaprikaCount(
            "Number of Paprikas",
                "Increase the number of paprika plants in your community",
                "/challenge/paprikaPlant.jpg",
                "#56b5db",
            community -> ChallengeUtils.countPlantsOfType(community, "tomato"),
    level -> (5.0 * level)),

    OnionCount(
        "Number of Onions",
        "Increase the number of onion plants in your community",
        "/challenge/onionPlant.jpg",
        "#56b5db",
        community -> ChallengeUtils.countPlantsOfType(community, "tomato"),
        level -> (5.0 * level)),

    letttuceCount(
        "Number of Lettuce",
        "Increase the number of lettuce plants in your community",
        "/challenge/lettucePlant.jpg",
        "#56b5db",
        community -> ChallengeUtils.countPlantsOfType(community, "tomato"),
        level -> (5.0 * level)),

    SpinachCount(
        "Number of Spinach",
        "Increase the number of spinach plants in your community",
        "/challenge/spinachPlant.jpg",
        "#56b5db",
        community -> ChallengeUtils.countPlantsOfType(community, "tomato"),
        level -> (5.0 * level)),

    WheatCount(
        "Number of Wheat",
        "Increase the number of wheat plants in your community",
        "/challenge/paprikaPlant.jpg",
        "#56b5db",
        community -> ChallengeUtils.countPlantsOfType(community, "tomato"),
        level -> (5.0 * level)),

    CucumberCount(
        "Number of Cucumbers",
        "Increase the number of cucumber plants in your community",
        "/challenge/cucumberPlant.jpg",
        "#56b5db",
        community -> ChallengeUtils.countPlantsOfType(community, "tomato"),
        level -> (5.0 * level)),

    SunFlowerCount(
        "Number of Sunflowers",
        "Increase the number of sunFlower plants in your community",
        "/challenge/sunFlowerPlant.jpg",
        "#56b5db",
        community -> ChallengeUtils.countPlantsOfType(community, "tomato"),
        level -> (5.0 * level)),
    ;

    private final String name;
    private final String description;
    private final String imageUrl;
    private final String color;
    private final String progressFunctionName;
    private final Function<Integer, Double> levelTargetFunction;
}
