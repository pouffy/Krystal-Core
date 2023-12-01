package com.pouffydev.krystal_core.init;

import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.Item;

import static com.pouffydev.krystal_core.helpers.ItemRegistryHelpers.countedItem;

public class KCDebugItems {
    public static final ItemEntry<Item> debugCountedItem = countedItem("counted_debug");
    
    public static void register() {
    }
}
