package com.pouffydev.krystal_core.foundation.data;

import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateItemModelProvider;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

public class AssetLookup {
    public static String name(NonNullSupplier<? extends ItemLike> item) {
        return item.get().asItem().getRegistryName().getPath();
    }
    public static <T extends Item> NonNullBiConsumer<DataGenContext<Item, T>, RegistrateItemModelProvider> compatItem(String type, String material) {
        return (c, p) -> p.withExistingParent(c.getName(), "item/generated").texture("layer0", p.modLoc("item/" + type + "/" + material));
    }
}
