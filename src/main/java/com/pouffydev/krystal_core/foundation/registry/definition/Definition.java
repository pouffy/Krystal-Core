package com.pouffydev.krystal_core.foundation.registry.definition;

import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DeferredHolder;

public class Definition<R, T extends R> extends DeferredHolder<R, T> {

    public Definition(ResourceKey<R> key) {
        super(key);
    }
}
