package com.pouffydev.krystal_core.foundation;

public enum MaterialType {
    metal("metal"),
    gem("gem"),
    ;
    
    private final String name;
    
    MaterialType(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public String getPluralName() {
        return name + "s";
    }
    
    public MaterialType getType() {
        return this;
    }
    
}
