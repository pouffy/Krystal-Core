package com.pouffydev.krystal_core.content;

import com.pouffydev.krystal_core.KrystalCore;
import com.pouffydev.krystal_core.content.item.UseRemainder;
import com.pouffydev.krystal_core.content.player.soulbound.Soulbound;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class KrystalDataComponents {
    public static final DeferredRegister<DataComponentType<?>> COMPONENTS = KrystalCore.getRegistryHelper().componentsRegister();

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<BlockPos>> BLOCK_POS = COMPONENTS.register("block_pos", () -> DataComponentType.<BlockPos>builder().persistent(BlockPos.CODEC).networkSynchronized(BlockPos.STREAM_CODEC).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ResourceKey<Level>>> LEVEL_KEY = COMPONENTS.register("level_key", () -> DataComponentType.<ResourceKey<Level>>builder().persistent(ResourceKey.codec(Registries.DIMENSION)).networkSynchronized(ResourceKey.streamCodec(Registries.DIMENSION)).build());

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Soulbound>> SOULBOUND = COMPONENTS.register("soulbound", () -> DataComponentType.<Soulbound>builder().persistent(Soulbound.CODEC).networkSynchronized(Soulbound.STREAM_CODEC).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<UseRemainder>> USE_REMAINDER = COMPONENTS.register("use_remainder", () -> DataComponentType.<UseRemainder>builder().persistent(UseRemainder.CODEC).networkSynchronized(UseRemainder.STREAM_CODEC).build());

    public static void staticInit() {}
}
