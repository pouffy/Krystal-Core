package com.pouffydev.krystal_core.foundation.registry.definition;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

public class ItemLikeDefinition<R extends ItemLike, T extends R> extends Definition<R, T> implements ItemLike {
    protected ItemLikeDefinition(ResourceKey<R> key, String customLang) {
        super(key, customLang);
    }

    public @NotNull Item asItem() {
        Item item = this.get().asItem();
        if (item == Items.AIR) {
            throw new IllegalArgumentException("No registered item for " + this.getRegisteredName());
        } else {
            return item;
        }
    }

    public Item item() {
        return this.asItem();
    }

    public boolean hasItem() {
        return this.get().asItem() != Items.AIR;
    }

    public Ingredient ingredient() {
        return this.ingredient(1);
    }

    public Ingredient ingredient(int count) {
        return Ingredient.of(new ItemStack(this.item(), count));
    }

    public ItemStack stack() {
        return this.stack(1);
    }

    public ItemStack stack(int count) {
        return new ItemStack(this.item(), count);
    }

    public boolean is(ItemLike itemLike) {
        return this.get().equals(itemLike);
    }
}
