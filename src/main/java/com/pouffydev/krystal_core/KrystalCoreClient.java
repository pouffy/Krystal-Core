package com.pouffydev.krystal_core;

import com.pouffydev.krystal_core.client.model.item.CurioModel;
import com.pouffydev.krystal_core.client.renderer.CurioRenderer;
import com.pouffydev.krystal_core.client.renderer.block.SuspiciousBlockEntityRenderer;
import com.pouffydev.krystal_core.content.KrystalBlockEntities;
import com.pouffydev.krystal_core.content.item.IRenderableCurio;
import com.pouffydev.krystal_core.content.item.exploration.NavigationHelper;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.item.CompassItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

@EventBusSubscriber(value = Dist.CLIENT, modid = KrystalCore.ID)
public class KrystalCoreClient {

    @SubscribeEvent
    public static void clientInit(FMLClientSetupEvent event) {
        CuriosRenderers.register();
        registerModelPredicates();

        BlockEntityRenderers.register(KrystalBlockEntities.SUSPICIOUS_BLOCK.get(), SuspiciousBlockEntityRenderer::new);
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

    @SubscribeEvent
    static void registerLayerDefinitions(final EntityRenderersEvent.RegisterLayerDefinitions event) {
        CuriosRenderers.onLayerRegister(event);
    }

    public static class CuriosRenderers {
        public static void register() {
            for (Item item : BuiltInRegistries.ITEM.stream().toList()) {
                if (!(item instanceof IRenderableCurio))
                    continue;

                CuriosRendererRegistry.register(item, CurioRenderer::new);
            }
        }

        public static void onLayerRegister(final EntityRenderersEvent.RegisterLayerDefinitions event) {
            for (Item item : BuiltInRegistries.ITEM.stream().toList()) {
                if (!(item instanceof IRenderableCurio renderable))
                    continue;

                event.registerLayerDefinition(CurioModel.getLayerLocation(item), renderable::constructLayerDefinition);
            }
        }
    }
}
