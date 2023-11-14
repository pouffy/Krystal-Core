package com.pouffydev.krystal_core.helpers;

import com.pouffydev.krystal_core.KrystalCore;
import com.pouffydev.krystal_core.foundation.KrystalCoreRegistrate;
import com.pouffydev.krystal_core.foundation.KrystalCoreTagGen;
import com.pouffydev.krystal_core.foundation.KrystalCoreTags;
import com.pouffydev.krystal_core.foundation.MaterialType;
import com.pouffydev.krystal_core.foundation.data.lang.KrystalCoreLang;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.providers.loot.RegistrateBlockLootTables;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.core.BlockPos;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraftforge.common.Tags;

import static com.pouffydev.krystal_core.foundation.KrystalCoreTagGen.*;
import static com.pouffydev.krystal_core.helpers.TagHelpers.*;


/**
 * A class containing helper methods for registering blocks
 */
public class BlockRegistryHelpers {
    private static final KrystalCoreRegistrate blockRegistrate = KrystalCore.registrate();
    
    private static TagKey<Block> harvestLevel(int harvestLevel) {
        return switch (harvestLevel) {
            case 1 -> BlockTags.NEEDS_IRON_TOOL;
            case 2 -> BlockTags.NEEDS_DIAMOND_TOOL;
            default -> BlockTags.NEEDS_STONE_TOOL;
        };
    }
    
    private static String oresInGround(boolean deepslate) {
        return deepslate ? "ores_in_ground/stone" : "ores_in_ground/deepslate";
    }
    
    
    /**
     * Gets the material type used for the given material
     * <p>
     * @return the TagKey for either ingots or gems
     */
    private static TagKey<Item> materialType(MaterialType materialType, String material) {
        if (materialType == MaterialType.metal) {
            return ingots(material);
        } else {
            return gems(material);
        }
    }
    
    
    
    /**
     * Creates a block entry for a block with the given material and lang
     * <p>
     * @param harvestLevel The harvest level of the block (1 = iron, 2 = diamond, default = stone)
     * @param material The material to use
     * @param lang The lang entry to use if the Ore should use a different name than the material
     * @param copyPropertiesFrom The block to copy properties from
     * @param soundType The sound type to use
     * @param materialColor The material color to use
     * @param drop The item to use in the loot table
     * @param deepslate Whether the ore should be deepslate or stone
     * <p>
     * @return The block entry
     * <p>
     */
    public static BlockEntry<Block> ore(String material, String lang, Block copyPropertiesFrom, SoundType soundType, MaterialColor materialColor, int harvestLevel, float hardness, Item drop, boolean deepslate) {
        return blockRegistrate.block(material + "_ore", Block::new)
                .initialProperties(() -> copyPropertiesFrom)
                .properties(p -> p.color(materialColor).strength(hardness))
                .properties(p -> p.requiresCorrectToolForDrops().sound(soundType))
                .transform(pickaxeOnly())
                .blockstate((ctx, prov) -> prov.cubeAll(ctx.getEntry()))
                .loot((lt, b) -> lt.add(b,
                        RegistrateBlockLootTables.createSilkTouchDispatchTable(b,
                                RegistrateBlockLootTables.applyExplosionDecay(b, LootItem.lootTableItem(drop)
                                        .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))))))
                .tag(harvestLevel(harvestLevel))
                .tag(Tags.Blocks.ORES)
                .transform(tagBlockAndItem("ores/" + material, oresInGround(deepslate)))
                .tag(Tags.Items.ORES)
                .build()
                .simpleItem()
                .lang(lang)
                .register();
    }
    
    public static BlockEntry<Block> storageBlock(String material, String lang, Block copyPropertiesFrom, SoundType soundType, MaterialColor materialColor, int harvestLevel, float hardness, MaterialType materialType) {
        return blockRegistrate.block(material + "_block", Block::new)
                .initialProperties(() -> copyPropertiesFrom)
                .properties(p -> p.color(materialColor).strength(hardness))
                .properties(p -> p.requiresCorrectToolForDrops().sound(soundType))
                .transform(pickaxeOnly())
                .tag(harvestLevel(harvestLevel))
                .tag(Tags.Blocks.STORAGE_BLOCKS)
                .tag(BlockTags.BEACON_BASE_BLOCKS)
                .blockstate((ctx, prov) -> prov.cubeAll(ctx.getEntry()))
                .recipe((ctx, prov) -> ShapedRecipeBuilder.shaped(ctx.getEntry(), 1)
                        .define('T', materialType(materialType, material))
                        .pattern("TTT")
                        .pattern("TTT")
                        .pattern("TTT")
                        .unlockedBy("has_item", RegistrateRecipeProvider.has(ctx.get()))
                        .save(prov))
                .transform(tagBlockAndItem("storage_blocks/" + material))
                .tag(Tags.Items.STORAGE_BLOCKS)
                .build()
                .lang(lang)
                .register();
    }
    
    
    private static final Material leaves = new Material.Builder(MaterialColor.PLANT).flammable().notSolidBlocking().destroyOnPush().build();
    private static final Material wood = new Material.Builder(MaterialColor.WOOD).flammable().build();
    private static final Material netherWood = new Material.Builder(MaterialColor.WOOD).build();
    private static Boolean ocelotOrParrot(BlockState state, BlockGetter getter, BlockPos pos, EntityType<?> entityType) {
        return entityType == EntityType.OCELOT || entityType == EntityType.PARROT;
    }
    private static Boolean never(BlockState state, BlockGetter getter, BlockPos pos) {
        return false;
    }
    
    private static Boolean always(BlockState state, BlockGetter getter, BlockPos pos) {
        return true;
    }
    public static BlockEntry<LeavesBlock> leaf(String material, String lang, SoundType soundType, MaterialColor materialColor, int harvestLevel, float hardness) {
        return blockRegistrate.block(material + "_leaves", LeavesBlock::new)
                .properties(p -> p.color(materialColor).sound(soundType).strength(hardness))
                .properties(p -> BlockBehaviour.Properties.of(leaves).randomTicks().noOcclusion().isValidSpawn(BlockRegistryHelpers::ocelotOrParrot).isSuffocating(BlockRegistryHelpers::never).isViewBlocking(BlockRegistryHelpers::never))
                .transform(hoeOnly())
                .tag(harvestLevel(harvestLevel))
                .tag(leaves())
                .blockstate((ctx, prov) -> prov.cubeAll(ctx.getEntry()))
                .transform(tagBlockAndItem("leaves/" + material))
                .tag(leavesI())
                .build()
                .lang(lang)
                .register();
    }
    public static BlockEntry<RotatedPillarBlock> log(String material, String lang, SoundType soundType, MaterialColor materialColor, int harvestLevel, float hardness) {
        return blockRegistrate.block(material + "_log", RotatedPillarBlock::new)
                .properties(p -> p.color(materialColor).sound(soundType).strength(hardness))
                .properties(p -> BlockBehaviour.Properties.of(wood))
                .transform(axeOnly())
                .tag(harvestLevel(harvestLevel))
                .tag(logs())
                .blockstate((ctx, prov) -> prov.logBlock(ctx.getEntry()))
                .transform(tagBlockAndItem("logs/" + material))
                .tag(logsI())
                .build()
                .lang(lang)
                .register();
    }
    public static BlockEntry<RotatedPillarBlock> netherStem(String material, String lang, SoundType soundType, MaterialColor materialColor, int harvestLevel, float hardness) {
        return blockRegistrate.block(material + "_stem", RotatedPillarBlock::new)
                .properties(p -> p.color(materialColor).sound(soundType).strength(hardness))
                .properties(p -> BlockBehaviour.Properties.of(netherWood))
                .transform(axeOnly())
                .tag(harvestLevel(harvestLevel))
                .tag(logs())
                .blockstate((ctx, prov) -> prov.logBlock(ctx.getEntry()))
                .transform(tagBlockAndItem("logs/" + material))
                .tag(logsI())
                .build()
                .lang(lang)
                .register();
    }
    
    public static BlockEntry<RotatedPillarBlock> wood(String material, String lang, SoundType soundType, MaterialColor materialColor, int harvestLevel, float hardness) {
        return blockRegistrate.block(material + "_wood", RotatedPillarBlock::new)
                .properties(p -> p.color(materialColor).sound(soundType).strength(hardness))
                .properties(p -> BlockBehaviour.Properties.of(wood))
                .transform(axeOnly())
                .tag(harvestLevel(harvestLevel))
                .tag(logs())
                .blockstate((ctx, prov) -> prov.axisBlock(ctx.getEntry(), new ResourceLocation(ctx.getId().getNamespace(), material + "_log_side"), new ResourceLocation(ctx.getId().getNamespace(), material + "_log_side")))
                
                .transform(tagBlockAndItem("logs/" + material))
                .tag(logsI())
                .build()
                .lang(lang)
                .register();
    }
    
    public static BlockEntry<RotatedPillarBlock> strippedWood(String material, String lang, SoundType soundType, MaterialColor materialColor, int harvestLevel, float hardness) {
        return blockRegistrate.block("stripped_" + material + "_wood", RotatedPillarBlock::new)
                .properties(p -> p.color(materialColor).sound(soundType).strength(hardness))
                .properties(p -> BlockBehaviour.Properties.of(wood))
                .transform(axeOnly())
                .tag(harvestLevel(harvestLevel))
                .tag(strippedLogs())
                .blockstate((ctx, prov) -> prov.axisBlock(ctx.getEntry(), new ResourceLocation(ctx.getId().getNamespace(), "stripped_" + material + "_log_side"), new ResourceLocation(ctx.getId().getNamespace(), "stripped_" + material + "_log_side")))
                .transform(tagBlockAndItem("stripped_logs/" + material))
                .tag(strippedLogsI())
                .build()
                .lang(lang)
                .register();
    }
    
    public static BlockEntry<RotatedPillarBlock> strippedNetherStem(String material, String lang, SoundType soundType, MaterialColor materialColor, int harvestLevel, float hardness) {
        return blockRegistrate.block("stripped_" + material + "_stem", RotatedPillarBlock::new)
                .properties(p -> p.color(materialColor).sound(soundType).strength(hardness))
                .properties(p -> BlockBehaviour.Properties.of(netherWood))
                .transform(axeOnly())
                .tag(harvestLevel(harvestLevel))
                .tag(strippedLogs())
                .blockstate((ctx, prov) -> prov.logBlock(ctx.getEntry()))
                .transform(tagBlockAndItem("stripped_logs/" + material))
                .tag(strippedLogsI())
                .build()
                .lang(lang)
                .register();
    }
    
    public static BlockEntry<RotatedPillarBlock> strippedLog(String material, String lang, SoundType soundType, MaterialColor materialColor, int harvestLevel, float hardness) {
        return blockRegistrate.block("stripped_" + material + "_log", RotatedPillarBlock::new)
                .properties(p -> p.color(materialColor).sound(soundType).strength(hardness))
                .properties(p -> BlockBehaviour.Properties.of(wood))
                .transform(axeOnly())
                .tag(harvestLevel(harvestLevel))
                .tag(strippedLogs())
                .blockstate((ctx, prov) -> prov.logBlock(ctx.getEntry()))
                .transform(tagBlockAndItem("stripped_logs/" + material))
                .tag(strippedLogsI())
                .build()
                .lang(lang)
                .register();
    }
    
}
