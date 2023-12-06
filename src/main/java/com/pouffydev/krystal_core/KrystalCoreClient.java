package com.pouffydev.krystal_core;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class KrystalCoreClient {
    
    public static void onCtorClient(IEventBus modEventBus, IEventBus forgeEventBus) {
        modEventBus.addListener(KrystalCoreClient::clientInit);
    }
    public static void clientInit(FMLClientSetupEvent event) {
        registerModelPredicates();
    }
    public static void registerModelPredicates() {
        ItemProperties.registerGeneric(new ResourceLocation(KrystalCore.ID, "count"), (pStack, pLevel, pEntity, pSeed) -> ((float) pStack.getCount()) / pStack.getMaxStackSize());
    }
}
