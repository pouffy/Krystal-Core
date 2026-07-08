package com.pouffydev.krystal_core.foundation.registry.definition.fluid;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;

public class FlowingFluidDefinition<T extends BaseFlowingFluid> extends FluidDefinition<T> {

    public FlowingFluidDefinition(ResourceKey<Fluid> key) {
        super(key);
    }

    protected FlowingFluidDefinition(ResourceKey<Fluid> key, FluidProperties properties) {
        super(key, properties);
    }

    @Override
    public Item bucket() {
        return this.get().getBucket();
    }

    @Override
    public boolean hasBucket() {
        return this.get().getBucket() != Items.AIR;
    }

    public Fluid getFlowing() {
        return this.get().getFlowing();
    }

    public Fluid getSource() {
        return this.get().getSource();
    }
}
