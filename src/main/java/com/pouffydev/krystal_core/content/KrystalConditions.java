package com.pouffydev.krystal_core.content;

import com.mojang.serialization.MapCodec;
import com.pouffydev.krystal_core.KrystalCore;
import com.pouffydev.krystal_core.foundation.data.condition.BooleanConfigCondition;
import com.pouffydev.krystal_core.foundation.data.condition.BundleLoadedCondition;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class KrystalConditions {
    public static final DeferredRegister<MapCodec<? extends ICondition>> CONDITIONS = KrystalCore.getRegistryManager().createRegister(NeoForgeRegistries.Keys.CONDITION_CODECS);

    public static final DeferredHolder<MapCodec<? extends ICondition>, MapCodec<BundleLoadedCondition>> BUNDLE_LOADED = CONDITIONS.register("bundle_loaded", () -> BundleLoadedCondition.CODEC);
    public static final DeferredHolder<MapCodec<? extends ICondition>, MapCodec<BooleanConfigCondition>> BOOLEAN_CONFIG = CONDITIONS.register("config_boolean", () -> BooleanConfigCondition.CODEC);

    public static void staticInit() {}
}
