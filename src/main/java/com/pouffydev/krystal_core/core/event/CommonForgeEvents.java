package com.pouffydev.krystal_core.core.event;

import com.pouffydev.krystal_core.KrystalCore;
import com.pouffydev.krystal_core.content.player.attribute.AttributesHelper;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

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
}
