package com.pouffydev.krystal_core.foundation.data.recipe.input;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.common.util.NeoForgeExtraCodecs;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.stream.Stream;

public class SizedOrCatalystFluidIngredient {
    public static final Codec<SizedOrCatalystFluidIngredient> FLAT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    FluidIngredient.MAP_CODEC_NONEMPTY.forGetter(SizedOrCatalystFluidIngredient::ingredient),
                    NeoForgeExtraCodecs.optionalFieldAlwaysWrite(ExtraCodecs.NON_NEGATIVE_INT, "amount", FluidType.BUCKET_VOLUME).forGetter(SizedOrCatalystFluidIngredient::amount))
            .apply(instance, SizedOrCatalystFluidIngredient::new));

    public static final Codec<SizedOrCatalystFluidIngredient> NESTED_CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    FluidIngredient.CODEC_NON_EMPTY.fieldOf("ingredient").forGetter(SizedOrCatalystFluidIngredient::ingredient),
                    NeoForgeExtraCodecs.optionalFieldAlwaysWrite(ExtraCodecs.NON_NEGATIVE_INT, "amount", FluidType.BUCKET_VOLUME).forGetter(SizedOrCatalystFluidIngredient::amount))
            .apply(instance, SizedOrCatalystFluidIngredient::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, SizedOrCatalystFluidIngredient> STREAM_CODEC = StreamCodec.composite(
            FluidIngredient.STREAM_CODEC,
            SizedOrCatalystFluidIngredient::ingredient,
            ByteBufCodecs.VAR_INT,
            SizedOrCatalystFluidIngredient::amount,
            SizedOrCatalystFluidIngredient::new);

    public static SizedOrCatalystFluidIngredient of(Fluid fluid, int amount) {
        return new SizedOrCatalystFluidIngredient(FluidIngredient.of(fluid), amount);
    }

    public static SizedOrCatalystFluidIngredient of(FluidStack stack) {
        return new SizedOrCatalystFluidIngredient(FluidIngredient.single(stack), stack.getAmount());
    }

    public static SizedOrCatalystFluidIngredient of(TagKey<Fluid> tag, int amount) {
        return new SizedOrCatalystFluidIngredient(FluidIngredient.tag(tag), amount);
    }

    private final FluidIngredient ingredient;
    private final int amount;

    @Nullable
    private FluidStack[] cachedStacks;

    public SizedOrCatalystFluidIngredient(FluidIngredient ingredient, int amount) {

        this.ingredient = ingredient;
        this.amount = amount;
    }

    public FluidIngredient ingredient() {
        return ingredient;
    }

    public int amount() {
        return amount;
    }

    public boolean test(FluidStack stack) {
        return ingredient.test(stack) && stack.getAmount() >= amount;
    }

    public FluidStack[] getFluids() {
        if (cachedStacks == null) {
            cachedStacks = Stream.of(ingredient.getStacks())
                    .map(s -> s.copyWithAmount(amount>0?amount:1))
                    .toArray(FluidStack[]::new);
        }
        return cachedStacks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SizedOrCatalystFluidIngredient other)) return false;
        return amount == other.amount && ingredient.equals(other.ingredient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ingredient, amount);
    }

    @Override
    public String toString() {
        return amount + "x " + ingredient;
    }
}
