package com.mabaya.assignment.model;

public enum CampaignStatus {
    ACTIVE("active"),
    INACTIVE("inactive");

    private final String text;

    CampaignStatus(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
