package com.senlainc.advertisementsystem.model.user;

public enum  UserStatus {

    ACTIVE("active"),
    BLOCKED("blocked"),
    DELETED("deleted");

    private String name;

    UserStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
