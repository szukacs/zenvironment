package com.useclient.zenvironment.repository;

import com.useclient.zenvironment.model.dao.Harvest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HarvestRepository extends JpaRepository<Harvest, UUID> {
}
