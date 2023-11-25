package com.useclient.zenvironment.controller;

import com.useclient.zenvironment.mapper.MainMapper;
import com.useclient.zenvironment.model.dto.*;
import com.useclient.zenvironment.service.ZenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.useclient.zenvironment.TestBootstrapper.MY_COMMUNITY_NAME;
import static com.useclient.zenvironment.TestBootstrapper.MY_GARDEN_NAME;

@RestController
@RequiredArgsConstructor
public class ZenController {
    private final ZenService zenService;
    private final MainMapper mapper;

    @Transactional(readOnly = true)
    @GetMapping("/plant-types")
    public ResponseEntity<List<PlantTypeDto>> getPlantTypes() {
        var responseBody = zenService.getAllPlantTypes().stream()
                .map(mapper::toDto)
                .toList();
        return ResponseEntity.ok(responseBody);
    }

    @Transactional(readOnly = true)
    @GetMapping("/my-garden")
    public ResponseEntity<GardenDto> getMyGarden() {
        var myGarden = zenService.getGardenByName(MY_GARDEN_NAME);
        var responseBody = mapper.toDto(myGarden);
        return ResponseEntity.ok(responseBody);
    }

    @Transactional
    @PostMapping("/my-garden/plants")
    public ResponseEntity<PlantDto> addPlant(@RequestBody NewPlantDto newPlantDto) {
        var myGarden = zenService.getGardenByName(MY_GARDEN_NAME);
        var newPlant = zenService.addPlant(myGarden, newPlantDto);
        var responseBody = mapper.toDto(newPlant);
        return ResponseEntity.ok(responseBody);
    }

    @Transactional(readOnly = true)
    @GetMapping("/my-garden/plants/{plantId}")
    public ResponseEntity<PlantDto> getPlantById(@PathVariable UUID plantId) {
        var plant = zenService.getPlantById(plantId);
        var responseBody = mapper.toDto(plant);
        return ResponseEntity.ok(responseBody);
    }

    @Transactional
    @PostMapping("/my-garden/plants/{plantId}/harvest")
    public ResponseEntity<HarvestDto> harvestPlant(@PathVariable UUID plantId, @RequestBody NewHarvestDto newHarvestDto) {
        var harvestedPlant = zenService.getPlantById(plantId);
        var newHarvest = zenService.harvestPlant(harvestedPlant, newHarvestDto);
        var responseBody = mapper.toDto(newHarvest);
        return ResponseEntity.ok(responseBody);
    }

    @Transactional(readOnly = true)
    @GetMapping("/my-community")
    public ResponseEntity<CommunityDto> getMyCommunity() {
        var myCommunity = zenService.getCommunityByName(MY_COMMUNITY_NAME);
        var responseBody = mapper.toDto(myCommunity);
        return ResponseEntity.ok(responseBody);
    }

    @Transactional(readOnly = true)
    @GetMapping("/my-community/challenges")
    public ResponseEntity<List<ChallengeDto>> getMyCommunityChallenges() {
        var myCommunity = zenService.getCommunityByName(MY_COMMUNITY_NAME);
        var challenges = zenService.getChallengesByCommunity(myCommunity);
        var responseBody = mapper.toChallengeDtoList(challenges);
        return ResponseEntity.ok(responseBody);
    }
}
