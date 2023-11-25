package com.useclient.zenvironment.controller;

import com.useclient.zenvironment.model.dao.Garden;
import com.useclient.zenvironment.repository.GardenRepository;
import com.useclient.zenvironment.repository.PlantRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cleanup")
@RequiredArgsConstructor
public class CleanupController {
    private final GardenRepository gardenRepository;
    private final PlantRepository plantRepository;

    @Operation(summary = "A technical endpoint to delete all gardens unnecessarily generated", description = "Returns with a status code ok in case everything went right")
    @Transactional
    @DeleteMapping
    public ResponseEntity<Void> deleteGeneratedGardens() {
        var gardens = gardenRepository.findGeneratedGardens();
        gardens.stream()
                .map(Garden::getPlants)
                .forEach(plantRepository::deleteAll);
        gardenRepository.deleteAll(gardens);
        return ResponseEntity.ok().build();
    }
}
