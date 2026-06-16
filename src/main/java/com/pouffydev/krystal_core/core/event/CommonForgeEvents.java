package com.pouffydev.krystal_core.core.event;

import com.pouffydev.krystal_core.KrystalCore;
import com.pouffydev.krystal_core.content.player.attribute.AttributesHelper;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
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

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void registerFallbackVanillaProviders(RegisterCapabilitiesEvent event) {
        if (KrystalCore.HONEY.isBound()) {
            event.registerItem(Capabilities.FluidHandler.ITEM, (stack, ctx) -> new FluidBucketWrapper(stack), KrystalCore.HONEY_BUCKET.get());
        }
        if (KrystalCore.POWDER_SNOW.isBound()) {
            event.registerItem(Capabilities.FluidHandler.ITEM, (stack, ctx) -> new FluidBucketWrapper(stack), Items.POWDER_SNOW_BUCKET);
        }
    }
}
