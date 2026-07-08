package com.pouffydev.krystal_core.foundation.registry.definition.block;

import com.pouffydev.krystal_core.foundation.registry.DeferredHolderSet;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;

public class BlockDefinitionSet<T extends Block, K extends Enum<K> & StringRepresentable> extends DeferredHolderSet<Block, T, K, BlockDefinition<T>> {

    public BlockDefinitionSet(Class<K> keyType) {
        super(keyType);
    }
}
