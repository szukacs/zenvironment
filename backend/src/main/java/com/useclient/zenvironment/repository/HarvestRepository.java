package com.useclient.zenvironment.repository;

import com.useclient.zenvironment.model.dao.Community;
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
            left join p.garden g
            where g = :garden
            group by t.name, t.harvestUnit
            order by sum(h.amount) desc
            """)
    List<HarvestSummary> getHarvestSummaryByGarden(@Param("garden") Garden garden);

    @Query("""
            select new com.useclient.zenvironment.model.dto.HarvestSummary(
            t.name, sum(h.amount), t.harvestUnit, count(h))
            from Harvest h
            left join h.plant p
            left join p.plantType t
            left join p.garden g
            left join g.community c
            where c = :community
            and lower(t.name) like %:plantName%
            group by t.name, t.harvestUnit
            order by sum(h.amount) desc
            """)
    HarvestSummary getHarvestSummaryOfCommunityByPlant(@Param("community") Community community, @Param("plantName") String plantName);

    @Query("""
            select new com.useclient.zenvironment.model.dto.HarvestSummary(
            t.name, sum(h.amount), t.harvestUnit, count(h))
            from Harvest h
            left join h.plant p
            left join p.plantType t
            left join p.garden g
            left join g.community c
            where c = :community
            group by t.name, t.harvestUnit
            """)
    List<HarvestSummary> getHarvestSummaryOfCommunity(@Param("community") Community community);
}
