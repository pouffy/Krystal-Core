package com.pouffydev.krystal_core.content;

import com.pouffydev.krystal_core.KrystalCore;
import com.pouffydev.krystal_core.foundation.registry.item.ItemHolderSet;
import com.pouffydev.krystal_core.foundation.registry.item.ItemRegistryHelper;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;

public class KrystalDebugItems {
    public static final ItemRegistryHelper HELPER = KrystalCore.getRegistryHelper().getItemHelper();

    public static final ItemHolderSet<Item, DyeColor> DYED_COINS = HELPER.registerColorSet("coin", (dyeColor, props) -> new Item(props), (props) -> new Item.Properties());

    public static void staticInit() {}
}
