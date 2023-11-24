package com.useclient.zenvironment.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ChallengeDto {
    private UUID id;
    private MinimalCommunity community;
    private int level;
    private String challengeName;
    private String challengeDescription;
    private double previousLevelTarget;
    private double nextLevelTarget;
    private double currentProgress;
}
