package com.pouffydev.krystal_core.foundation.registry;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class CreativeTabRegistryHelper extends RegistryHelper {
    public CreativeTabRegistryHelper(String modId, IEventBus eventBus) {
        super(modId, eventBus);
    }

    public final DeferredRegister<CreativeModeTab> TABS = createRegister(Registries.CREATIVE_MODE_TAB);

    public DeferredHolder<CreativeModeTab, CreativeModeTab> registerTab(String name, Holder<Item> icon, BiConsumer<CreativeModeTab.ItemDisplayParameters, CreativeModeTab.Output> displayItems) {
        return registerTab(name, icon, displayItems, noAction());
    }

    public DeferredHolder<CreativeModeTab, CreativeModeTab> registerTab(String name, Holder<Item> icon, BiConsumer<CreativeModeTab.ItemDisplayParameters, CreativeModeTab.Output> displayItems, Consumer<CreativeModeTab.Builder> additionalProperties) {
        return TABS.register(name, id -> {
            final CreativeModeTab.Builder builder = CreativeModeTab.builder();
            builder.title(Component.translatable(id.toLanguageKey("itemGroup")))
                    .icon(() -> new ItemStack(icon))
                    .displayItems(displayItems::accept);
            additionalProperties.accept(builder);
            return builder.build();
        });
    }

    public DeferredHolder<CreativeModeTab, CreativeModeTab> registerTabSearchBar(String name, Holder<Item> icon, BiConsumer<CreativeModeTab.ItemDisplayParameters, CreativeModeTab.Output> displayItems, Consumer<CreativeModeTab.Builder> additionalProperties) {
        return TABS.register(name, id -> {
            final CreativeModeTab.Builder builder = CreativeModeTab.builder();
            builder.title(Component.translatable(id.toLanguageKey("itemGroup")))
                    .icon(() -> new ItemStack(icon))
                    .withSearchBar()
                    .displayItems(displayItems::accept);
            additionalProperties.accept(builder);
            return builder.build();
        });
    }

    public DeferredHolder<CreativeModeTab, CreativeModeTab> registerTabWithBlockIcon(String name, Holder<Block> icon, BiConsumer<CreativeModeTab.ItemDisplayParameters, CreativeModeTab.Output> displayItems, Consumer<CreativeModeTab.Builder> additionalProperties) {
        return TABS.register(name, id -> {
            final CreativeModeTab.Builder builder = CreativeModeTab.builder();
            builder.title(Component.translatable(id.toLanguageKey("itemGroup")))
                    .icon(() -> new ItemStack((ItemLike) icon))
                    .displayItems(displayItems::accept);
            additionalProperties.accept(builder);
            return builder.build();
        });
    }
}
