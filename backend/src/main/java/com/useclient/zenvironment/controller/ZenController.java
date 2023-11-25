package com.useclient.zenvironment.controller;

import com.useclient.zenvironment.mapper.MainMapper;
import com.useclient.zenvironment.model.dto.*;
import com.useclient.zenvironment.service.ChallengeService;
import com.useclient.zenvironment.service.ExchangeService;
import com.useclient.zenvironment.service.GardenService;
import com.useclient.zenvironment.service.ZenService;
import io.swagger.v3.oas.annotations.Operation;
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
    private final GardenService gardenService;

    @Operation(summary = "Get all available plant types in the app", description = "Returns all plant types with associated information")
    @Transactional(readOnly = true)
    @GetMapping("/plant-types")
    public ResponseEntity<List<PlantTypeDto>> getPlantTypes() {
        var responseBody = zenService.getAllPlantTypes().stream()
                .map(mapper::toDto)
                .toList();
        return ResponseEntity.ok(responseBody);
    }

    @Operation(summary = "Creates a garden with required parameters", description = "Returns newly created garden")
    @Transactional
    @PostMapping("/gardens")
    public ResponseEntity<GardenDto> createGarden(@RequestBody NewGardenDto newGardenDto) {
        var newGarden = gardenService.createGarden(newGardenDto);
        return ResponseEntity.ok(mapper.toDto(newGarden));
    }

    @Operation(summary = "Gets garden by unique gardenId", description = "Returns garden belonging to id")
    @Transactional(readOnly = true)
    @GetMapping("/gardens/{gardenId}")
    public ResponseEntity<GardenDto> getGardenById(@PathVariable String gardenId) {
        var myGarden = gardenService.getGardenById(gardenId);
        var responseBody = mapper.toDto(myGarden);
        return ResponseEntity.ok(responseBody);
    }

    @Operation(summary = "Creates new plant in given garden", description = "Returns newly created plant")
    @Transactional
    @PostMapping("/gardens/{gardenId}/plants")
    public ResponseEntity<PlantDto> addPlant(@PathVariable String gardenId, @RequestBody NewPlantDto newPlantDto) {
        var myGarden = gardenService.getGardenById(gardenId);
        var newPlant = zenService.addPlant(myGarden, newPlantDto);
        var responseBody = mapper.toDto(newPlant);
        return ResponseEntity.ok(responseBody);
    }

    @Operation(summary = "Find user's garden", description = "Returns user's garden")
    @Transactional(readOnly = true)
    @GetMapping("/my-garden")
    public ResponseEntity<GardenDto> getMyGarden() {
        var myGarden = zenService.getGardenByName(MY_GARDEN_NAME);
        var responseBody = mapper.toDto(myGarden);
        return ResponseEntity.ok(responseBody);
    }

    @Operation(summary = "Initiating harvest for user's garden", description = "Return harvest details")
    @Transactional(readOnly = true)
    @GetMapping("/my-garden/harvests")
    public ResponseEntity<List<HarvestSummary>> getMyHarvests() {
        var myGarden = zenService.getGardenByName(MY_GARDEN_NAME);
        var harvestSummary = zenService.getHarvestSummaryByGarden(myGarden);
        return ResponseEntity.ok(harvestSummary);
    }

    @Operation(summary = "Creates new plant in given garden", description = "Returns newly created plant")
    @Transactional
    @PostMapping("/my-garden/plants")
    public ResponseEntity<PlantDto> addPlant(@RequestBody NewPlantDto newPlantDto) {
        var myGarden = zenService.getGardenByName(MY_GARDEN_NAME);
        var newPlant = zenService.addPlant(myGarden, newPlantDto);
        var responseBody = mapper.toDto(newPlant);
        return ResponseEntity.ok(responseBody);
    }

    @Operation(summary = "Finding a plant", description = "Returns plant based on plantId")
    @Transactional(readOnly = true)
    @GetMapping("/my-garden/plants/{plantId}")
    public ResponseEntity<PlantDto> getPlantById(@PathVariable String plantId) {
        var plant = zenService.getPlantById(UUID.fromString(plantId));
        var responseBody = mapper.toDto(plant);
        return ResponseEntity.ok(responseBody);
    }

    @Operation(summary = "Harvesting plant based on user input", description = "Returns harvesting details")
    @Transactional
    @PostMapping("/my-garden/plants/{plantId}/harvest")
    public ResponseEntity<HarvestDto> harvestPlant(@PathVariable String plantId, @RequestBody NewHarvestDto newHarvestDto) {
        var harvestedPlant = zenService.getPlantById(UUID.fromString(plantId));
        var newHarvest = zenService.harvestPlant(harvestedPlant, newHarvestDto);
        var responseBody = mapper.toDto(newHarvest);
        return ResponseEntity.ok(responseBody);
    }

    @Operation(summary = "Initiating finding the community belonging to the user", description = "Returns community to which user belongs to")
    @Transactional(readOnly = true)
    @GetMapping("/my-community")
    public ResponseEntity<CommunityDto> getMyCommunity() {
        var myCommunity = zenService.getCommunityByName(MY_COMMUNITY_NAME);
        var responseBody = mapper.toDto(myCommunity);
        return ResponseEntity.ok(responseBody);
    }

    @Operation(summary = "Finding challenges for the community", description = "Returns back actual state of the challenges of the user's commmunity")
    @Transactional(readOnly = true)
    @GetMapping("/my-community/challenges")
    public ResponseEntity<List<ChallengeDto>> getMyCommunityChallenges() {
        var myCommunity = zenService.getCommunityByName(MY_COMMUNITY_NAME);
        var challenges = challengeService.getChallengesByCommunity(myCommunity);
        var responseBody = mapper.toChallengeDtoList(challenges);
        return ResponseEntity.ok(responseBody);
    }

    @Operation(summary = "User creates a new exchange request to give away plants", description = "Returns back all exchange requests to which the user is affiliated to")
    @Transactional
    @PostMapping("my-garden/exchanges")
    public ResponseEntity<List<ExchangeDto>> addExchange(ExchangeDto exchangeDto){
        var responseBody = exchangeService.createExchange(exchangeDto);
        return ResponseEntity.ok(responseBody);
    }

    @Operation(summary = "Finding all exchanges belonging to given garden", description = "Returns all exchanges to which user is affiliated to")
    @Transactional(readOnly = true)
    @GetMapping("/my-garden/exchanges/{gardenId}")
    public ResponseEntity<List<ExchangeDto>> findAllExchangesBelongingToGarden(@PathVariable("gardenId") String gardenId){
        var responseBody = exchangeService.findAllExchangesBelongingToGarden(gardenId);
        return ResponseEntity.ok(responseBody);
    }

    @Operation(summary = "User can accept another user's exchange request", description = "Return a success message tha request was accepted by user")
    @Transactional
    @PutMapping("my-garden/exchanges")
    public ResponseEntity<?> acceptExchange(ExchangeDto exchangeDto){
        exchangeService.updateStatusOfExchange(exchangeDto);
        return ResponseEntity.ok("You successfully accepted the plants");
    }

    @Operation(summary = "Finding all exchanges belonging to given community", description = "Returns all exchanges within the community")
    @Transactional(readOnly = true)
    @GetMapping("/my-community/exchanges/{communityId}")
    public ResponseEntity<List<ExchangeDto>> findAllExchangesBelongingToCommuniy(@PathVariable("communityId") String communityId){
        var responseBody = exchangeService.findAllExchangesBelongingToCommunity(communityId);
        return ResponseEntity.ok(responseBody);
    }
}
