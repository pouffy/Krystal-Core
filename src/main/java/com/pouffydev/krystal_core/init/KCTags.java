package com.pouffydev.krystal_core.init;

import com.pouffydev.krystal_core.foundation.KrystalCoreTags;
import com.pouffydev.krystal_core.foundation.data.lang.KrystalCoreLang;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import static com.pouffydev.krystal_core.foundation.KrystalCoreTags.NameSpace.MOD;
import static com.pouffydev.krystal_core.foundation.KrystalCoreTags.optionalTag;

public class KCTags {
    
    public enum AllItemTags {
        AMETHYST_ITEM(MOD, "amethyst_item"),
        ;
        public final TagKey<Item> tag;
        public final boolean alwaysDatagen;
        
        AllItemTags() {
            this(MOD);
        }
        
        AllItemTags(KrystalCoreTags.NameSpace namespace) {
            this(namespace, namespace.optionalDefault, namespace.alwaysDatagenDefault);
        }
        
        AllItemTags(KrystalCoreTags.NameSpace namespace, String path) {
            this(namespace, path, namespace.optionalDefault, namespace.alwaysDatagenDefault);
        }
        
        AllItemTags(KrystalCoreTags.NameSpace namespace, boolean optional, boolean alwaysDatagen) {
            this(namespace, null, optional, alwaysDatagen);
        }
        
        AllItemTags(KrystalCoreTags.NameSpace namespace, String path, boolean optional, boolean alwaysDatagen) {
            ResourceLocation id = new ResourceLocation(namespace.id, path == null ? KrystalCoreLang.asId(name()) : path);
            if (optional) {
                tag = optionalTag(ForgeRegistries.ITEMS, id);
            } else {
                tag = ItemTags.create(id);
            }
            this.alwaysDatagen = alwaysDatagen;
        }
        
        @SuppressWarnings("deprecation")
        public boolean matches(Item item) {
            return item.builtInRegistryHolder()
                    .is(tag);
        }
        
        public boolean matches(ItemStack stack) {
            return stack.is(tag);
        }
        
        private static void init() {
        }
    }
}
