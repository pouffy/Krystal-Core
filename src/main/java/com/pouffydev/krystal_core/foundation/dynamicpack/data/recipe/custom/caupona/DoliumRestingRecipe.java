package com.pouffydev.krystal_core.foundation.dynamicpack.data.recipe.custom.caupona;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import com.pouffydev.krystal_core.foundation.data.recipe.input.SizedOrCatalystFluidIngredient;
import com.pouffydev.krystal_core.foundation.data.recipe.input.SizedOrCatalystIngredient;
import com.pouffydev.krystal_core.foundation.dynamicpack.data.recipe.custom.CustomRecipe;
import lombok.Getter;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class DoliumRestingRecipe extends CustomRecipe<CraftingInput> {
    private final NonNullList<SizedOrCatalystIngredient> items = NonNullList.create();
    public Ingredient container = Ingredient.EMPTY;
    public Fluid base = null;
    public SizedOrCatalystFluidIngredient fluid = null;
    public float density = 0;
    public boolean keepInfo = false;
    public ItemStack output;
    public int time = 1200;

    private final @Getter List<ICondition> conditions = new ArrayList<>();

    public DoliumRestingRecipe() {}

    public DoliumRestingRecipe(ItemLike output, int count, int time, @Nullable Ingredient container) {
        this(new ItemStack(output, count), time, container);
    }

    public DoliumRestingRecipe(ItemStack outputIn, int time, @Nullable Ingredient container) {
        this.output = outputIn;
        this.time = time;
        this.container = container != null ? container : Ingredient.EMPTY;
    }

    public static DoliumRestingRecipe restingRecipe(ItemLike output, int count, int time) {
        return new DoliumRestingRecipe(output, count, time, null);
    }

    public static DoliumRestingRecipe restingRecipe(ItemLike output, int count, int time, Ingredient container) {
        return new DoliumRestingRecipe(output, count, time, container);
    }

    public DoliumRestingRecipe withBase(Fluid fluid) {
        this.base = fluid;
        return this;
    }

    public DoliumRestingRecipe keepInfo(boolean value) {
        this.keepInfo = value;
        return this;
    }

    public DoliumRestingRecipe withDensity(int density) {
        this.density = density;
        return this;
    }

    public DoliumRestingRecipe addIngredient(TagKey<Item> tagIn) {
        return addIngredient(Ingredient.of(tagIn));
    }

    public DoliumRestingRecipe addIngredient(ItemLike itemIn) {
        return addIngredient(itemIn, 1);
    }

    public DoliumRestingRecipe addIngredient(ItemLike itemIn, int quantity) {
        items.add(SizedOrCatalystIngredient.of(itemIn, quantity));
        return this;
    }

    public DoliumRestingRecipe addIngredient(Ingredient ingredientIn) {
        return addIngredient(ingredientIn, 1);
    }

    public DoliumRestingRecipe addIngredient(Ingredient ingredientIn, int quantity) {
        items.add(new SizedOrCatalystIngredient(ingredientIn, quantity));
        return this;
    }

    public DoliumRestingRecipe addFluidIngredient(Fluid fluid, int i) {
        this.fluid = SizedOrCatalystFluidIngredient.of(fluid, i);
        return this;
    }

    public DoliumRestingRecipe addFluidIngredient(TagKey<Fluid> fluid, int i) {
        this.fluid = SizedOrCatalystFluidIngredient.of(fluid, i);
        return this;
    }

    public DoliumRestingRecipe addFluidIngredient(FluidIngredient ingredient, int i) {
        this.fluid = new SizedOrCatalystFluidIngredient(ingredient, i);
        return this;
    }

    public DoliumRestingRecipe withCondition(ICondition condition) {
        conditions.add(condition);
        return this;
    }

    @Override
    public JsonObject serialize() {
        JsonObject json = new JsonObject();
        json.addProperty("type", "caupona:dolium");
        SizedOrCatalystIngredient.FLAT_CODEC.listOf().encodeStart(JsonOps.INSTANCE, this.items).result().ifPresent((items) -> json.add("items", items));
        if (!container.isEmpty()) Ingredient.CODEC.encodeStart(JsonOps.INSTANCE, this.container).result().ifPresent((container) -> json.add("container", container));
        if (base != null) BuiltInRegistries.FLUID.byNameCodec().encodeStart(JsonOps.INSTANCE, this.base).result().ifPresent((base) -> json.add("base", base));
        if (fluid != null) SizedOrCatalystFluidIngredient.FLAT_CODEC.encodeStart(JsonOps.INSTANCE, this.fluid).result().ifPresent((fluid) -> json.add("fluid", fluid));
        json.addProperty("density", this.density);
        json.addProperty("keepInfo", this.keepInfo);
        ItemStack.CODEC.encodeStart(JsonOps.INSTANCE, this.output).result().ifPresent((output) -> json.add("output", output));
        json.addProperty("time", this.time);
        return json;
    }
}
