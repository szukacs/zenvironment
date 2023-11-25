package com.useclient.zenvironment.repository;

import com.useclient.zenvironment.model.dao.Garden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface GardenRepository extends JpaRepository<Garden, UUID> {
  Garden getByName(String name);

  @Query(name = "SELECT garden from Garden garden where garden.community.id = :communityId")
  List<Garden> findAllGardensByCommunityId(@Param("communityId") UUID communityId);

}
