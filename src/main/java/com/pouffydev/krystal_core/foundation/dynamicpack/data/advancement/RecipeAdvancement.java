package com.pouffydev.krystal_core.foundation.dynamicpack.data.advancement;

import com.mojang.datafixers.util.Either;
import com.pouffydev.krystal_core.foundation.dynamicpack.data.recipe.output.CustomRecipeOutput;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class RecipeAdvancement {
    private final Either<RecipeOutput, CustomRecipeOutput> output;
    private final ResourceLocation id;
    private final Map<String, Criterion<?>> criteria;

    public RecipeAdvancement(RecipeOutput output, ResourceLocation id) {
        this.output = Either.left(output);
        this.id = id;
        this.criteria = new LinkedHashMap<>();
    }

    public RecipeAdvancement(CustomRecipeOutput output, ResourceLocation id) {
        this.output = Either.right(output);
        this.id = id;
        this.criteria = new LinkedHashMap<>();
    }

    public RecipeAdvancement unlockedBy(String criterionName, Criterion<?> criterionTrigger) {
        this.criteria.put(criterionName, criterionTrigger);
        return this;
    }

    public RecipeAdvancement unlockedByItems(String criterionName, ItemLike... items) {
        return this.unlockedBy(criterionName, InventoryChangeTrigger.TriggerInstance.hasItems(items));
    }

    public RecipeAdvancement unlockedByAnyIngredient(ItemLike... items) {
        this.criteria.put("has_any_ingredient", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(items).build()));
        return this;
    }

    public AdvancementHolder save() {
        Advancement.Builder builder = output.map(RecipeOutput::advancement, CustomRecipeOutput::advancement).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id)).rewards(AdvancementRewards.Builder.recipe(id)).requirements(AdvancementRequirements.Strategy.OR);
        Objects.requireNonNull(builder);
        this.criteria.forEach(builder::addCriterion);
        return builder.build(id.withPrefix("recipes/"));
    }
}
