package com.pouffydev.krystal_core.foundation.bundle.runtime;

import com.pouffydev.krystal_core.KrystalCore;
import com.pouffydev.krystal_core.foundation.bundle.Bundle;
import com.pouffydev.krystal_core.foundation.dynamicpack.data.recipe.custom.CustomRecipe;
import com.pouffydev.krystal_core.foundation.dynamicpack.data.recipe.output.CustomRecipeOutput;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.neoforged.neoforge.common.conditions.ICondition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.function.Consumer;

public class BundledRecipes {
    public static final Set<ResourceLocation> RECIPE_FILTERS = new ObjectOpenHashSet<>();

    public static void recipeAddition(RecipeOutput output) {
        RecipeOutput consumer = new RecipeOutput() {
            @Override
            public Advancement.@NotNull Builder advancement() {
                return output.advancement();
            }
            @Override
            public void accept(@NotNull ResourceLocation id, @NotNull Recipe<?> recipe, @Nullable AdvancementHolder advancement, ICondition @NotNull... conditions) {
                if (!RECIPE_FILTERS.contains(id)) {
                    output.accept(id, recipe, advancement, conditions);
                }
            }
        };
        for (Bundle bundle : KrystalCore.INSTANCE.BUNDLES.values()) {
            if (!bundle.isLoaded()) {
                continue;
            }
            var recipeHandler = bundle.getRecipeHandler();
            if (recipeHandler == null) {
                continue;
            }
            recipeHandler.run(consumer);
        }
    }

    public static void customDataAddition(CustomRecipeOutput output) {
        CustomRecipeOutput consumer = new CustomRecipeOutput() {
            @Override
            public Advancement.@NotNull Builder advancement() {
                return output.advancement();
            }
            @Override
            public void accept(@NotNull ResourceLocation id, @NotNull CustomRecipe<?> recipe, @Nullable AdvancementHolder advancement, ICondition @NotNull... conditions) {
                if (!RECIPE_FILTERS.contains(id)) {
                    output.accept(id, recipe, advancement, conditions);
                }
            }
        };
        for (Bundle bundle : KrystalCore.INSTANCE.BUNDLES.values()) {
            if (!bundle.isLoaded()) {
                continue;
            }
            var recipeHandler = bundle.getRecipeHandler();
            if (recipeHandler == null) {
                continue;
            }
            recipeHandler.runCustom(consumer);
        }
    }

    public static void recipeRemoval(Consumer<ResourceLocation> registry) {
        final Consumer<ResourceLocation> actualConsumer = registry.andThen(RECIPE_FILTERS::add);
        RECIPE_FILTERS.clear();
        for (Bundle bundle : KrystalCore.INSTANCE.BUNDLES.values()) {
            if (!bundle.isLoaded()) {
                continue;
            }
            var recipeHandler = bundle.getRecipeHandler();
            if (recipeHandler == null) {
                continue;
            }
            recipeHandler.removeRecipes(actualConsumer);
        }
    }
}
