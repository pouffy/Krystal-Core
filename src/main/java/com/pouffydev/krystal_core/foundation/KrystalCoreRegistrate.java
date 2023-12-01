package com.pouffydev.krystal_core.foundation;

import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.FluidBuilder;
import com.tterrag.registrate.util.nullness.NonNullBiFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.DistExecutor;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class KrystalCoreRegistrate extends AbstractRegistrate<KrystalCoreRegistrate> {
    /**
     * Construct a new Registrate for the given mod ID.
     *
     * @param modid The mod ID for which objects will be registered
     */
    protected KrystalCoreRegistrate(String modid) {
        super(modid);
    }
    public static KrystalCoreRegistrate create(String modid) {
        return new KrystalCoreRegistrate(modid);
    }
    
    @Override
    public @NotNull KrystalCoreRegistrate registerEventListeners(@NotNull IEventBus bus) {
        return super.registerEventListeners(bus);
    }
    protected static void onClient(Supplier<Runnable> toRun) {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, toRun);
    }
    protected static void onServer(Supplier<Runnable> toRun) {
        DistExecutor.unsafeRunWhenOn(Dist.DEDICATED_SERVER, toRun);
    }
    
    public FluidBuilder<ForgeFlowingFluid.Flowing, KrystalCoreRegistrate> standardMWFluid(String name, String ID) {
        return fluid(name, new ResourceLocation(ID, "fluid/" + name + "/still"), new ResourceLocation(ID, "fluid/" + name + "/flowing"));
    }
    
    public FluidBuilder<ForgeFlowingFluid.Flowing, KrystalCoreRegistrate> standardMWFluid(String name, String ID, NonNullBiFunction<FluidAttributes.Builder, Fluid, FluidAttributes> attributesFactory) {
        return fluid(name, new ResourceLocation(ID, "fluid/" + name + "/still"), new ResourceLocation(ID, "fluid/" + name + "/flowing"), attributesFactory);
    }
}
