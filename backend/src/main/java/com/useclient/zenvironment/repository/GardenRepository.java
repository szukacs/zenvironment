package com.useclient.zenvironment.repository;

import com.useclient.zenvironment.model.dao.Garden;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GardenRepository extends JpaRepository<Garden, UUID> {
    Garden findByName(String name);
}
