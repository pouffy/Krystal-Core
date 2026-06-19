package com.pouffydev.krystal_core.foundation.dynamicpack.data.recipe.custom.farmersdelight;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import com.pouffydev.krystal_core.foundation.data.ChanceResult;
import com.pouffydev.krystal_core.foundation.dynamicpack.data.recipe.custom.CustomRecipe;
import lombok.Getter;
import net.minecraft.core.NonNullList;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.ICondition;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class CuttingBoardRecipe extends CustomRecipe<CuttingBoardWrapper> {
    private final NonNullList<ChanceResult> results = NonNullList.createWithCapacity(4);
    private Ingredient ingredient;
    private Ingredient tool;
    private Optional<SoundEvent> soundEvent = Optional.empty();
    private String group = "";

    private final @Getter List<ICondition> conditions = new ArrayList<>();

    public CuttingBoardRecipe() {}

    public CuttingBoardRecipe(Ingredient ingredient, Ingredient tool, ItemLike mainResult, int count, float chance) {
        this.results.add(new ChanceResult(new ItemStack(mainResult.asItem(), count), chance));
        this.ingredient = ingredient;
        this.tool = tool;
    }

    public static CuttingBoardRecipe cuttingRecipe(Ingredient ingredient, Ingredient tool, ItemLike mainResult, int count) {
        return new CuttingBoardRecipe(ingredient, tool, mainResult, count, 1);
    }

    public static CuttingBoardRecipe cuttingRecipe(Ingredient ingredient, Ingredient tool, ItemLike mainResult, int count, float chance) {
        return new CuttingBoardRecipe(ingredient, tool, mainResult, count, chance);
    }

    public static CuttingBoardRecipe cuttingRecipe(Ingredient ingredient, Ingredient tool, ItemLike mainResult) {
        return new CuttingBoardRecipe(ingredient, tool, mainResult, 1, 1);
    }

    public CuttingBoardRecipe addResult(ItemLike result) {
        return this.addResult(result, 1);
    }

    public CuttingBoardRecipe addResult(ItemLike result, int count) {
        this.results.add(new ChanceResult(new ItemStack(result.asItem(), count), 1));
        return this;
    }

    public CuttingBoardRecipe addResultWithChance(ItemLike result, float chance) {
        return this.addResultWithChance(result, chance, 1);
    }

    public CuttingBoardRecipe addResultWithChance(ItemLike result, float chance, int count) {
        this.results.add(new ChanceResult(new ItemStack(result.asItem(), count), chance));
        return this;
    }

    public CuttingBoardRecipe addSound(SoundEvent soundEvent) {
        this.soundEvent = Optional.of(soundEvent);
        return this;
    }

    public CuttingBoardRecipe group(String group) {
        this.group = group;
        return this;
    }

    public CuttingBoardRecipe withCondition(ICondition condition) {
        conditions.add(condition);
        return this;
    }

    @Override
    public JsonObject serialize() {
        JsonObject json = new JsonObject();
        json.addProperty("type", "farmersdelight:cutting");
        json.addProperty("group", this.group);
        JsonArray ingredients = new JsonArray();
        Ingredient.CODEC_NONEMPTY.encodeStart(JsonOps.INSTANCE, this.ingredient).result().ifPresent(ingredients::add);
        json.add("ingredients", ingredients);
        Ingredient.CODEC.encodeStart(JsonOps.INSTANCE, this.tool).result().ifPresent((tool) -> json.add("tool", tool));
        ChanceResult.CODEC.listOf().encodeStart(JsonOps.INSTANCE, this.results).result().ifPresent((result) -> json.add("result", result));
        this.soundEvent.flatMap(event -> SoundEvent.DIRECT_CODEC.encodeStart(JsonOps.INSTANCE, event).result()).ifPresent((sound) -> json.add("sound", sound));
        return json;
    }


}
