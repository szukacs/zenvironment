package com.useclient.zenvironment.model.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Garden {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    @ManyToOne
    private Community community;
    @OneToMany(mappedBy = "garden")
    private List<Plant> plants;

    public Garden(String name, Community community) {
        this.name = name;
        this.community = community;
    }
}
