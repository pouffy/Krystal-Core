package com.pouffydev.krystal_core.content.block.suspicious;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class SuspiciousBlock extends BrushableBlock {
    public SuspiciousBlock(Block baseBlock, BlockBehaviour.Properties settings, SoundEvent brushingSound, SoundEvent brushingCompleteSound) {
        super(baseBlock, brushingCompleteSound, brushingSound, settings);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SuspiciousBlockEntity(pos, state);
    }
}
