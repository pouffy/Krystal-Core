package com.pouffydev.krystal_core.foundation.registry.block;

import com.pouffydev.krystal_core.foundation.registry.DeferredHolderSet;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;

public class BlockHolderSet<T extends Block, K extends Enum<K> & StringRepresentable> extends DeferredHolderSet<Block, T, K, DeferredHolder<Block, T>> {

    public BlockHolderSet(Class<K> keyType) {
        super(keyType);
    }
}
