package com.pouffydev.krystal_core;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;

public class KrystalCoreClient {
    
    public static void onCtorClient(IEventBus modEventBus, IEventBus forgeEventBus) {
        modEventBus.addListener(KrystalCoreClient::clientInit);
    }
    public static void clientInit(final ModelRegistryEvent event) {
        registerModelPredicates();
    }
    public static void registerModelPredicates() {
        ItemProperties.registerGeneric(new ResourceLocation(KrystalCore.ID, "count"), (pStack, pLevel, pEntity, pSeed) -> ((float) pStack.getCount()) / pStack.getMaxStackSize());
    }
}
