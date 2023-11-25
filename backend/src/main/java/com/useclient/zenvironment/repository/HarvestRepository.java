package com.useclient.zenvironment.repository;

import com.useclient.zenvironment.model.dao.Garden;
import com.useclient.zenvironment.model.dao.Harvest;
import com.useclient.zenvironment.model.dto.HarvestSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface HarvestRepository extends JpaRepository<Harvest, UUID> {

    @Query("""
            select new com.useclient.zenvironment.model.dto.HarvestSummary(
            t.name, sum(h.amount), t.harvestUnit, count(h))
            from Harvest h
            left join h.plant p
            left join p.plantType t
            left join p.garden g where g = :garden
            group by t.name, t.harvestUnit
            """)
    List<HarvestSummary> getHarvestSummaryByGarden(@Param("garden") Garden garden);
}
