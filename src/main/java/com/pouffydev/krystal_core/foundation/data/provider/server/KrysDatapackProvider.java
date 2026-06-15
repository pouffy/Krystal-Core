package com.pouffydev.krystal_core.foundation.data.provider.server;

import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.WithConditions;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public abstract class KrysDatapackProvider<T> implements DataProvider {
    protected final PackOutput.PathProvider pathProvider;
    private final CompletableFuture<HolderLookup.Provider> registries;
    private final String modid;
    private final String name;

    public KrysDatapackProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries, String modid, String kind, String name) {
        this.pathProvider = packOutput.createPathProvider(PackOutput.Target.DATA_PACK, kind);
        this.registries = registries;
        this.modid = modid;
        this.name = name;
    }

    protected abstract void addData(KrysOutput<T> output, HolderLookup.Provider registries);

    protected abstract Codec<Optional<WithConditions<T>>> codec();

    public final CompletableFuture<?> run(CachedOutput output) {
        return this.registries.thenCompose((provider) -> this.run(output, provider));
    }

    public CompletableFuture<?> run(final CachedOutput output, final HolderLookup.Provider registries) {
        Set<CompletableFuture<?>> list = new HashSet<>();
        final Set<ResourceLocation> set = Sets.newHashSet();
        this.addData((location, value, conditions) -> {
            if (!set.add(location)) {
                throw new IllegalStateException("Duplicate " + name + ": " + location);
            } else {
                list.add(DataProvider.saveStable(output, registries, codec(), Optional.of(new WithConditions<>(value, conditions)), pathProvider.json(location)));
            }
        }, registries);
        return CompletableFuture.allOf(list.toArray(CompletableFuture[]::new));
    }

    public void save(KrysOutput<T> output, T value, ResourceLocation catchId) {
        output.accept(catchId, value);
    }

    public void save(KrysOutput<T> output, T value, ResourceLocation catchId, ICondition... conditions) {
        output.accept(catchId, value, conditions);
    }

    @Override
    public String getName() {
        return "%s %s Provider".formatted(this.modid, this.name);
    }
}
