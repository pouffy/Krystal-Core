package com.pouffydev.krystal_core.foundation.registry.definition;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

public class ItemDefinition<T extends Item> extends ItemLikeDefinition<Item, T> {
    protected ItemDefinition(ResourceKey<Item> key, String customLang) {
        super(key, customLang);
    }

    protected ItemDefinition(ResourceKey<Item> key) {
        this(key, "");
    }

    public static <T extends Item> ItemDefinition<T> fromHolder(DeferredItem<T> holder, String customLang) {
        return new ItemDefinition<T>(holder.getKey(), customLang);
    }

    public static <T extends Item> ItemDefinition<T> fromHolder(DeferredItem<T> holder) {
        return fromHolder(holder, "");
    }

    public boolean is(Item item) {
        return this.get().equals(item);
    }

    public boolean isBlockItem() {
        return this.get() instanceof BlockItem;
    }

    public String langKey() {
        return "item." + super.langKey();
    }
}
