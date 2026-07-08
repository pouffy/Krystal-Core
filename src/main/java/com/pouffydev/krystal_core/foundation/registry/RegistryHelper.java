package com.pouffydev.krystal_core.foundation.registry;


import com.pouffydev.krystal_core.foundation.registry.definition.block.BlockRegistryHelper;
import com.pouffydev.krystal_core.foundation.registry.definition.item.ItemRegistryHelper;
import lombok.Getter;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class RegistryHelper {
    public static List<DeferredRegister<DataComponentType<?>>> COMPONENT_REGISTRIES = new ArrayList<>();
    @Getter
    private final String modId;
    @Getter
    private final IEventBus eventBus;

    private static RegistryHelper INSTANCE;

    public RegistryHelper(String modId, IEventBus eventBus) {
        this.modId = modId;
        this.eventBus = eventBus;
        INSTANCE = this;
    }

    public ItemRegistryHelper getItemHelper() {
        return new ItemRegistryHelper(getModId(), getEventBus());
    }

    public BlockRegistryHelper getBlockHelper() {
        return new BlockRegistryHelper(getItemHelper());
    }

    public CreativeTabRegistryHelper getCreativeTabHelper() {
        return new CreativeTabRegistryHelper(getModId(), getEventBus());
    }

    public static RegistryHelper getInstance() {
        return INSTANCE;
    }

    private static final Consumer<?> NO_ACTION = (a) -> {};

    public ResourceLocation location(String path) {
        return ResourceLocation.fromNamespaceAndPath(getModId(), path);
    }

    public <DR extends DeferredRegister<T>, T> DR createRegister(Function<String, DR> factory) {
        return registerToBus(factory.apply(getModId()));
    }

    public <T> DeferredRegister<T> createRegister(ResourceKey<Registry<T>> registry) {
        return registerToBus(DeferredRegister.create(registry, getModId()));
    }

    public DeferredRegister.Items createItems() {
        return registerToBus(DeferredRegister.createItems(getModId()));
    }

    public DeferredRegister.Blocks createBlocks() {
        return registerToBus(DeferredRegister.createBlocks(getModId()));
    }

    public DeferredRegister<DataComponentType<?>> createComponents() {
        var register = registerToBus(DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, getModId()));
        COMPONENT_REGISTRIES.add(register);
        return register;
    }

    private <DR extends DeferredRegister<T>, T> DR registerToBus(DR deferredRegister) {
        deferredRegister.register(getEventBus());
        return deferredRegister;
    }

    @SuppressWarnings("unchecked")
    public static  <T> Consumer<T> noAction() {
        return ((Consumer<T>) NO_ACTION);
    }
}
