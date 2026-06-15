package com.pouffydev.krystal_core;

import com.pouffydev.krystal_core.content.item.exploration.NavigationHelper;
import net.minecraft.client.renderer.item.CompassItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

@EventBusSubscriber(value = Dist.CLIENT, modid = KrystalCore.ID)
public class KrystalCoreClient {

    @SubscribeEvent
    public static void clientInit(FMLClientSetupEvent event) {
        registerModelPredicates();
    }

    public static void registerModelPredicates() {
        ItemProperties.registerGeneric(KrystalCore.location("count"), (pStack, pLevel, pEntity, pSeed) -> ((float) pStack.getCount()) / pStack.getMaxStackSize());
        ItemProperties.registerGeneric(KrystalCore.location("compass_angle"), new CompassItemPropertyFunction((pClientLevel, pStack, pEntity) -> NavigationHelper.getTargetPosition(pStack)));
    }

    @SubscribeEvent
    static void onRegisterClientExtensions(RegisterClientExtensionsEvent event) {
        KrystalCore.POWDER_SNOW_TYPE.asOptional().ifPresent(snowType -> event.registerFluidType(new IClientFluidTypeExtensions() {
            private static final ResourceLocation SNOW_STILL = ResourceLocation.withDefaultNamespace("block/powder_snow");
            private static final ResourceLocation SNOW_FLOW = KrystalCore.location("block/powder_snow_flowing");

            @Override
            public ResourceLocation getStillTexture() {
                return SNOW_STILL;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return SNOW_FLOW;
            }
        }, snowType));
        KrystalCore.HONEY_TYPE.asOptional().ifPresent(honeyType -> event.registerFluidType(new IClientFluidTypeExtensions() {
            private static final ResourceLocation HONEY_STILL = KrystalCore.location("block/honey_still");
            private static final ResourceLocation HONEY_FLOW = KrystalCore.location("block/honey_flowing");

            @Override
            public ResourceLocation getStillTexture() {
                return HONEY_STILL;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return HONEY_FLOW;
            }
        }, honeyType));
    }
}
