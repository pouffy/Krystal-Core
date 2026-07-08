package com.pouffydev.krystal_core.foundation.registry.definition.fluid;

public record FluidProperties(String customLang) {

    public static FluidProperties custom(String customLang) {
        return new FluidProperties(customLang);
    }
}
