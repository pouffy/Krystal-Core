package com.pouffydev.krystal_core.core.event;

import com.pouffydev.krystal_core.content.KrystalAttachmentTypes;
import com.pouffydev.krystal_core.content.player.soulbound.SoulboundInventory;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class PlayerEvents {

    @SubscribeEvent
    public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            SoulboundInventory inventory = player.getData(KrystalAttachmentTypes.SOULBOUND_INVENTORY);

            for (SoulboundInventory.Entry entry : inventory.entries()) {
                player.getInventory().setItem(entry.slot(), entry.stack());
            }
        }
    }
}
