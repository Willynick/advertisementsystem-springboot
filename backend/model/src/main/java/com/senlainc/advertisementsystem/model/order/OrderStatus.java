package com.senlainc.advertisementsystem.model.order;

public enum OrderStatus {

    NEW("new"),
    COMPLETED("completed"),
    CANCELED("canceled");

    private String name;

    OrderStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
