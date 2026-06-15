package com.pouffydev.krystal_core.content;

import com.pouffydev.krystal_core.KrystalCore;
import com.pouffydev.krystal_core.content.player.soulbound.SoulboundInventory;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class KrystalAttachmentTypes {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = KrystalCore.getRegistryHelper().createRegister(NeoForgeRegistries.Keys.ATTACHMENT_TYPES);

    public static final Supplier<AttachmentType<SoulboundInventory>> SOULBOUND_INVENTORY = ATTACHMENT_TYPES.register("soulbound_inventory", () -> AttachmentType.builder(SoulboundInventory::create).serialize(SoulboundInventory.CODEC, inventory -> !inventory.isEmpty()).copyOnDeath().build());

    public static void staticInit() {}
}
