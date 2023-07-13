package com.mabaya.assignment.controller;

import com.mabaya.assignment.model.Campaign;
import com.mabaya.assignment.model.Product;
import com.mabaya.assignment.service.PromoteAdsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PromoteAdsController {

    Logger logger = LoggerFactory.getLogger(PromoteAdsController.class);

    @Autowired
    private PromoteAdsService promoteAdsService;

    @PostMapping("/create/campaign")
    public ResponseEntity<Campaign> createCampaign(@RequestBody Campaign campaign) {
        try {
            Campaign campaign1 = promoteAdsService.createCampaign(campaign);
            return ResponseEntity.ok(campaign1);
        } catch (Exception e) {
            logger.error("error processing campaign {}", campaign, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/serve/ad/{category}")
    public ResponseEntity<Product> serveAd(@PathVariable String category) {
        try{
            Product product = promoteAdsService.serveAd(category);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            logger.error("error getting products of category {}", category);
            return ResponseEntity.internalServerError().build();
        }
    }

}
