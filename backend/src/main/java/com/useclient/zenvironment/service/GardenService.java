package com.useclient.zenvironment.service;

import com.useclient.zenvironment.TestBootstrapper;
import com.useclient.zenvironment.model.dao.Garden;
import com.useclient.zenvironment.model.dto.NewGardenDto;
import com.useclient.zenvironment.repository.GardenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GardenService {
    private final ZenService zenService;
    private final GardenRepository gardenRepository;

    public Garden createGarden(NewGardenDto newGardenDto) {
        var community = zenService.getCommunityByName(TestBootstrapper.MY_COMMUNITY_NAME);
        var newGarden = new Garden(newGardenDto.getName(), community);
        return gardenRepository.save(newGarden);
    }

    public Garden getGardenById(String gardenId) {
        return gardenRepository.findById(UUID.fromString(gardenId)).get();
    }
}
