package com.pouffydev.krystal_core.foundation;

import com.pouffydev.krystal_core.KrystalCore;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import net.minecraft.client.renderer.entity.FoxRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Collections;

public class KrystalCoreTags {
    public static <T extends IForgeRegistryEntry<T>> TagKey<T> optionalTag(IForgeRegistry<T> registry, ResourceLocation id) {
        return registry.tags().createOptionalTagKey(id, Collections.emptySet());
    }
    public static <T extends IForgeRegistryEntry<T>> TagKey<T> minecraftTag(IForgeRegistry<T> registry, String path) {
        return optionalTag(registry, new ResourceLocation("minecraft", path));
    }
    public static <T extends IForgeRegistryEntry<T>> TagKey<T> modTag(IForgeRegistry<T> registry, String modID, String path) {
        return optionalTag(registry, new ResourceLocation(modID, path));
    }
    public static TagKey<Item> modItemTag(String modID, String path) {
        return modTag(ForgeRegistries.ITEMS, modID, path);
    }
    public static <T extends IForgeRegistryEntry<T>> TagKey<T> forgeTag(IForgeRegistry<T> registry, String path) {
        return optionalTag(registry, new ResourceLocation("forge", path));
    }
    
    public static TagKey<Block> forgeBlockTag(String path) {
        return forgeTag(ForgeRegistries.BLOCKS, path);
    }
    
    
    public static TagKey<Item> forgeItemTag(String path) {
        return forgeTag(ForgeRegistries.ITEMS, path);
    }
    
    public static TagKey<Fluid> forgeFluidTag(String path) {
        return forgeTag(ForgeRegistries.FLUIDS, path);
    }
    
    @Deprecated(forRemoval = true)
    public static <T extends Block, P> NonNullFunction<BlockBuilder<T, P>, BlockBuilder<T, P>> axeOrPickaxe() {
        return KrystalCoreTagGen.axeOrPickaxe();
    }
    
    @Deprecated(forRemoval = true)
    public static <T extends Block, P> NonNullFunction<BlockBuilder<T, P>, BlockBuilder<T, P>> axeOnly() {
        return KrystalCoreTagGen.axeOnly();
    }
    
    @Deprecated(forRemoval = true)
    public static <T extends Block, P> NonNullFunction<BlockBuilder<T, P>, BlockBuilder<T, P>> pickaxeOnly() {
        return KrystalCoreTagGen.pickaxeOnly();
    }
    
    @Deprecated(forRemoval = true)
    public static <T extends Block, P> NonNullFunction<BlockBuilder<T, P>, ItemBuilder<BlockItem, BlockBuilder<T, P>>> tagBlockAndItem(
            String... path) {
        return KrystalCoreTagGen.tagBlockAndItem(path);
    }
    public enum NameSpace {
        MOD(KrystalCore.ID, false, true),
        FORGE("forge"),
        TIC("tconstruct"),
        CREATE(KrystalCoreMods.create.getId())
        
        ;
        
        public final String id;
        public final boolean optionalDefault;
        public final boolean alwaysDatagenDefault;
        
        NameSpace(String id) {
            this(id, true, false);
        }
        
        NameSpace(String id, boolean optionalDefault, boolean alwaysDatagenDefault) {
            this.id = id;
            this.optionalDefault = optionalDefault;
            this.alwaysDatagenDefault = alwaysDatagenDefault;
        }
    }
}
