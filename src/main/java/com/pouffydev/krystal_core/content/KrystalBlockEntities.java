package com.pouffydev.krystal_core.content;

import com.pouffydev.krystal_core.KrystalCore;
import com.pouffydev.krystal_core.content.block.suspicious.SuspiciousBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class KrystalBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = KrystalCore.getRegistryHelper().createRegister(Registries.BLOCK_ENTITY_TYPE);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SuspiciousBlockEntity>> SUSPICIOUS_BLOCK =
            BLOCK_ENTITIES.register("suspicious_block", () -> BlockEntityType.Builder.of(SuspiciousBlockEntity::new).build(null));

    public static void staticInit() {}
}
