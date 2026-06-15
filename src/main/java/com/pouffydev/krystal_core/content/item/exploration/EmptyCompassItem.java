package com.pouffydev.krystal_core.content.item.exploration;

import com.pouffydev.krystal_core.content.KrystalDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.Structure;

public class EmptyCompassItem extends Item {

    private final TagKey<Structure> structureTag;
    private final ItemLike compass;
    private final int searchRadius;
    private final boolean skipExistingChunks;

    public EmptyCompassItem(Properties properties, TagKey<Structure> structureTag, ItemLike compass, int searchRadius, boolean skipExistingChunks) {
        super(properties);
        this.structureTag = structureTag;
        this.compass = compass;
        this.searchRadius = searchRadius;
        this.skipExistingChunks = skipExistingChunks;
    }

    public EmptyCompassItem(Properties properties, TagKey<Structure> structureTag, ItemLike compass) {
        this(properties, structureTag, compass, 100, true);
    }

    public SoundEvent useSound() {
        return SoundEvents.LODESTONE_COMPASS_LOCK;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (level.isClientSide) {
            return InteractionResultHolder.success(itemstack);
        } else {
            if (level instanceof ServerLevel serverLevel) {
                BlockPos blockpos = serverLevel.findNearestMapStructure(this.structureTag, BlockPos.containing(player.position()), this.searchRadius, this.skipExistingChunks);
                if (blockpos != null) {
                    itemstack.consume(1, player);
                    player.awardStat(Stats.ITEM_USED.get(this));
                    player.level().playSound(null, player, useSound(), player.getSoundSource(), 1.0F, 1.0F);
                    ItemStack stack = new ItemStack(this.compass);
                    stack.set(KrystalDataComponents.BLOCK_POS, blockpos);
                    stack.set(KrystalDataComponents.LEVEL_KEY, serverLevel.dimension());
                    if (itemstack.isEmpty()) {
                        return InteractionResultHolder.consume(stack);
                    } else {
                        if (!player.getInventory().add(stack.copy())) {
                            player.drop(stack, false);
                        }
                        return InteractionResultHolder.consume(itemstack);
                    }
                }
            }
            return InteractionResultHolder.pass(itemstack);
        }
    }
}
