package com.useclient.zenvironment.repository;

import com.useclient.zenvironment.model.dao.Community;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommunityRepository extends JpaRepository<Community, UUID> {
    Community getByName(String name);
}
