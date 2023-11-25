package com.useclient.zenvironment.repository;

import com.useclient.zenvironment.model.dao.Community;
import com.useclient.zenvironment.model.dao.challenge.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ChallengeRepository extends JpaRepository<Challenge, UUID> {
    List<Challenge> getAllByCommunity(Community community);
}
