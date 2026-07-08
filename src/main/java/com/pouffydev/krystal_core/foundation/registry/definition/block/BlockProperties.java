package com.pouffydev.krystal_core.foundation.registry.definition.block;

import com.pouffydev.krystal_core.foundation.data.loot.BlockLootType;
import com.pouffydev.krystal_core.foundation.data.loot.CustomBlockLootType;

public record BlockProperties(BlockLootType lootType, String customLang) {
    public static BlockProperties custom(String customLang) {
        return new BlockProperties(new CustomBlockLootType(), customLang);
    }
}
