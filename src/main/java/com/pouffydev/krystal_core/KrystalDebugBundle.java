package com.pouffydev.krystal_core;

import com.pouffydev.krystal_core.content.KrystalDebugItems;
import com.pouffydev.krystal_core.foundation.bundle.Bundle;
import com.pouffydev.krystal_core.foundation.bundle.BundleManager;
import com.pouffydev.krystal_core.foundation.bundle.runtime.AbstractBundleRecipeHandler;
import com.pouffydev.krystal_core.foundation.dynamicpack.data.recipe.custom.farmersdelight.CuttingBoardRecipe;
import com.pouffydev.krystal_core.foundation.dynamicpack.data.recipe.output.CustomRecipeOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class KrystalDebugBundle extends Bundle {
    public KrystalDebugBundle(BundleManager manager) {
        super(manager);
    }

    @Override
    public List<String> getRequiredClasses() {
        return List.of();
    }

    @Override
    public String getName() {
        return "debug";
    }

    @Override
    protected void onLoad() {
        KrystalCore.LOGGER.info("Loaded debug bundle. If this is not a development environment, please report this to the issue tracker");
        KrystalDebugItems.staticInit();
    }

    @Override
    public void runDatagen(GatherDataEvent event) {

    }

    @Override
    public void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES)
            KrystalDebugItems.DYED_COINS.forEach((c, i) -> event.accept(i.get()));
    }

    @Override
    public AbstractBundleRecipeHandler getRecipeHandler() {
        return new AbstractBundleRecipeHandler() {
            @Override
            public void run(@NotNull RecipeOutput output) {
                ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, Items.GLOWSTONE).requires(Items.GRASS_BLOCK).unlockedBy("has_grass", has(Items.GRASS_BLOCK)).save(output, KrystalCore.location("debug_shapeless"));
            }

            @Override
            public void runCustom(@NotNull CustomRecipeOutput output) {
                output.accept(KrystalCore.location("debug_cutting"), CuttingBoardRecipe.cuttingRecipe(Ingredient.of(Items.STONE), Ingredient.of(ItemTags.PICKAXES), Items.COBBLED_DEEPSLATE), null);
            }
        };
    }
}
