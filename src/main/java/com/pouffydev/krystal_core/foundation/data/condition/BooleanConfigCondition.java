package com.pouffydev.krystal_core.foundation.data.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.conditions.ICondition;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BooleanSupplier;

public record BooleanConfigCondition(ResourceLocation option, boolean enabledWhen) implements ICondition {
    public static final Map<ResourceLocation, BooleanSupplier> OPTIONS = new HashMap<>();

    public static final MapCodec<BooleanConfigCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("option").forGetter(BooleanConfigCondition::option),
            Codec.BOOL.fieldOf("enabled_when").forGetter(BooleanConfigCondition::enabledWhen)
    ).apply(instance, BooleanConfigCondition::new));

    @Override
    public boolean test(ICondition.IContext context) {
        BooleanSupplier supplier = OPTIONS.get(option);
        return supplier != null && supplier.getAsBoolean() == enabledWhen;
    }

    @Override
    public MapCodec<? extends ICondition> codec() {
        return CODEC;
    }

    public static ModConfigSpec.BooleanValue addOption(ModConfigSpec.Builder configBuilder, ResourceLocation option) {
        return addOption(configBuilder, option, true);
    }

    public static ModConfigSpec.BooleanValue addOption(ModConfigSpec.Builder configBuilder, ResourceLocation option, boolean defaultValue) {
        var configValue = configBuilder.define(option.toString().replace(':', '_'), defaultValue);
        OPTIONS.put(option, configValue::get);
        return configValue;
    }
}
