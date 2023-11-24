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
    @OneToOne
    private Community community;
    private int level;
    private ChallengeType challengeType;
}
