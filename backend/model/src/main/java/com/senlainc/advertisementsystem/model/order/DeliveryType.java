package com.senlainc.advertisementsystem.model.order;

public enum DeliveryType {

    BY_COURIER("by courier"),
    BY_CONTACTLESS_COURIER("contactless courier"),
    BY_MAIL("by mail"),
    PICKUP("pickup");

    private String name;

    DeliveryType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
