package com.pouffydev.krystal_core.foundation.dynamicpack.data.recipe.custom.farmersdelight;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import com.pouffydev.krystal_core.foundation.dynamicpack.data.recipe.custom.CustomRecipe;
import lombok.Getter;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.common.conditions.ICondition;

import java.util.ArrayList;
import java.util.List;

public class KettlePouringRecipe extends CustomRecipe<RecipeInput> {
    private Ingredient container;
    private Fluid fluid;
    private int amount;
    private ItemStack output;

    private final @Getter List<ICondition> conditions = new ArrayList<>();

    public KettlePouringRecipe() {}

    private KettlePouringRecipe(Ingredient container, Fluid fluid, int amount, ItemStack output) {
        this.container = container;
        this.fluid = fluid;
        this.amount = amount;
        this.output = output;
    }

    public static KettlePouringRecipe pouringRecipe(Ingredient container, Fluid fluid, int amount, ItemStack output) {
        return new KettlePouringRecipe(container, fluid, amount, output);
    }

    public KettlePouringRecipe withCondition(ICondition condition) {
        conditions.add(condition);
        return this;
    }

    @Override
    public JsonObject serialize() {
        JsonObject json = new JsonObject();
        json.addProperty("type", "farmersrespite:kettle_pouring");
        Ingredient.CODEC.encodeStart(JsonOps.INSTANCE, this.container).result().ifPresent((container) -> json.add("container", container));
        BuiltInRegistries.FLUID.byNameCodec().encodeStart(JsonOps.INSTANCE, this.fluid).result().ifPresent((fluid) -> json.add("fluid", fluid));
        json.addProperty("amount", this.amount);
        ItemStack.STRICT_CODEC.encodeStart(JsonOps.INSTANCE, this.output).result().ifPresent((output) -> json.add("output", output));
        return json;
    }
}
