package com.pouffydev.krystal_core.foundation.dynamicpack.data.recipe.custom;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.conditions.ICondition;

import java.util.ArrayList;
import java.util.List;

public abstract class CustomRecipe<T extends RecipeInput> implements Recipe<T> {
    private final List<ICondition> conditions = new ArrayList<>();

    @Override
    public boolean matches(T recipeInput, Level level) {
        return false;
    }

    @Override
    public ItemStack assemble(T recipeInput, HolderLookup.Provider provider) {
        return null;
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return false;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return null;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return null;
    }

    @Override
    public RecipeType<?> getType() {
        return null;
    }

    public abstract List<ICondition> getConditions();

    private void serializeConditions(JsonObject json) {
        ICondition.writeConditions(JsonOps.INSTANCE, json, this.getConditions());
    }

    public abstract JsonObject serialize();

    public JsonElement save() {
        JsonObject recipe = serialize();
        serializeConditions(recipe);
        return recipe;
    }
}
