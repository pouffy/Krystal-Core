package com.pouffydev.krystal_core.foundation.registry.definition.fluid;

import com.pouffydev.krystal_core.foundation.registry.definition.TranslatableDefinition;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;
import net.neoforged.neoforge.registries.DeferredHolder;

public class FluidDefinition<T extends Fluid> extends TranslatableDefinition<Fluid, T> {
    private final FluidProperties properties;

    protected FluidDefinition(ResourceKey<Fluid> key, FluidProperties properties) {
        super(key, properties.customLang());
        this.properties = properties;
    }

    public FluidDefinition(ResourceKey<Fluid> key) {
        this(key, FluidProperties.custom(""));
    }

    public static <T extends Fluid> FluidDefinition<T> fromHolder(DeferredHolder<Fluid, T> holder) {
        return fromHolder(holder, FluidProperties.custom(""));
    }

    public static <T extends Fluid> FluidDefinition<T> fromHolder(DeferredHolder<Fluid, T> holder, FluidProperties properties) {
        return new FluidDefinition<T>(holder.getKey(), properties);
    }

    public boolean is(Fluid fluid) {
        return this.get().equals(fluid);
    }

    public String langKey() {
        return "fluid." + super.langKey();
    }

    public Item bucket() {
        return this.get().getBucket();
    }

    public boolean hasBucket() {
        return this.get().getBucket() != Items.AIR;
    }

    public SizedFluidIngredient ingredient() {
        return this.ingredient(1000);
    }

    public SizedFluidIngredient ingredient(int count) {
        return SizedFluidIngredient.of(new FluidStack(this.get(), count));
    }

    public FluidStack stack() {
        return this.stack(1000);
    }

    public FluidStack stack(int count) {
        return new FluidStack(this.get(), count);
    }
}
