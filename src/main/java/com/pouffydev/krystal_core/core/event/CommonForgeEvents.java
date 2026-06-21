package com.pouffydev.krystal_core.core.event;

import com.pouffydev.krystal_core.KrystalCore;
import com.pouffydev.krystal_core.content.KrystalBlockEntities;
import com.pouffydev.krystal_core.content.block.suspicious.SuspiciousBlock;
import com.pouffydev.krystal_core.content.effect.IDamageAltering;
import com.pouffydev.krystal_core.content.player.attribute.AttributesHelper;
import com.pouffydev.krystal_core.foundation.dynamicpack.KrystalPackSource;
import com.pouffydev.krystal_core.foundation.dynamicpack.data.BundleDynamicDataPack;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.fluids.capability.wrappers.FluidBucketWrapper;

@EventBusSubscriber(modid = KrystalCore.ID)
public class CommonForgeEvents {

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onEntityAdded(EntityJoinLevelEvent event) {
        AttributesHelper.entityAdded(event);
    }

    @SubscribeEvent
    public static void incomingDamage(LivingIncomingDamageEvent event) {
        AttributesHelper.incomingDamage(event);
    }

    @SubscribeEvent
    public static void onHurt(LivingDamageEvent.Pre event) {
        event.getEntity().getActiveEffectsMap().forEach((effect, inst) -> {
            if (effect.value() instanceof IDamageAltering damageAltering) {
                damageAltering.modifyDamage(event, inst.getAmplifier());
            }
        });
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void registerFallbackVanillaProviders(RegisterCapabilitiesEvent event) {
        if (KrystalCore.HONEY.isBound()) {
            event.registerItem(Capabilities.FluidHandler.ITEM, (stack, ctx) -> new FluidBucketWrapper(stack), KrystalCore.HONEY_BUCKET.get());
        }
        if (KrystalCore.POWDER_SNOW.isBound()) {
            event.registerItem(Capabilities.FluidHandler.ITEM, (stack, ctx) -> new FluidBucketWrapper(stack), Items.POWDER_SNOW_BUCKET);
        }
    }

    @SubscribeEvent
    public static void addBlocksToEntity(BlockEntityTypeAddBlocksEvent event) {
        for (var block : BuiltInRegistries.BLOCK) {
            if (block instanceof SuspiciousBlock suspiciousBlock) {
                event.modify(KrystalBlockEntities.SUSPICIOUS_BLOCK.get(), suspiciousBlock);
            }
        }
    }

    @SubscribeEvent
    public static void addPackFinders(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.SERVER_DATA) {
            BundleDynamicDataPack.clearServer();
            event.addRepositorySource(new KrystalPackSource("krystal_core:dynamic_bundle_data",
                    event.getPackType(),
                    Pack.Position.BOTTOM,
                    BundleDynamicDataPack::new));
        }
    }

    @SubscribeEvent
    public static void addToTab(BuildCreativeModeTabContentsEvent event) {
        for (var manager : KrystalCore.INSTANCE.BUNDLE_MANAGERS.values()) {
            manager.forEach(bundle -> bundle.addCreative(event), true);
        }
    }
}
