package com.mabaya.assignment.service;

import com.mabaya.assignment.model.Campaign;
import com.mabaya.assignment.model.CampaignStatus;
import com.mabaya.assignment.model.Product;
import com.mabaya.assignment.repository.CampaignRepository;
import com.mabaya.assignment.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromoteAdsService {

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private ProductRepository productRepository;
    public Campaign createCampaign(Campaign campaign) {
        Campaign newCampaign = campaignRepository.save(campaign);
        return newCampaign;
    }

    public Product serveAd(String category) {
        Product product = productRepository.getHighestBidProductByCategory(category);
        if (product == null) {
            product = productRepository.getHighestBidProduct();
        }
        return product;
    }

    @Scheduled(cron = "0 0 * * * *")
    public void deactivateOldCampaigns() {
        List<Campaign> campaigns = campaignRepository.deactivateOutdatedCampaigns();
        if(!campaigns.isEmpty()) {
            campaigns.forEach(c -> c.setStatus(CampaignStatus.INACTIVE));
            campaignRepository.saveAll(campaigns);
        }
    }
}
