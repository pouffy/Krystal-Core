package com.pouffydev.krystal_core.core.event;

import com.pouffydev.krystal_core.content.KrystalAttachmentTypes;
import com.pouffydev.krystal_core.content.KrystalDataComponents;
import com.pouffydev.krystal_core.content.player.soulbound.SoulboundInventory;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

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
}
