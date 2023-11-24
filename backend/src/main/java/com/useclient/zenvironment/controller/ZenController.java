package com.useclient.zenvironment.controller;

import com.useclient.zenvironment.mapper.MainMapper;
import com.useclient.zenvironment.model.dao.PlantType;
import com.useclient.zenvironment.model.dto.GardenDto;
import com.useclient.zenvironment.model.dto.PlantTypeDto;
import com.useclient.zenvironment.repository.GardenRepository;
import com.useclient.zenvironment.repository.PlantRepository;
import com.useclient.zenvironment.repository.PlantTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.useclient.zenvironment.TestBootstrapper.MY_GARDEN_NAME;

@RestController
@RequiredArgsConstructor
public class ZenController {
    private final PlantTypeRepository plantTypeRepository;
    private final GardenRepository gardenRepository;
    private final PlantRepository plantRepository;
    private final MainMapper mapper;

    @GetMapping("/plant-types")
    public ResponseEntity<List<PlantTypeDto>> getPlantTypes() {
        var responseBody = plantTypeRepository.findAll().stream()
                .map(mapper::toDto)
                .toList();
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/my-garden")
    public ResponseEntity<GardenDto> getMyGarden() {
        var myGarden = gardenRepository.findByName(MY_GARDEN_NAME);
        var myPlants = plantRepository.findAllByGarden(myGarden).stream()
                .map(mapper::toDto)
                .toList();
        var responseBody = mapper.toDto(myGarden, myPlants);
        return ResponseEntity.ok(responseBody);
    }
}
