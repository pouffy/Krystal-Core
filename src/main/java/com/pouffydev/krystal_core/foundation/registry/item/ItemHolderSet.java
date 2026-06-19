package com.pouffydev.krystal_core.foundation.registry.item;

import com.pouffydev.krystal_core.foundation.registry.DeferredHolderSet;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ItemHolderSet<T extends Item, K extends Enum<K> & StringRepresentable> extends DeferredHolderSet<Item, T, K, DeferredHolder<Item, T>> {

    public ItemHolderSet(Class<K> keyType) {
        super(keyType);
    }
}
