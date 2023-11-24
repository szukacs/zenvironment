package com.useclient.zenvironment.mapper;

import com.useclient.zenvironment.model.dao.Garden;
import com.useclient.zenvironment.model.dao.Plant;
import com.useclient.zenvironment.model.dao.PlantType;
import com.useclient.zenvironment.model.dto.GardenDto;
import com.useclient.zenvironment.model.dto.PlantDto;
import com.useclient.zenvironment.model.dto.PlantTypeDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface MainMapper {

    PlantTypeDto toDto(PlantType plantType);

    @Mapping(target = "gardenId", source = "garden.id")
    PlantDto toDto(Plant plant);

    GardenDto toDto(Garden garden, List<PlantDto> plants);
}
