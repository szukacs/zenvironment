package com.useclient.zenvironment.repository;

import com.useclient.zenvironment.model.dao.PlantType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PlantTypeRepository extends JpaRepository<PlantType, UUID> {
}
