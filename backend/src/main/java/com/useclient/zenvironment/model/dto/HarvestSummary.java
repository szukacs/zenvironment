package com.useclient.zenvironment.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HarvestSummary {
    private String plantName;
    private double amount;
    private String unit;
    private long harvestCount;
}
