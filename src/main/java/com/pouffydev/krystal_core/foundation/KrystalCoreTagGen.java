package com.pouffydev.krystal_core.foundation;

import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

public class KrystalCoreTagGen {
    public static <T extends Block, P> NonNullFunction<BlockBuilder<T, P>, BlockBuilder<T, P>> axeOrPickaxe() {
        return b -> b.tag(BlockTags.MINEABLE_WITH_AXE)
                .tag(BlockTags.MINEABLE_WITH_PICKAXE);
    }
    
    public static <T extends Block, P> NonNullFunction<BlockBuilder<T, P>, BlockBuilder<T, P>> axeOnly() {
        return b -> b.tag(BlockTags.MINEABLE_WITH_AXE);
    }
    
    public static <T extends Block, P> NonNullFunction<BlockBuilder<T, P>, BlockBuilder<T, P>> pickaxeOnly() {
        return b -> b.tag(BlockTags.MINEABLE_WITH_PICKAXE);
    }
    
    public static <T extends Block, P> NonNullFunction<BlockBuilder<T, P>, ItemBuilder<BlockItem, BlockBuilder<T, P>>> tagBlockAndItem(
            String... path) {
        return b -> {
            for (String p : path)
                b.tag(KrystalCoreTags.forgeBlockTag(p));
            ItemBuilder<BlockItem, BlockBuilder<T, P>> item = b.item();
            for (String p : path)
                item.tag(KrystalCoreTags.forgeItemTag(p));
            return item;
        };
    }
    
    public static <T extends TagsProvider.TagAppender<?>> T addOptional(T appender, KrystalCoreMods mod, String id) {
        appender.addOptional(mod.asResource(id));
        return appender;
    }
    
    public static <T extends TagsProvider.TagAppender<?>> T addOptional(T appender, KrystalCoreMods mod, String... ids) {
        for (String id : ids) {
            appender.addOptional(mod.asResource(id));
        }
        return appender;
    }
}
