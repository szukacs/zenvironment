package com.useclient.zenvironment.controller;

import com.useclient.zenvironment.model.dao.PlantType;
import com.useclient.zenvironment.repository.PlantTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ZenController {
    private final PlantTypeRepository plantTypeRepository;

    @GetMapping("/plant-types")
    public ResponseEntity<List<PlantType>> getPlantTypes() {
        return ResponseEntity.ok(plantTypeRepository.findAll());
    }
}
