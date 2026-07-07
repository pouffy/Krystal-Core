package com.pouffydev.krystal_core.foundation.registry.definition;

import com.pouffydev.krystal_core.foundation.data.loot.BlockLootType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;

public class BlockDefinition<T extends Block> extends ItemLikeDefinition<Block, T> {
    private final BlockProperties properties;

    protected BlockDefinition(ResourceKey<Block> key, BlockProperties properties) {
        super(key, properties.customLang());
        this.properties = properties;
    }

    protected BlockDefinition(ResourceKey<Block> key) {
        this(key, BlockProperties.custom(""));
    }

    public static <T extends Block> BlockDefinition<T> fromHolder(DeferredBlock<T> holder) {
        return fromHolder(holder, BlockProperties.custom(""));
    }

    public static <T extends Block> BlockDefinition<T> fromHolder(DeferredBlock<T> holder, BlockProperties properties) {
        return new BlockDefinition<T>(holder.getKey(), properties);
    }

    public boolean is(Block block) {
        return this.get().equals(block);
    }

    public String langKey() {
        return "block." + super.langKey();
    }

    public BlockLootType lootType() {
        return this.properties.lootType();
    }

    public Block block() {
        return this.get();
    }

    public BlockProperties properties() {
        return this.properties;
    }
}
