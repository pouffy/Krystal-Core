package com.pouffydev.krystal_core.foundation.registry.definition.fluid;

import com.pouffydev.krystal_core.foundation.registry.RegistryHelper;
import com.pouffydev.krystal_core.foundation.registry.RegistryManager;
import com.pouffydev.krystal_core.foundation.registry.definition.item.ItemRegistryHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;

public class FluidRegistryHelper extends RegistryHelper<Fluid> {
    public final List<FluidDefinition<?>> FLUID_DEFINITIONS;
    public final ItemRegistryHelper itemRegistryHelper;

    public FluidRegistryHelper(ItemRegistryHelper itemRegistryHelper) {
        this.itemRegistryHelper = itemRegistryHelper;
        this.FLUID_DEFINITIONS = new ArrayList<>();
    }

    public final DeferredRegister<Fluid> FLUIDS = RegistryManager.getInstance().createRegister(Registries.FLUID);

}
