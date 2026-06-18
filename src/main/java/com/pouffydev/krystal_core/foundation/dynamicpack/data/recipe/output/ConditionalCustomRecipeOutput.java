package com.pouffydev.krystal_core.foundation.dynamicpack.data.recipe.output;

import com.pouffydev.krystal_core.foundation.dynamicpack.data.recipe.custom.CustomRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.conditions.ICondition;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.Nullable;

public class ConditionalCustomRecipeOutput implements CustomRecipeOutput {
    private final CustomRecipeOutput inner;
    private final ICondition[] conditions;

    public ConditionalCustomRecipeOutput(CustomRecipeOutput inner, ICondition[] conditions) {
        this.inner = inner;
        this.conditions = conditions;
    }

    public Advancement.Builder advancement() {
        return this.inner.advancement();
    }

    public void accept(ResourceLocation id, CustomRecipe<?> recipe, @Nullable AdvancementHolder advancement, ICondition... conditions) {
        ICondition[] innerConditions;
        if (conditions.length == 0) {
            innerConditions = this.conditions;
        } else if (this.conditions.length == 0) {
            innerConditions = conditions;
        } else {
            innerConditions = ArrayUtils.addAll(this.conditions, conditions);
        }

        this.inner.accept(id, recipe, advancement, innerConditions);
    }
}
