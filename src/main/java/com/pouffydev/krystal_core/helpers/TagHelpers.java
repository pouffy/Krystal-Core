package com.pouffydev.krystal_core.helpers;

import com.pouffydev.krystal_core.foundation.KrystalCoreTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

/**
 * Pre-Built TagKey's for my items for use in TagGen
 * @see TagKey
 */
public class TagHelpers {
    
    public static TagKey<Item> gems(String material) {
        return KrystalCoreTags.forgeItemTag("gems/" + material);
    }
    public static TagKey<Item> rawMaterials(String material) {
        return KrystalCoreTags.forgeItemTag("raw_materials/" + material);
    }
    public static TagKey<Item> rods(String material) {
        return KrystalCoreTags.forgeItemTag("rods/" + material);
    }
    public static TagKey<Item> rods() {
        return KrystalCoreTags.forgeItemTag("rods");
    }
    public static TagKey<Item> ingots(String material) {
        return KrystalCoreTags.forgeItemTag("ingots/" + material);
    }
    public static TagKey<Item> dusts(String material) {
        return KrystalCoreTags.forgeItemTag("dusts/" + material);
    }
    public static TagKey<Item> gears(String material) {
        return KrystalCoreTags.forgeItemTag("gears/" + material);
    }
    public static TagKey<Item> nuggets(String material) {
        return KrystalCoreTags.forgeItemTag("nuggets/" + material);
    }
    public static TagKey<Item> plates(String material) {
        return KrystalCoreTags.forgeItemTag("plates/" + material);
    }
    public static TagKey<Item> plates() {
        return KrystalCoreTags.forgeItemTag("plates");
    }
    public static TagKey<Item> sturdyPlates(String material) {
        return KrystalCoreTags.forgeItemTag("plates/sturdy/" + material);
    }
    public static TagKey<Item> sturdyPlates() {
        return KrystalCoreTags.forgeItemTag("plates/sturdy");
    }
    public static TagKey<Item> reinforcedPlates(String material) {
        return KrystalCoreTags.forgeItemTag("plates/reinforced/" + material);
    }
    public static TagKey<Item> reinforcedPlates() {
        return KrystalCoreTags.forgeItemTag("plates/reinforced");
    }
    public static TagKey<Item> rawMaterials() {
        return KrystalCoreTags.forgeItemTag("raw_materials");
    }
    public static TagKey<Item> ingots() {
        return KrystalCoreTags.forgeItemTag("ingots");
    }
    public static TagKey<Item> dusts() {
        return KrystalCoreTags.forgeItemTag("dusts");
    }
    public static TagKey<Item> gears() {
        return KrystalCoreTags.forgeItemTag("gears");
    }
    public static TagKey<Item> randomiumBlacklist() {
        return KrystalCoreTags.modItemTag("randomium", "blacklist");
    }
    public static TagKey<Item> nuggets() {
        return KrystalCoreTags.forgeItemTag("nuggets");
    }
    public static TagKey<Item> gems() {
        return KrystalCoreTags.forgeItemTag("gems");
    }
    public static TagKey<Item> coins() {
        return KrystalCoreTags.forgeItemTag("coins");
    }
    public static TagKey<Item> coins(String material) {
        return KrystalCoreTags.forgeItemTag("coins/" + material);
    }
    public static TagKey<Item> sturdySheets(String material) {
        return KrystalCoreTags.forgeItemTag("plates/sturdy/" + material);
    }
    public static TagKey<Item> sturdySheets() {
        return KrystalCoreTags.forgeItemTag("plates/sturdy");
    }
    public static TagKey<Item> reinforcedSheets(String material) {
        return KrystalCoreTags.forgeItemTag("plates/reinforced/" + material);
    }
    public static TagKey<Item> reinforcedSheets() {
        return KrystalCoreTags.forgeItemTag("plates/reinforced");
    }
    public static TagKey<Block> logs() {
        return KrystalCoreTags.forgeBlockTag("logs");
    }
    public static TagKey<Block> logs(String material) {
        return KrystalCoreTags.forgeBlockTag("logs/" + material);
    }
    public static TagKey<Block> strippedLogs() {
        return KrystalCoreTags.forgeBlockTag("stripped_logs");
    }
    public static TagKey<Block> strippedLogs(String material) {
        return KrystalCoreTags.forgeBlockTag("stripped_logs/" + material);
    }
    public static TagKey<Block> planks() {
        return KrystalCoreTags.forgeBlockTag("planks");
    }
    public static TagKey<Block> planks(String material) {
        return KrystalCoreTags.forgeBlockTag("planks/" + material);
    }
    public static TagKey<Block> leaves() {
        return KrystalCoreTags.forgeBlockTag("leaves");
    }
    public static TagKey<Block> leaves(String material) {
        return KrystalCoreTags.forgeBlockTag("leaves/" + material);
    }
    
    public static TagKey<Item> logsI() {
        return KrystalCoreTags.forgeItemTag("logs");
    }
    public static TagKey<Item> logsI(String material) {
        return KrystalCoreTags.forgeItemTag("logs/" + material);
    }
    public static TagKey<Item> strippedLogsI() {
        return KrystalCoreTags.forgeItemTag("stripped_logs");
    }
    public static TagKey<Item> strippedLogsI(String material) {
        return KrystalCoreTags.forgeItemTag("stripped_logs/" + material);
    }
    public static TagKey<Item> planksI() {
        return KrystalCoreTags.forgeItemTag("planks");
    }
    public static TagKey<Item> planksI(String material) {
        return KrystalCoreTags.forgeItemTag("planks/" + material);
    }
    public static TagKey<Item> leavesI() {
        return KrystalCoreTags.forgeItemTag("leaves");
    }
    public static TagKey<Item> leavesI(String material) {
        return KrystalCoreTags.forgeItemTag("leaves/" + material);
    }
    
    
    
}
