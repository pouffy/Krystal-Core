package com.pouffydev.krystal_core.helpers;

import com.pouffydev.krystal_core.KrystalCore;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateItemModelProvider;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class KrystalCoreAssetLookup {
    static ResourceLocation count = new ResourceLocation(KrystalCore.ID, "count");
    public static <T extends Item> NonNullBiConsumer<DataGenContext<Item, T>, RegistrateItemModelProvider> countedItem() {
        return (c, p) -> p.withExistingParent(c.getName(), "item/generated").texture("layer0", p.modLoc("item/" + c.getName() + "_count4"))
                .override()
                .predicate(count, 0.00000f).model(p.withExistingParent(c.getName() + "_0", "item/generated").texture("layer0", p.modLoc("item/" + c.getName() + "_count0"))).end()
                .override()
                .predicate(count, 0.03125f).model(p.withExistingParent(c.getName() + "_1", "item/generated").texture("layer0", p.modLoc("item/" + c.getName() + "_count1"))).end()
                .override()
                .predicate(count, 0.25000f).model(p.withExistingParent(c.getName() + "_2", "item/generated").texture("layer0", p.modLoc("item/" + c.getName() + "_count2"))).end()
                .override()
                .predicate(count, 0.50000f).model(p.withExistingParent(c.getName() + "_3", "item/generated").texture("layer0", p.modLoc("item/" + c.getName() + "_count3"))).end()
                .override()
                .predicate(count, 1.00000f).model(p.withExistingParent(c.getName() + "_4", "item/generated").texture("layer0", p.modLoc("item/" + c.getName() + "_count4"))).end();
    }
}
