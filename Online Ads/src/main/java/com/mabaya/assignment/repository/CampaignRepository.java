package com.mabaya.assignment.repository;

import com.mabaya.assignment.model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, UUID> {

    @Query(value = "select * from campaign where status = 'ACTIVE' and start_date <= current_date - INTERVAL '10 days' ",nativeQuery = true)
    List<Campaign> deactivateOutdatedCampaigns();
}
