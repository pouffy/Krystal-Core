package com.pouffydev.krystal_core.content;

import com.pouffydev.krystal_core.KrystalCore;
import com.pouffydev.krystal_core.foundation.registry.definition.item.ItemDefinitionSet;
import com.pouffydev.krystal_core.foundation.registry.definition.item.ItemRegistryHelper;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;

public class KrystalDebugItems {
    public static final ItemRegistryHelper HELPER = KrystalCore.getRegistryHelper().getItemHelper();

    public static final ItemDefinitionSet<Item, DyeColor> DYED_COINS = HELPER.registerColorSet("coin", (dyeColor, props) -> new Item(props), (props) -> new Item.Properties());

    public static void staticInit() {}
}
