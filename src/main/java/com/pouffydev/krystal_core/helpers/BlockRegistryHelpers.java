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
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraftforge.common.Tags;

import static com.pouffydev.krystal_core.foundation.KrystalCoreTagGen.pickaxeOnly;
import static com.pouffydev.krystal_core.foundation.KrystalCoreTagGen.tagBlockAndItem;
import static com.pouffydev.krystal_core.helpers.TagHelpers.gems;
import static com.pouffydev.krystal_core.helpers.TagHelpers.ingots;


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
    public static BlockEntry<Block> ore(String material, String lang, Block copyPropertiesFrom, SoundType soundType, MaterialColor materialColor, int harvestLevel, Item drop, boolean deepslate) {
        return blockRegistrate.block(material + "_ore", Block::new)
                .initialProperties(() -> copyPropertiesFrom)
                .properties(p -> p.color(materialColor))
                .properties(p -> p.requiresCorrectToolForDrops().sound(soundType))
                .transform(pickaxeOnly())
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
    
    public static BlockEntry<Block> storageBlock(String material, String lang, Block copyPropertiesFrom, SoundType soundType, MaterialColor materialColor, int harvestLevel, MaterialType materialType) {
        return blockRegistrate.block(material + "_block", Block::new)
                .initialProperties(() -> copyPropertiesFrom)
                .properties(p -> p.color(materialColor))
                .properties(p -> p.requiresCorrectToolForDrops().sound(soundType))
                .transform(pickaxeOnly())
                .tag(harvestLevel(harvestLevel))
                .tag(Tags.Blocks.STORAGE_BLOCKS)
                .tag(BlockTags.BEACON_BASE_BLOCKS)
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
    
    
}
