package com.useclient.zenvironment.controller;

import com.useclient.zenvironment.mapper.MainMapper;
import com.useclient.zenvironment.model.dto.*;
import com.useclient.zenvironment.service.ChallengeService;
import com.useclient.zenvironment.service.ExchangeService;
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
    private final ChallengeService challengeService;
    private final MainMapper mapper;
    private final ExchangeService exchangeService;

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

    @Transactional(readOnly = true)
    @GetMapping("/my-garden/harvests")
    public ResponseEntity<List<HarvestSummary>> getMyHarvests() {
        var myGarden = zenService.getGardenByName(MY_GARDEN_NAME);
        var harvestSummary = zenService.getHarvestSummaryByGarden(myGarden);
        return ResponseEntity.ok(harvestSummary);
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
    public ResponseEntity<PlantDto> getPlantById(@PathVariable String plantId) {
        var plant = zenService.getPlantById(UUID.fromString(plantId));
        var responseBody = mapper.toDto(plant);
        return ResponseEntity.ok(responseBody);
    }

    @Transactional
    @PostMapping("/my-garden/plants/{plantId}/harvest")
    public ResponseEntity<HarvestDto> harvestPlant(@PathVariable String plantId, @RequestBody NewHarvestDto newHarvestDto) {
        var harvestedPlant = zenService.getPlantById(UUID.fromString(plantId));
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
        var challenges = challengeService.getChallengesByCommunity(myCommunity);
        var responseBody = mapper.toChallengeDtoList(challenges);
        return ResponseEntity.ok(responseBody);
    }

    @Transactional
    @PostMapping("/my-garden/exchanges")
    public ResponseEntity<List<ExchangeDto>> addExchange(ExchangeDto exchangeDto){
        var responseBody = exchangeService.createExchange(exchangeDto);
        return ResponseEntity.ok(responseBody);
    }

    @Transactional
    @PutMapping("/my-garden/exchanges/")
    public ResponseEntity<?> acceptExchange(ExchangeDto exchangeDto){
        exchangeService.updateStatusOfExchange(exchangeDto);
        return ResponseEntity.ok("You successfully accepted the plants");
    }

    @Transactional(readOnly = true)
    @GetMapping("/my-community/exchanges/{communityId}")
    public ResponseEntity<List<ExchangeDto>> findAllExchangesBelongingToCommuniy(@PathVariable("communityId") String communityId){
        var responseBody = exchangeService.findAllExchangesBelongingToCommunity(communityId);
        return ResponseEntity.ok(responseBody);
    }

    @Transactional(readOnly = true)
    @GetMapping("/my-garden/exchanges/{gardenId}")
    public ResponseEntity<List<ExchangeDto>> findAllExchangesBelongingToGarden(@PathVariable("gardenId") String gardenId){
        var responseBody = exchangeService.findAllExchangesBelongingToGarden(gardenId);
        return ResponseEntity.ok(responseBody);
    }
}
