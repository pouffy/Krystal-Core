package com.pouffydev.krystal_core.foundation.mixin.bundle;

import com.pouffydev.krystal_core.foundation.bundle.runtime.BundledRecipes;
import com.pouffydev.krystal_core.foundation.dynamicpack.data.BundleDynamicDataPack;
import com.pouffydev.krystal_core.foundation.dynamicpack.data.recipe.custom.CustomRecipe;
import com.pouffydev.krystal_core.foundation.dynamicpack.data.recipe.output.CustomRecipeOutput;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.commands.Commands;
import net.minecraft.core.LayeredRegistryAccess;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.RegistryLayer;
import net.minecraft.server.ReloadableServerResources;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.crafting.Recipe;
import net.neoforged.neoforge.common.conditions.ICondition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Mixin(value = ReloadableServerResources.class, priority = 2000)
public abstract class ReloadableServerResourcesMixin {

    @Inject(method = "loadResources", at = @At("HEAD"))
    private static void init(ResourceManager resourceManager, LayeredRegistryAccess<RegistryLayer> access,
                                    FeatureFlagSet featureFlags, Commands.CommandSelection commands,
                                    int functionCompilationLevel, Executor backgroundExecutor, Executor gameExecutor,
                                    CallbackInfoReturnable<CompletableFuture<ReloadableServerResources>> cir) {
        RegistryAccess.Frozen frozen = access.compositeAccess();

        BundledRecipes.recipeAddition(new RecipeOutput() {

            @Override
            public Advancement.@NotNull Builder advancement() {
                // noinspection removal
                return Advancement.Builder.recipeAdvancement().parent(RecipeBuilder.ROOT_RECIPE_ADVANCEMENT);
            }

            @Override
            public void accept(@NotNull ResourceLocation id, @NotNull Recipe<?> recipe, @Nullable AdvancementHolder advancement, ICondition @NotNull... conditions) {
                BundleDynamicDataPack.addRecipe(id, recipe, advancement, frozen);
            }
        });
        BundledRecipes.customDataAddition(new CustomRecipeOutput() {

            @Override
            public Advancement.Builder advancement() {
                // noinspection removal
                return Advancement.Builder.recipeAdvancement().parent(RecipeBuilder.ROOT_RECIPE_ADVANCEMENT);
            }

            @Override
            public void accept(ResourceLocation id, CustomRecipe<?> recipe, @Nullable AdvancementHolder advancement, ICondition... conditions) {
                BundleDynamicDataPack.addRecipe(id, recipe, advancement, frozen);
            }
        });
    }
}
