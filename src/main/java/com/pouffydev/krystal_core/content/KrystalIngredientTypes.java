package com.pouffydev.krystal_core.content;

import com.pouffydev.krystal_core.KrystalCore;
import com.pouffydev.krystal_core.foundation.dynamicpack.data.ExDataComponentIngredient;
import net.neoforged.neoforge.common.crafting.IngredientType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class KrystalIngredientTypes {
    public static final DeferredRegister<IngredientType<?>> TYPES = KrystalCore.getRegistryManager().createRegister(NeoForgeRegistries.Keys.INGREDIENT_TYPES);

    public static final DeferredHolder<IngredientType<?>, IngredientType<ExDataComponentIngredient>> DATA_COMPONENT_INGREDIENT = TYPES.register("components", () -> new IngredientType<>(ExDataComponentIngredient.CODEC));
}
