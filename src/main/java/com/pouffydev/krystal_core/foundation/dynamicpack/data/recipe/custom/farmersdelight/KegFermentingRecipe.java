package com.pouffydev.krystal_core.foundation.dynamicpack.data.recipe.custom.farmersdelight;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.pouffydev.krystal_core.foundation.dynamicpack.data.recipe.custom.CustomRecipe;
import lombok.Getter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class KegFermentingRecipe extends CustomRecipe<KegWrapper> {
    private int ingredientCount = 0;
    private final NonNullList<Ingredient> ingredients = NonNullList.withSize(4, Ingredient.EMPTY);
    private FermentingBookCategory tab;

    private Optional<SizedFluidIngredient> fluidIngredient = Optional.empty();
    private Either<FluidStack, ItemStack> result = null;

    private int fermentingTime = 9600;
    private float experience = 0.0F;
    private int temperature = 3;
    private int amount;

    private final @Getter List<ICondition> conditions = new ArrayList<>();

    public KegFermentingRecipe() {}

    private KegFermentingRecipe(FermentingBookCategory tab, int amount, int fermentingTime, float experience, int temperature) {
        this.fermentingTime = fermentingTime;
        this.tab = tab;
        this.experience = experience;
        this.temperature = temperature;
        this.amount = amount;
    }

    public static KegFermentingRecipe kegFermentingRecipe(FermentingBookCategory tab, Item item, int amount, int fermentingTime, float experience, int temperature) {
        KegFermentingRecipe i = new KegFermentingRecipe(tab, amount, fermentingTime, experience, temperature);
        i.setResult(item);
        return i;
    }

    public static KegFermentingRecipe kegFermentingRecipe(FermentingBookCategory tab, Fluid fluid, int amount, int fermentingTime, float experience, int temperature) {
        KegFermentingRecipe i = new KegFermentingRecipe(tab, amount, fermentingTime, experience, temperature);
        i.setResult(fluid);
        return i;
    }

    public static KegFermentingRecipe kegFermentingRecipe(FermentingBookCategory tab, Item item, int amount, int fermentingTime, float experience) {
        KegFermentingRecipe i = new KegFermentingRecipe(tab, amount, fermentingTime, experience, 3);
        i.setResult(item);
        return i;
    }

    public static KegFermentingRecipe kegFermentingRecipe(FermentingBookCategory tab, Fluid fluid, int amount, int fermentingTime, float experience) {
        KegFermentingRecipe i = new KegFermentingRecipe(tab, amount, fermentingTime, experience, 3);
        i.setResult(fluid);
        return i;
    }

    private void setResult(Fluid fluid) {
        result = Either.left(new FluidStack(fluid, amount));
    }

    private void setResult(FluidStack fluid) {
        result = Either.left(new FluidStack(fluid.getFluidHolder(), fluid.getAmount(), fluid.getComponentsPatch()));
    }

    private void setResult(Item item) {
        result = Either.right(item.getDefaultInstance().copyWithCount(amount));
    }

    private void setResult(ItemStack stack) {
        result = Either.right(stack.copyWithCount(amount));
    }

    public KegFermentingRecipe addIngredient(TagKey<Item> tagIn) {
        return addIngredient(Ingredient.of(tagIn));
    }

    public KegFermentingRecipe addIngredient(ItemLike itemIn) {
        return addIngredient(itemIn, 1);
    }

    public KegFermentingRecipe addIngredient(ItemLike itemIn, int quantity) {
        addIngredient(Ingredient.of(itemIn), quantity);
        return this;
    }

    public KegFermentingRecipe addIngredient(Ingredient ingredientIn) {
        return addIngredient(ingredientIn, 1);
    }

    public KegFermentingRecipe addIngredient(Ingredient ingredientIn, int quantity) {
        for (int i = 0; i < quantity; ++i) {
            ingredients.set(ingredientCount, ingredientIn);
            ++ingredientCount;
        }
        return this;
    }

    public KegFermentingRecipe addFluidIngredient(Fluid fluid, int i) {
        fluidIngredient = Optional.of(SizedFluidIngredient.of(fluid, i));
        return this;
    }

    public KegFermentingRecipe addFluidIngredient(TagKey<Fluid> fluid, int i) {
        fluidIngredient = Optional.of(SizedFluidIngredient.of(fluid, i));
        return this;
    }

    public KegFermentingRecipe addFluidIngredient(FluidIngredient ingredient, int i) {
        fluidIngredient = Optional.of(new SizedFluidIngredient(ingredient, i));
        return this;
    }

    public KegFermentingRecipe withCondition(ICondition condition) {
        conditions.add(condition);
        return this;
    }

    @Override
    public JsonObject serialize() {
        JsonObject json = new JsonObject();
        json.addProperty("type", "brewinandchewin:keg_fermenting");
        JsonArray ingredients = new JsonArray();
        for (Ingredient ingredient : this.ingredients) {
            Ingredient.CODEC.encodeStart(JsonOps.INSTANCE, ingredient).result().ifPresent(ingredients::add);
        }
        json.add("ingredients", ingredients);
        json.addProperty("category", this.tab.getSerializedName());
        this.fluidIngredient.flatMap(sizedFluidIngredient -> SizedFluidIngredient.NESTED_CODEC.encodeStart(JsonOps.INSTANCE, sizedFluidIngredient).result()).ifPresent((fluid) -> json.add("base_fluid", fluid));
        json.addProperty("unit", "millibuckets");
        Codec.either(FluidStack.CODEC, ItemStack.CODEC).encodeStart(JsonOps.INSTANCE, this.result).result().ifPresent((result) -> json.add("result", result));
        json.addProperty("experience", this.experience);
        json.addProperty("fermenting_time", this.fermentingTime);
        json.addProperty("temperature", this.temperature);
        return json;
    }

    public enum FermentingBookCategory implements StringRepresentable {
        MEALS, DRINKS;

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }
}
