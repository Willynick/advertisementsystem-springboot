package com.senlainc.advertisementsystem.model.advertisement;

public enum ProductCondition {

    NEW("new"),
    WAS_IN_USE("was in use"),
    WITH_DEFECTS("with defects"),
    BROKEN("broken");

    private String name;

    ProductCondition(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
