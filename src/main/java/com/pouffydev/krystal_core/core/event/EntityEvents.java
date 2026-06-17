package com.pouffydev.krystal_core.core.event;

import com.pouffydev.krystal_core.content.KrystalAttachmentTypes;
import com.pouffydev.krystal_core.content.KrystalDataComponents;
import com.pouffydev.krystal_core.content.item.equipment.KrystalArmorItem;
import com.pouffydev.krystal_core.content.player.soulbound.SoulboundInventory;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

@EventBusSubscriber
public class EntityEvents {

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            SoulboundInventory inventory = SoulboundInventory.create();

            for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                ItemStack stack = player.getInventory().getItem(i);

                if (stack.has(KrystalDataComponents.SOULBOUND)) {
                    inventory.add(i, stack);

                    player.getInventory().setItem(i, ItemStack.EMPTY);
                }
            }

            player.setData(KrystalAttachmentTypes.SOULBOUND_INVENTORY, inventory);
        }
    }

    @SubscribeEvent
    public static void entityTick(EntityTickEvent.Pre event) {
        Entity entity = event.getEntity();
        if (entity instanceof LivingEntity living) {
            for (ItemStack stack : living.getArmorSlots()) {
                if (stack.getItem() instanceof KrystalArmorItem armorItem) {
                    if (living.level().isClientSide()) {
                        armorItem.clientTick(living);
                    } else {
                        armorItem.tick(living);
                    }
                }
            }
        }
    }
}
