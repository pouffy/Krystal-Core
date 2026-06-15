package com.pouffydev.krystal_core.content.item.exploration;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ComplexItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.saveddata.maps.MapDecorationType;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

public class CustomEmptyMap extends ComplexItem {

    private final TagKey<Structure> structureTag;
    private final Holder<MapDecorationType> decoration;
    private final ItemLike filledMap;
    private final int searchRadius;
    private final boolean skipExistingChunks;

    public CustomEmptyMap(Item.Properties properties, TagKey<Structure> structureTag, Holder<MapDecorationType> decoration, ItemLike filledMap, int searchRadius, boolean skipExistingChunks) {
        super(properties);
        this.structureTag = structureTag;
        this.decoration = decoration;
        this.filledMap = filledMap;
        this.searchRadius = searchRadius;
        this.skipExistingChunks = skipExistingChunks;
    }

    public CustomEmptyMap(Properties properties, TagKey<Structure> structureTag, Holder<MapDecorationType> decoration, ItemLike filledMap) {
        this(properties, structureTag, decoration, filledMap, 50, true);
    }

    public SoundEvent useSound() {
        return SoundEvents.UI_CARTOGRAPHY_TABLE_TAKE_RESULT;
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
                    ItemStack stack = CustomFilledMap.create(filledMap, level, blockpos.getX(), blockpos.getZ(), (byte) 2, true, true);
                    CustomFilledMap.renderBiomePreviewMap(serverLevel, stack);
                    MapItemSavedData.addTargetDecoration(stack, blockpos, "+", this.decoration);
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
