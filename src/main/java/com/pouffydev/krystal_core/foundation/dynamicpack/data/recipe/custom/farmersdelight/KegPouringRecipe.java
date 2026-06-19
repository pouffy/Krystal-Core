package com.pouffydev.krystal_core.foundation.dynamicpack.data.recipe.custom.farmersdelight;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import com.pouffydev.krystal_core.foundation.dynamicpack.data.recipe.custom.CustomRecipe;
import lombok.Getter;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class KegPouringRecipe extends CustomRecipe<KegWrapper> {
    private FluidStack fluid;
    private Optional<ItemStack> container;
    private ItemStack output;
    private boolean strict;
    private boolean filling;

    private final @Getter List<ICondition> conditions = new ArrayList<>();

    public KegPouringRecipe() {

    }

    public KegPouringRecipe(FluidStack fluid, ItemStack output, boolean strict, boolean filling) {
        this.fluid = fluid;
        this.output = output;
        this.strict = strict;
        this.filling = filling;
    }

    public static KegPouringRecipe kegPouringRecipe(Fluid fluid, int amount, ItemStack output, boolean strict) {
        return new KegPouringRecipe(new FluidStack(fluid, amount), output, strict, true);
    }

    public static KegPouringRecipe kegPouringRecipe(Fluid fluid, int amount, ItemStack output, boolean strict, boolean filling) {
        return new KegPouringRecipe(new FluidStack(fluid, amount), output, strict, filling);
    }

    public static KegPouringRecipe kegPouringRecipe(Fluid fluid, int amount, ItemLike output) {
        return new KegPouringRecipe(new FluidStack(fluid, amount), output.asItem().getDefaultInstance(), false, true);
    }

    public static KegPouringRecipe kegPouringRecipe(Fluid fluid, int amount, ItemLike output, boolean filling) {
        return new KegPouringRecipe(new FluidStack(fluid, amount), output.asItem().getDefaultInstance(), false, filling);
    }

    public KegPouringRecipe withContainer(ItemLike container) {
        this.container = Optional.of(container.asItem().getDefaultInstance());
        return this;
    }

    public KegPouringRecipe withCondition(ICondition condition) {
        conditions.add(condition);
        return this;
    }

    @Override
    public JsonObject serialize() {
        JsonObject json = new JsonObject();
        json.addProperty("type", "brewinandchewin:keg_pouring");
        FluidStack.CODEC.encodeStart(JsonOps.INSTANCE, this.fluid).result().ifPresent((fluid) -> json.add("fluid", fluid));
        if (container.isPresent()) {
            ItemStack.CODEC.encodeStart(JsonOps.INSTANCE, this.container.get()).result().ifPresent((container) -> json.add("container", container));
        }
        ItemStack.CODEC.encodeStart(JsonOps.INSTANCE, this.output).result().ifPresent((output) -> json.add("output", output));
        json.addProperty("unit", "millibuckets");
        json.addProperty("strict", this.strict);
        json.addProperty("can_fill", this.filling);
        return json;
    }
}
