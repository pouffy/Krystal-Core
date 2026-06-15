package com.pouffydev.krystal_core.content.item.exploration;

import com.pouffydev.krystal_core.content.KrystalDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class NavigationHelper {

    @Nullable
    public static GlobalPos getTargetPosition(ItemStack stack) {
        if (stack.has(KrystalDataComponents.BLOCK_POS) && stack.has(KrystalDataComponents.LEVEL_KEY)) {
            BlockPos blockPos = stack.get(KrystalDataComponents.BLOCK_POS);
            ResourceKey<Level> levelKey = stack.get(KrystalDataComponents.LEVEL_KEY);
            return (levelKey != null && blockPos != null) ? GlobalPos.of(levelKey, blockPos) : null;
        } else {
            return null;
        }
    }
}
