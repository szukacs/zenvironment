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
    @Transient
    private int level = 1;
    @Enumerated(EnumType.STRING)
    private ChallengeType challengeType;

    @Transient
    private double currentProgress;

    public Challenge(Community community, ChallengeType challengeType) {
        this.community = community;
        this.challengeType = challengeType;
    }

    public int getLevel() {
        var currentProgress = getCurrentProgress();
        while (getLevelTarget(level) <= currentProgress) {
            level++;
        }
        return level;
    }

    private double getLevelTarget(int targetLevel) {
        return challengeType.getLevelTargetFunction().apply(targetLevel);
    }

    public double getPreviousLevelTarget() {
        return getLevelTarget(getLevel() - 1);
    }

    public double getNextLevelTarget() {
        return getLevelTarget(getLevel());
    }

}
