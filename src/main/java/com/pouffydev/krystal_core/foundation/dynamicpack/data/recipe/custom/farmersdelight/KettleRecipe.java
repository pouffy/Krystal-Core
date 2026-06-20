package com.pouffydev.krystal_core.foundation.dynamicpack.data.recipe.custom.farmersdelight;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import com.pouffydev.krystal_core.foundation.dynamicpack.data.recipe.custom.CustomRecipe;
import lombok.Getter;
import net.minecraft.core.NonNullList;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class KettleRecipe extends CustomRecipe<RecipeInput> {
    private int ingredientCount = 0;
    private FluidStack inputFluid;
    private FluidStack outputFluid;
    private final NonNullList<Ingredient> ingredients = NonNullList.withSize(2, Ingredient.EMPTY);
    private float experience = 0.0F;
    private int brewTime = 2400;
    private String group = "";

    private final @Getter List<ICondition> conditions = new ArrayList<>();

    public KettleRecipe() {}

    private KettleRecipe(FluidStack inputFluid, FluidStack outputFluid, float experience, int brewTime) {
        this.inputFluid = inputFluid;
        this.outputFluid = outputFluid;
        this.experience = experience;
        this.brewTime = brewTime;
    }

    public static KettleRecipe kettleRecipe(Fluid inputFluid, int inputAmount, Fluid outputFluid, int outputAmount, int brewTime, float experience) {
        return new KettleRecipe(new FluidStack(inputFluid, inputAmount), new FluidStack(outputFluid, outputAmount), experience, brewTime);
    }

    public KettleRecipe addIngredient(ItemLike item) {
        return this.addIngredient(Ingredient.of(item));
    }

    public KettleRecipe addIngredient(TagKey<Item> tag) {
        return this.addIngredient(Ingredient.of(tag));
    }

    public KettleRecipe addIngredient(Ingredient ingredient) {
        ingredients.set(ingredientCount, ingredient);
        ++ingredientCount;
        return this;
    }

    public KettleRecipe group(@Nullable String groupName) {
        this.group = groupName == null ? "" : groupName;
        return this;
    }

    public KettleRecipe withCondition(ICondition condition) {
        conditions.add(condition);
        return this;
    }

    @Override
    public JsonObject serialize() {
        JsonObject json = new JsonObject();
        json.addProperty("type", "farmersrespite:keg_fermenting");
        json.addProperty("group", this.group);
        FluidStack.CODEC.encodeStart(JsonOps.INSTANCE, this.inputFluid).result().ifPresent((fluid) -> json.add("base", fluid));
        JsonArray ingredients = new JsonArray();
        for (Ingredient ingredient : this.ingredients) {
            Ingredient.CODEC.encodeStart(JsonOps.INSTANCE, ingredient).result().ifPresent(ingredients::add);
        }
        json.add("ingredients", ingredients);
        FluidStack.CODEC.encodeStart(JsonOps.INSTANCE, this.outputFluid).result().ifPresent((fluid) -> json.add("result", fluid));
        json.addProperty("experience", this.experience);
        json.addProperty("cookingtime", this.brewTime);
        return json;
    }
}
