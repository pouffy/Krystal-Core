package com.pouffydev.krystal_core.foundation.bundle;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.pouffydev.krystal_core.KrystalCore;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.conditions.ICondition;
import org.jetbrains.annotations.NotNull;

public record BundleLoadedCondition(ResourceLocation id) implements ICondition {
    public static final MapCodec<BundleLoadedCondition> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(ResourceLocation.CODEC.fieldOf("id").forGetter(BundleLoadedCondition::id)).apply(instance, BundleLoadedCondition::new));

    @Override
    public boolean test(@NotNull IContext context) {
        return KrystalCore.isBundleLoaded(id);
    }

    @Override
    public MapCodec<BundleLoadedCondition> codec() {
        return CODEC;
    }
}
