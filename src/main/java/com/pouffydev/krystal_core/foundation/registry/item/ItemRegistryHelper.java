package com.pouffydev.krystal_core.foundation.registry.item;

import com.pouffydev.krystal_core.foundation.registry.RegistryHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class ItemRegistryHelper extends RegistryHelper {

    public ItemRegistryHelper(String modId, IEventBus eventBus) {
        super(modId, eventBus);
    }

    public final DeferredRegister<Item> ITEMS = createRegister(Registries.ITEM);

    public <T extends Item> ItemHolderSet<T, DyeColor> registerColorSet(String name, BiFunction<DyeColor, Item.Properties, T> function, Function<DyeColor, Item.Properties> properties) {
        return this.registerSet(DyeColor.class, name, function, properties);
    }

    public <T extends Item> ItemHolderSet<T, DyeColor> registerColorSet(UnaryOperator<String> name, BiFunction<DyeColor, Item.Properties, T> function, Function<DyeColor, Item.Properties> properties) {
        return this.registerSet(DyeColor.class, name, function, properties);
    }

    public <T extends Item> ItemHolderSet<T, DyeColor> registerColorSet(String name, BiFunction<DyeColor, Item.Properties, T> function, Function<DyeColor, Item.Properties> properties, UnaryOperator<DeferredHolder<Item, T>> unaryOperator) {
        return this.registerSet(DyeColor.class, name, function, properties, unaryOperator);
    }

    public <T extends Item> ItemHolderSet<T, DyeColor> registerColorSet(UnaryOperator<String> name, BiFunction<DyeColor, Item.Properties, T> function, Function<DyeColor, Item.Properties> properties, UnaryOperator<DeferredHolder<Item, T>> unaryOperator) {
        return this.registerSet(DyeColor.class, name, function, properties, unaryOperator);
    }

    public <K extends Enum<K> & StringRepresentable, T extends Item> ItemHolderSet<T, K> registerSet(Class<K> keyType, String name, BiFunction<K, Item.Properties, T> function, Function<K, Item.Properties> properties) {
        return this.registerSet(keyType, s -> s + "_" + name, function, properties);
    }

    public <K extends Enum<K> & StringRepresentable, T extends Item> ItemHolderSet<T, K> registerSet(Class<K> keyType, UnaryOperator<String> nameFunction, BiFunction<K, Item.Properties, T> function, Function<K, Item.Properties> properties) {
        ItemHolderSet<T, K> set = new ItemHolderSet<>(keyType);

        for (K key : keyType.getEnumConstants()) {
            String name = nameFunction.apply(key.getSerializedName());
            set.put(key, ITEMS.register(name, () -> function.apply(key, properties.apply(key))));
        }

        return set;
    }

    public <K extends Enum<K> & StringRepresentable, T extends Item> ItemHolderSet<T, K> registerSet(Class<K> keyType, String name, BiFunction<K, Item.Properties, T> function, Function<K, Item.Properties> properties, UnaryOperator<DeferredHolder<Item, T>> unaryOperator) {
        return this.registerSet(keyType, s -> s + "_" + name, function, properties, unaryOperator);
    }

    public <K extends Enum<K> & StringRepresentable, T extends Item> ItemHolderSet<T, K> registerSet(Class<K> keyType, UnaryOperator<String> nameFunction, BiFunction<K, Item.Properties, T> function, Function<K, Item.Properties> properties, UnaryOperator<DeferredHolder<Item, T>> unaryOperator) {
        ItemHolderSet<T, K> set = new ItemHolderSet<>(keyType);

        for (K key : keyType.getEnumConstants()) {
            String name = nameFunction.apply(key.getSerializedName());
            set.put(key, unaryOperator.apply(ITEMS.register(name, () -> function.apply(key, properties.apply(key)))));
        }

        return set;
    }
}
