package com.pouffydev.krystal_core.foundation.dynamicpack.data.recipe.output;

import com.pouffydev.krystal_core.foundation.dynamicpack.data.recipe.custom.CustomRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.conditions.ICondition;

import javax.annotation.Nullable;

public interface CustomRecipeOutput extends ICustomRecipeOutputExtension {
    default void accept(ResourceLocation id, CustomRecipe<?> recipe, @Nullable AdvancementHolder advancement) {
        this.accept(id, recipe, advancement, new ICondition[0]);
    }

    Advancement.Builder advancement();
}
