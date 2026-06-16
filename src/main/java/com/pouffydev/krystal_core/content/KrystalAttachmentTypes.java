package com.pouffydev.krystal_core.content;

import com.mojang.serialization.Codec;
import com.pouffydev.krystal_core.KrystalCore;
import com.pouffydev.krystal_core.content.player.soulbound.SoulboundInventory;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class KrystalAttachmentTypes {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = KrystalCore.getRegistryHelper().createRegister(NeoForgeRegistries.Keys.ATTACHMENT_TYPES);

    public static final Supplier<AttachmentType<SoulboundInventory>> SOULBOUND_INVENTORY = ATTACHMENT_TYPES.register("soulbound_inventory", () -> AttachmentType.builder(SoulboundInventory::create).serialize(SoulboundInventory.CODEC, inventory -> !inventory.isEmpty()).copyOnDeath().build());

    public static final Supplier<AttachmentType<Double>> DAMAGE_MULTIPLIER = ATTACHMENT_TYPES.register("total_damage_multiplier", () -> AttachmentType.builder(() -> 1.0).serialize(Codec.DOUBLE).build());
    public static final Supplier<AttachmentType<Double>> CRIT_CHANCE = ATTACHMENT_TYPES.register("critical_strike_chance_multiplier", () -> AttachmentType.builder(() -> 0.0).serialize(Codec.DOUBLE).build());

    public static void staticInit() {}
}
