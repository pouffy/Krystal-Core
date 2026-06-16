package com.pouffydev.krystal_core.content.block;

import com.pouffydev.krystal_core.foundation.utility.Couple;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public abstract class TripleHighBlock extends Block {
    public static final EnumProperty<Shape> SHAPE = EnumProperty.create("shape", Shape.class);

    public TripleHighBlock(Properties properties) {
        super(properties);
        this.defaultBlockState().setValue(SHAPE, Shape.LOWER);
    }

    protected BlockState updateShape(BlockState state, Direction direction, BlockState otherState, LevelAccessor level, BlockPos pos, BlockPos otherPos) {
        Shape shape = state.getValue(SHAPE);
        if (direction.getAxis() == Direction.Axis.Y) {
            if ((shape == Shape.LOWER || shape == Shape.MIDDLE) == (direction == Direction.UP)) {
                return otherState.getBlock() instanceof TripleHighBlock && otherState.getValue(SHAPE) != shape ? otherState.setValue(SHAPE, shape) : Blocks.AIR.defaultBlockState();
            }
            if ((shape == Shape.UPPER || shape == Shape.MIDDLE) == (direction == Direction.DOWN)) {
                return otherState.getBlock() instanceof TripleHighBlock && otherState.getValue(SHAPE) != shape ? otherState.setValue(SHAPE, shape) : Blocks.AIR.defaultBlockState();
            }
        }
        return shape == Shape.LOWER && direction == Direction.DOWN && !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, otherState, level, pos, otherPos);
    }

    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide && (player.isCreative() || !player.hasCorrectToolForDrops(state, level, pos))) {
            preventDropFromOtherParts(level, pos, state, player);
        }

        return super.playerWillDestroy(level, pos, state, player);
    }

    protected static void preventDropFromOtherParts(Level level, BlockPos pos, BlockState state, Player player) {
        Shape shape = state.getValue(SHAPE);
        shape.getParts(pos).forEach(part -> {
            BlockState blockstate = level.getBlockState(part);
            if (blockstate.is(state.getBlock())) {
                BlockState blockstate1 = blockstate.getFluidState().is(Fluids.WATER) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
                level.setBlock(part, blockstate1, 35);
                level.levelEvent(player, 2001, part, Block.getId(blockstate));
            }
        });
    }

    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        level.setBlock(pos, state.setValue(SHAPE, Shape.LOWER), 3);
        level.setBlock(pos.above(), state.setValue(SHAPE, Shape.MIDDLE), 3);
        level.setBlock(pos.above(2), state.setValue(SHAPE, Shape.UPPER), 3);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SHAPE);
        super.createBlockStateDefinition(builder);
    }

    public enum Shape implements StringRepresentable {
        UPPER,
        MIDDLE,
        LOWER
        ;

        public static Shape getTypeForConnections(boolean connectUp, boolean connectDown) {
            if (connectUp && connectDown) {
                return Shape.MIDDLE;
            } else if (connectUp) {
                return Shape.LOWER;
            } else if (connectDown) {
                return Shape.UPPER;
            }

            return Shape.LOWER;
        }

        public Couple<BlockPos> getParts(BlockPos pos) {
            return switch (this) {
                case LOWER -> Couple.create(pos.above(), pos.above(2));
                case UPPER -> Couple.create(pos.below(), pos.below(2));
                case MIDDLE -> Couple.create(pos.below(), pos.above());
            };
        }

        public BlockPos getPart(BlockPos pos, Shape part) {
            return switch (this) {
                case LOWER -> part == UPPER ? pos.above(2) : pos.above();
                case UPPER -> part == LOWER ? pos.below(2) : pos.below();
                case MIDDLE -> part == LOWER ? pos.below() : pos.above();
            };
        }

        public Shape getOpposite() {
            return switch (this) {
                case LOWER -> Shape.UPPER;
                case UPPER -> Shape.LOWER;
                case MIDDLE -> Shape.MIDDLE;
            };
        }

        @Override
        public @NotNull String getSerializedName() {
            return this.name().toLowerCase();
        }
    }
}
