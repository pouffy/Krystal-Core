package com.pouffydev.krystal_core;

import com.mojang.logging.LogUtils;
import com.pouffydev.krystal_core.foundation.CommonEvents;
import com.pouffydev.krystal_core.foundation.KrystalCoreRegistrate;
import com.pouffydev.krystal_core.foundation.data.KCRegistrateTags;
import com.pouffydev.krystal_core.init.KCDebugItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.stream.Collectors;

@Mod(KrystalCore.ID)
public class KrystalCore
{
    /**
     * Krystal Core's Mod ID
     */
    public static final String ID = "krystal_core";
    /**
     * Krystal Core's Registrate
     */
    public static final KrystalCoreRegistrate registrate = KrystalCoreRegistrate.create(KrystalCore.ID);
    /**
     * Krystal Core's Logger
     */
    public static final Logger LOGGER = LogUtils.getLogger();
    private static final boolean isDevelopmentEnvironment = !FMLEnvironment.production;
    public KrystalCore()
    {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        
        MinecraftForge.EVENT_BUS.register(this);
        
        if(isDevelopmentEnvironment) {
            KCDebugItems.register();
        }
        
        eventBus.addListener(CommonEvents::init);
        registrate.registerEventListeners(eventBus);
        eventBus.addListener(EventPriority.LOWEST, KrystalCore::gatherData);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> KrystalCoreClient.onCtorClient(eventBus, forgeEventBus));
    }
    
    public static void gatherData(GatherDataEvent event) {
        KCRegistrateTags.addGenerators();
    }
    private void setup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getDescriptionId());
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        InterModComms.sendTo(KrystalCore.ID, "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.messageSupplier().get()).
                collect(Collectors.toList()));
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        LOGGER.info("HELLO from server starting");
    }
    
    @SubscribeEvent
    public void jsonReading(AddReloadListenerEvent event) {
        //event.addListener(CompostableJsonListener.instance);
    }
    @Contract("_ -> new")
    public static @NotNull ResourceLocation asResource(String path) {
        return new ResourceLocation(ID, path);
    }
    public static @NotNull KrystalCoreRegistrate registrate() {
        return registrate;
    }
}
