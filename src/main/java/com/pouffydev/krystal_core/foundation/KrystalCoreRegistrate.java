package com.pouffydev.krystal_core.foundation;

import com.tterrag.registrate.AbstractRegistrate;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
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
}
