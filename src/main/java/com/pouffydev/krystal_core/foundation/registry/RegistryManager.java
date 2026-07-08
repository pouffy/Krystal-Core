package com.pouffydev.krystal_core.foundation.registry;

import com.pouffydev.krystal_core.foundation.registry.definition.block.BlockRegistryHelper;
import com.pouffydev.krystal_core.foundation.registry.definition.fluid.FluidRegistryHelper;
import com.pouffydev.krystal_core.foundation.registry.definition.item.ItemRegistryHelper;
import lombok.Getter;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

@SuppressWarnings("unchecked")
public class RegistryManager {
    private final Map<ResourceKey<? extends Registry<?>>, RegistryHelper<?>> registryHelpers;

    @Getter
    private final String modId;
    @Getter
    private final IEventBus eventBus;

    private static RegistryManager INSTANCE;

    public RegistryManager(String modId, IEventBus eventBus) {
        this.modId = modId;
        this.eventBus = eventBus;
        this.registryHelpers = new HashMap<>();
        INSTANCE = this;
    }

    public static RegistryManager makeDefault(String modId, IEventBus eventBus) {
        var itemHelper = new ItemRegistryHelper();
        return new RegistryManager(modId, eventBus)
                .withHelper(Registries.ITEM, itemHelper)
                .withHelper(Registries.BLOCK, new BlockRegistryHelper(itemHelper))
                .withHelper(Registries.FLUID, new FluidRegistryHelper(itemHelper))
                .withHelper(Registries.CREATIVE_MODE_TAB, new CreativeTabRegistryHelper());
    }

    public <T> RegistryManager withHelper(ResourceKey<Registry<T>> key, RegistryHelper<T> helper) {
        this.registryHelpers.put(key, helper);
        return this;
    }

    public <T> boolean hasHelper(ResourceKey<Registry<T>> registryResourceKey) {
        return this.registryHelpers.containsKey(registryResourceKey);
    }

    public <T, H extends RegistryHelper<T>> H getHelper(ResourceKey<Registry<T>> registryResourceKey) {
        if (!this.hasHelper(registryResourceKey)) {
            throw new NullPointerException("Registry Manager for '" + this.modId + "' has no Helper for registry: " + registryResourceKey.location());
        }
        return (H) this.registryHelpers.get(registryResourceKey);
    }

    public ItemRegistryHelper getItemHelper() {
        return getHelper(Registries.ITEM);
    }

    public BlockRegistryHelper getBlockHelper() {
        return getHelper(Registries.BLOCK);
    }

    public CreativeTabRegistryHelper getCreativeTabHelper() {
        return getHelper(Registries.CREATIVE_MODE_TAB);
    }

    public static RegistryManager getInstance() {
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
        return registerToBus(DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, getModId()));
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
