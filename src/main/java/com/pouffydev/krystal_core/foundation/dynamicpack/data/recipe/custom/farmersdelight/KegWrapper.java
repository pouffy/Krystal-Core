package com.pouffydev.krystal_core.foundation.dynamicpack.data.recipe.custom.farmersdelight;

import net.minecraft.world.item.crafting.RecipeInput;
import net.neoforged.neoforge.fluids.FluidStack;

public interface KegWrapper extends RecipeInput {
    FluidStack getFluid();
    long getTankCapacity();

    @Override
    default boolean isEmpty() {
        if (getFluid().isEmpty())
            return false;
        return RecipeInput.super.isEmpty();
    }
}
