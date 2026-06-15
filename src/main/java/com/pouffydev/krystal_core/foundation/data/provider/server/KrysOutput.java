package com.pouffydev.krystal_core.foundation.data.provider.server;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.conditions.ICondition;

public interface KrysOutput<T> extends KrysOutputExtension<T> {
    default void accept(ResourceLocation location, T value) {
        accept(location, value, new ICondition[0]);
    }
}
