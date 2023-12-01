package com.pouffydev.krystal_core.foundation.data;

import com.pouffydev.krystal_core.KrystalCore;
import com.pouffydev.krystal_core.foundation.KrystalCoreTags;
import com.pouffydev.krystal_core.init.KCTags;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

public class KCRegistrateTags {
    public static void addGenerators() {
        KrystalCore.registrate.addDataGenerator(ProviderType.ITEM_TAGS, KCRegistrateTags::genItemTags);
    }
    private static void genItemTags(RegistrateTagsProvider<Item> prov) {
        prov.tag(KCTags.AllItemTags.AMETHYST_ITEM.tag)
                .addTag(KrystalCoreTags.forgeItemTag("gems/amethyst"))
                .add(Items.AMETHYST_BLOCK, Items.AMETHYST_CLUSTER, Items.AMETHYST_SHARD, Items.BUDDING_AMETHYST, Items.LARGE_AMETHYST_BUD, Items.MEDIUM_AMETHYST_BUD, Items.SMALL_AMETHYST_BUD);
    }
}
