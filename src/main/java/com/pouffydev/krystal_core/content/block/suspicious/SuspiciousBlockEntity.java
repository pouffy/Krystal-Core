package com.pouffydev.krystal_core.content.block.suspicious;

import com.pouffydev.krystal_core.content.KrystalBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SuspiciousBlockEntity extends BrushableBlockEntity {
    public SuspiciousBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public BlockEntityType<?> getType() {
        return KrystalBlockEntities.SUSPICIOUS_BLOCK.get();
    }
}
