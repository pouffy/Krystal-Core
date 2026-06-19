package com.pouffydev.krystal_core.foundation.registry;

import net.minecraft.util.StringRepresentable;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.EnumMap;
import java.util.List;

public class DeferredHolderSet<E, T extends E, K extends Enum<K> & StringRepresentable, R extends DeferredHolder<E, T>> extends EnumMap<K, R> {

    public DeferredHolderSet(Class<K> keyType) {
        super(keyType);
    }

    public List<R> getHolders() {
        return this.values().stream().toList();
    }
}
