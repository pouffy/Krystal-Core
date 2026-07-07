package com.pouffydev.krystal_core.foundation.data.loot;

import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class OtherBlockLootType extends BlockLootType {
    private final Supplier<Block> block;

    public OtherBlockLootType(Supplier<Block> block) {
        this.block = block;
    }

    public ItemLike getBlock() {
        return this.block.get();
    }
}
