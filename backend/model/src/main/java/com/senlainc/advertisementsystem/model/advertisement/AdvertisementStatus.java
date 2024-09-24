package com.senlainc.advertisementsystem.model.advertisement;

public enum  AdvertisementStatus {

    IN_THE_TOP("in the top"),
    ACTIVE("active"),
    HIDDEN("hidden");

    private String name;

    AdvertisementStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
