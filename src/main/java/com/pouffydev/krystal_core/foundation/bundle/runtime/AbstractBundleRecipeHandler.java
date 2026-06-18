package com.pouffydev.krystal_core.foundation.bundle.runtime;

import com.pouffydev.krystal_core.foundation.dynamicpack.data.recipe.output.CustomRecipeOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public abstract class AbstractBundleRecipeHandler {
    public abstract void run(@NotNull RecipeOutput output);

    public abstract void runCustom(@NotNull CustomRecipeOutput output);

    public void removeRecipes(@NotNull Consumer<ResourceLocation> consumer) {}
}
