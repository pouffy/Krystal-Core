package com.pouffydev.krystal_core.foundation.dynamicpack.data.recipe.custom;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import com.pouffydev.krystal_core.foundation.dynamicpack.data.advancement.RecipeAdvancement;
import com.pouffydev.krystal_core.foundation.dynamicpack.data.recipe.output.CustomRecipeOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.conditions.ICondition;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

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

    public void save(CustomRecipeOutput output, ResourceLocation location) {
        output.accept(location, this, null);
    }

    public void save(CustomRecipeOutput output, ResourceLocation location, @Nullable Function<ResourceLocation, RecipeAdvancement> advancement) {
        output.accept(location, this, advancement != null ? advancement.apply(location).save() : null);
    }
}
