package com.useclient.zenvironment.mapper;

import com.useclient.zenvironment.model.dao.Community;
import com.useclient.zenvironment.model.dao.Garden;
import com.useclient.zenvironment.model.dao.Plant;
import com.useclient.zenvironment.model.dao.PlantType;
import com.useclient.zenvironment.model.dto.*;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface MainMapper {

    PlantTypeDto toDto(PlantType plantType);

    @Mapping(target = "gardenId", source = "garden.id")
    PlantDto toDto(Plant plant);

    List<PlantDto> toPlantDtoList(List<Plant> plants);

    MinimalCommunity toMinDto(Community community);

    CommunityDto toDto(Community community);

    GardenDto toDto(Garden garden);
}
