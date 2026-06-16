package com.pouffydev.krystal_core.content.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class TripleHighBlockItem extends BlockItem {
    public TripleHighBlockItem(TripleHighBlock block, Properties properties) {
        super(block, properties);
    }

    protected boolean placeBlock(BlockPlaceContext context, BlockState state) {
        Level level = context.getLevel();
        BlockPos middle = context.getClickedPos().above();
        BlockPos upper = context.getClickedPos().above(2);
        if (!level.getBlockState(middle).canBeReplaced(context) || !level.getBlockState(upper).canBeReplaced(context)) {
            return false;
        }
        BlockState middleState = Blocks.AIR.defaultBlockState();
        BlockState upperState = Blocks.AIR.defaultBlockState();
        if (state.hasProperty(BlockStateProperties.WATERLOGGED)) {
            middleState = level.isWaterAt(middle) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
            upperState = level.isWaterAt(upper) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
        }
        level.setBlock(middle, middleState, 27);
        level.setBlock(upper, upperState, 27);
        return super.placeBlock(context, state);
    }
}
