package com.pouffydev.krystal_core.foundation.dynamicpack.data.recipe.output;

import com.pouffydev.krystal_core.foundation.dynamicpack.data.recipe.custom.CustomRecipe;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.conditions.ICondition;
import org.jetbrains.annotations.Nullable;

public interface ICustomRecipeOutputExtension {
    private CustomRecipeOutput self() {
        return (CustomRecipeOutput)this;
    }

    void accept(ResourceLocation id, CustomRecipe<?> recipe, @Nullable AdvancementHolder advancement, ICondition... conditions);

    default CustomRecipeOutput withConditions(ICondition... conditions) {
        return new ConditionalCustomRecipeOutput(this.self(), conditions);
    }
}
