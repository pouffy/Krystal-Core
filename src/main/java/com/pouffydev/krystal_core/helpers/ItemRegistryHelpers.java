package com.pouffydev.krystal_core.helpers;

import com.pouffydev.krystal_core.KrystalCore;
import com.pouffydev.krystal_core.content.TagDependentCompatItem;
import com.pouffydev.krystal_core.foundation.KrystalCoreRegistrate;
import com.pouffydev.krystal_core.foundation.KrystalCoreTags;
import com.pouffydev.krystal_core.foundation.data.AssetLookup;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.*;

import static com.pouffydev.krystal_core.helpers.TagHelpers.*;

/**
 * A class containing helper methods for registering items
 */
public class ItemRegistryHelpers {
    private static final KrystalCoreRegistrate itemRegistrate = KrystalCore.registrate();
    public static ItemEntry<SwordItem> sword(String material, Tier tier, int attackDamageModifier, float attackSpeedModifier) {
        return itemRegistrate.item(material + "_sword", p -> new SwordItem(tier, attackDamageModifier, attackSpeedModifier, p))
                .properties(p->p)
                .model((ctx, p) -> p.withExistingParent(ctx.getName(), "item/handheld").texture("layer0", "item/" + material + "_sword"))
                .register();
    }
    public static ItemEntry<PickaxeItem> pickaxe(String material, Tier tier, int attackDamageModifier, float attackSpeedModifier) {
        return itemRegistrate.item(material + "_pickaxe", p -> new PickaxeItem(tier, attackDamageModifier, attackSpeedModifier, p))
                .properties(p->p)
                .model((ctx, p) -> p.withExistingParent(ctx.getName(), "item/handheld").texture("layer0", "item/" + material + "_pickaxe"))
                .register();
    }
    public static ItemEntry<AxeItem> axe(String material, Tier tier, float attackDamageModifier, float attackSpeedModifier) {
        return itemRegistrate.item(material + "_axe", p -> new AxeItem(tier, attackDamageModifier, attackSpeedModifier, p))
                .properties(p->p)
                .model((ctx, p) -> p.withExistingParent(ctx.getName(), "item/handheld").texture("layer0", "item/" + material + "_axe"))
                .register();
    }
    public static ItemEntry<ShovelItem> shovel(String material, Tier tier, float attackDamageModifier, float attackSpeedModifier) {
        return itemRegistrate.item(material + "_shovel", p -> new ShovelItem(tier, attackDamageModifier, attackSpeedModifier, p))
                .properties(p->p)
                .model((ctx, p) -> p.withExistingParent(ctx.getName(), "item/handheld")
                        .texture("layer0", "item/" + material + "_shovel"))
                .register();
    }
    public static ItemEntry<HoeItem> hoe(String material, Tier tier, int attackDamageModifier, float attackSpeedModifier) {
        return itemRegistrate.item(material + "_hoe", p -> new HoeItem(tier, attackDamageModifier, attackSpeedModifier, p))
                .properties(p->p)
                .model((ctx, p) -> p.withExistingParent(ctx.getName(), "item/handheld").texture("layer0", "item/" + material + "_hoe"))
                .register();
    }
    public static ItemEntry<Item> sheet(String material) {
        return itemRegistrate.item(material + "_sheet", Item::new)
                .properties(p->p)
                .tag(plates(material))
                .tag(plates())
                .register();
    }
    public static ItemEntry<Item> ingot(String material) {
        return itemRegistrate.item(material + "_ingot", Item::new)
                .properties(p->p)
                .tag(ingots(material))
                .tag(ingots())
                .register();
    }
    public static ItemEntry<Item> nugget(String material) {
        return itemRegistrate.item(material + "_nugget", Item::new)
                .properties(p->p)
                .tag(nuggets(material))
                .tag(nuggets()).register();
    }
    public static ItemEntry<Item> rawOre(String material) {
        return itemRegistrate.item("raw_" + material, Item::new).properties(p->p)
                .tag(rawMaterials(material))
                .tag(rawMaterials())
                .register();
    }
    /**
        Kinda random, but Sturdy & Reinforced sheets are used in both Milkyway Core and Krystal's Material Compats, so I'm putting them here.
     */
    public static ItemEntry<Item> sturdySheet(String material) {
        return itemRegistrate.item("sturdy_" + material + "_sheet", Item::new)
                .properties(p->p)
                .tag(sturdySheets(material))
                .tag(sturdySheets())
                .register();
    }
    public static ItemEntry<Item> reinforcedSheet(String material) {
        return itemRegistrate.item("reinforced_" + material + "_sheet", Item::new)
                .properties(p->p)
                .tag(reinforcedSheets(material))
                .tag(reinforcedSheets())
                .register();
    }
    public static ItemEntry<Item> unprocessedSheet(String material) {
        return itemRegistrate.item("unprocessed_" + material + "_sheet", Item::new)
                .properties(p->p)
                .register();
    }
    public static ItemEntry<Item> reprocessedSheet(String material) {
        return itemRegistrate.item("reprocessed_" + material + "_sheet", Item::new)
                .properties(p->p).register();
    }
    public static ItemEntry<Item> countedItem(String name) {
        return itemRegistrate
                .item(name, Item::new)
                .model(KrystalCoreAssetLookup.countedItem())
                .register();
    }
    
    /**
     * Compat Items
     * <p>
     * These are items that are used in Krystal's Material Compats to make the mod compatible with other mods.
     * <p>
     * AssetLookup.compatItem() is used to create the item model for the compat item.
     * <p>
     * It takes two parameters: the type of item as a String, and the material.
     * <p>
     * It will search for the Item Texture in the assets folder under "item/[type]/[material]"
     * <p>
     * @param metal The material to use for the item
     * <p>
     * @return The item entry for the compat item
     * @see AssetLookup
     * @see TagDependentCompatItem
     */
    public static ItemEntry<TagDependentCompatItem> compatSheet(String metal) {
        return itemRegistrate
                .item(metal + "_sheet",
                        props -> new TagDependentCompatItem(props, ingots(metal)))
                .tag(plates(metal))
                .tag(plates())
                .model(AssetLookup.compatItem("sheet", metal))
                .register();
    }
    public static ItemEntry<TagDependentCompatItem> compatMetalRod(String metal) {
        return itemRegistrate
                .item(metal + "_rod",
                        props -> new TagDependentCompatItem(props, ingots(metal)))
                .tag(rods(metal))
                .tag(rods())
                .model(AssetLookup.compatItem("rod", metal))
                .register();
    }
    public static ItemEntry<TagDependentCompatItem> compatUnprocessedSheet(String metal) {
        return itemRegistrate
                .item("unprocessed_" + metal + "_sheet",
                        props -> new TagDependentCompatItem(props, ingots(metal)))
                .tag(sturdyPlates(metal))
                .tag(sturdyPlates())
                .model(AssetLookup.compatItem("unprocessed_sheet", metal))
                .register();
    }
    public static ItemEntry<TagDependentCompatItem> compatSturdySheet(String metal) {
        return itemRegistrate
                .item("sturdy_" + metal + "_sheet",
                        props -> new TagDependentCompatItem(props, ingots(metal)))
                .tag(sturdyPlates(metal))
                .tag(sturdyPlates())
                .model(AssetLookup.compatItem("sturdy_sheet", metal))
                .register();
    }
    public static ItemEntry<TagDependentCompatItem> compatReprocessedSheet(String metal) {
        return itemRegistrate
                .item("reprocessed_" + metal + "_sheet",
                        props -> new TagDependentCompatItem(props, ingots(metal)))
                .tag(sturdyPlates(metal))
                .tag(sturdyPlates())
                .model(AssetLookup.compatItem("reprocessed_sheet", metal))
                .register();
    }
    public static ItemEntry<TagDependentCompatItem> compatReinforcedSheet(String metal) {
        return itemRegistrate
                .item("reinforced_" + metal + "_sheet",
                        props -> new TagDependentCompatItem(props, ingots(metal)))
                .tag(reinforcedPlates(metal))
                .tag(reinforcedPlates())
                .model(AssetLookup.compatItem("reinforced_sheet", metal))
                .register();
    }
    public static ItemEntry<TagDependentCompatItem> compatDust(String metal) {
        return itemRegistrate
                .item(metal + "_dust",
                        props -> new TagDependentCompatItem(props, ingots(metal)))
                .tag(dusts(metal))
                .tag(dusts())
                .model(AssetLookup.compatItem("dust", metal))
                .register();
    }
    public static ItemEntry<TagDependentCompatItem> compatGear(String metal) {
        return itemRegistrate
                .item(metal + "_gear",
                        props -> new TagDependentCompatItem(props, ingots(metal)))
                .tag(gears(metal))
                .tag(gears())
                .model(AssetLookup.compatItem("gear", metal))
                .register();
    }
}
