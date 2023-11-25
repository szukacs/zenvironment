package com.useclient.zenvironment.model.dao.challenge;

import com.useclient.zenvironment.model.dao.Community;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Challenge {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    private Community community;
    private int level = 1;
    @Enumerated(EnumType.STRING)
    private ChallengeType challengeType;

    @Transient
    private double currentProgress;

    public Challenge(Community community, ChallengeType challengeType) {
        this.community = community;
        this.challengeType = challengeType;
    }

    public void calibrateChallengeLevel() {
        var currentProgress = getCurrentProgress();
        while (getNextLevelTarget() <= currentProgress) {
            level++;
        }
    }

    public double getPreviousLevelTarget() {
        return challengeType.getLevelTargetFunction().apply(level - 1);
    }

    public double getNextLevelTarget() {
        return challengeType.getLevelTargetFunction().apply(level);
    }

}
