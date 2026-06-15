package com.pouffydev.krystal_core.foundation.data.provider.server;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.conditions.ICondition;

public interface KrysOutputExtension<T> {
    private KrysOutput self() {
        return (KrysOutput) this;
    }
    void accept(ResourceLocation id, T value, ICondition... conditions);

    default KrysOutput withConditions(ICondition... conditions) {
        return new ConditionalKrysOutput<>(this.self(), conditions);
    }
}
