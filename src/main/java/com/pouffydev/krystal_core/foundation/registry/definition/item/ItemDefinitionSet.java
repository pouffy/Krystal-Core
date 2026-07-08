package com.pouffydev.krystal_core.foundation.registry.definition.item;

import com.pouffydev.krystal_core.foundation.registry.DeferredHolderSet;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;

public class ItemDefinitionSet<T extends Item, K extends Enum<K> & StringRepresentable> extends DeferredHolderSet<Item, T, K, ItemDefinition<T>> {

    public ItemDefinitionSet(Class<K> keyType) {
        super(keyType);
    }
}
