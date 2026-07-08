package com.pouffydev.krystal_core.foundation.registry.definition.item;

import com.pouffydev.krystal_core.foundation.TextHelpers;
import com.pouffydev.krystal_core.foundation.registry.RegistryHelper;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class ItemRegistryHelper extends RegistryHelper {
    public final List<ItemDefinition<?>> ITEM_DEFINITIONS;

    public ItemRegistryHelper(String modId, IEventBus eventBus) {
        super(modId, eventBus);
        this.ITEM_DEFINITIONS = new ArrayList<>();
    }

    public final DeferredRegister.Items ITEMS = createItems();

    public <T extends Item> ItemDefinition<T> register(String name, Supplier<T> constructor) {
        DeferredItem<T> deferred = ITEMS.register(name, constructor);
        ItemDefinition<T> definition = ItemDefinition.fromHolder(deferred, "");
        ITEM_DEFINITIONS.add(definition);
        return definition;
    }

    public <T extends Item> ItemDefinition<T> register(String name, Function<Item.Properties, T> constructor) {
        DeferredItem<T> deferred = ITEMS.register(name, () -> constructor.apply(new Item.Properties()));
        ItemDefinition<T> definition = ItemDefinition.fromHolder(deferred, "");
        ITEM_DEFINITIONS.add(definition);
        return definition;
    }

    public <T extends Item> ItemDefinition<T> register(String name, String langName, Supplier<T> constructor) {
        DeferredItem<T> deferred = ITEMS.register(name, constructor);
        ItemDefinition<T> definition = ItemDefinition.fromHolder(deferred, langName);
        ITEM_DEFINITIONS.add(definition);
        return definition;
    }

    public <T extends Item> ItemDefinition<T> register(String name, String langName, Function<Item.Properties, T> constructor) {
        DeferredItem<T> deferred = ITEMS.register(name, () -> constructor.apply(new Item.Properties()));
        ItemDefinition<T> definition = ItemDefinition.fromHolder(deferred, langName);
        ITEM_DEFINITIONS.add(definition);
        return definition;
    }

    public <T extends Item> ItemDefinitionSet<T, DyeColor> registerColorSet(String name, BiFunction<DyeColor, Item.Properties, T> function, Function<DyeColor, Item.Properties> properties) {
        return this.registerSet(DyeColor.class, name, function, properties);
    }

    public <T extends Item> ItemDefinitionSet<T, DyeColor> registerColorSet(UnaryOperator<String> name, BiFunction<DyeColor, Item.Properties, T> function, Function<DyeColor, Item.Properties> properties) {
        return this.registerSet(DyeColor.class, name, function, properties);
    }

    public <T extends Item> ItemDefinitionSet<T, DyeColor> registerColorSet(String name, BiFunction<DyeColor, Item.Properties, T> function, Function<DyeColor, Item.Properties> properties, UnaryOperator<ItemDefinition<T>> unaryOperator) {
        return this.registerSet(DyeColor.class, name, function, properties, unaryOperator);
    }

    public <T extends Item> ItemDefinitionSet<T, DyeColor> registerColorSet(UnaryOperator<String> name, BiFunction<DyeColor, Item.Properties, T> function, Function<DyeColor, Item.Properties> properties, UnaryOperator<ItemDefinition<T>> unaryOperator) {
        return this.registerSet(DyeColor.class, name, function, properties, unaryOperator);
    }

    public <T extends Item> ItemDefinitionSet<T, DyeColor> registerColorSet(String name, String langName, BiFunction<DyeColor, Item.Properties, T> function, Function<DyeColor, Item.Properties> properties) {
        return this.registerSet(DyeColor.class, name, langName, function, properties);
    }

    public <T extends Item> ItemDefinitionSet<T, DyeColor> registerColorSet(UnaryOperator<String> name, UnaryOperator<String> langName, BiFunction<DyeColor, Item.Properties, T> function, Function<DyeColor, Item.Properties> properties) {
        return this.registerSet(DyeColor.class, name, langName, function, properties);
    }

    public <T extends Item> ItemDefinitionSet<T, DyeColor> registerColorSet(String name, String langName, BiFunction<DyeColor, Item.Properties, T> function, Function<DyeColor, Item.Properties> properties, UnaryOperator<ItemDefinition<T>> unaryOperator) {
        return this.registerSet(DyeColor.class, name, langName, function, properties, unaryOperator);
    }

    public <T extends Item> ItemDefinitionSet<T, DyeColor> registerColorSet(UnaryOperator<String> name, UnaryOperator<String> langName, BiFunction<DyeColor, Item.Properties, T> function, Function<DyeColor, Item.Properties> properties, UnaryOperator<ItemDefinition<T>> unaryOperator) {
        return this.registerSet(DyeColor.class, name, langName, function, properties, unaryOperator);
    }

    public <K extends Enum<K> & StringRepresentable, T extends Item> ItemDefinitionSet<T, K> registerSet(Class<K> keyType, String name, BiFunction<K, Item.Properties, T> function, Function<K, Item.Properties> properties) {
        return this.registerSet(keyType, s -> s + "_" + name, function, properties);
    }

    public <K extends Enum<K> & StringRepresentable, T extends Item> ItemDefinitionSet<T, K> registerSet(Class<K> keyType, UnaryOperator<String> nameFunction, BiFunction<K, Item.Properties, T> function, Function<K, Item.Properties> properties) {
        ItemDefinitionSet<T, K> set = new ItemDefinitionSet<>(keyType);

        for (K key : keyType.getEnumConstants()) {
            String name = nameFunction.apply(key.getSerializedName());
            set.put(key, register(name, () -> function.apply(key, properties.apply(key))));
        }

        return set;
    }

    public <K extends Enum<K> & StringRepresentable, T extends Item> ItemDefinitionSet<T, K> registerSet(Class<K> keyType, String name, BiFunction<K, Item.Properties, T> function, Function<K, Item.Properties> properties, UnaryOperator<ItemDefinition<T>> unaryOperator) {
        return this.registerSet(keyType, s -> s + "_" + name, function, properties, unaryOperator);
    }

    public <K extends Enum<K> & StringRepresentable, T extends Item> ItemDefinitionSet<T, K> registerSet(Class<K> keyType, UnaryOperator<String> nameFunction, BiFunction<K, Item.Properties, T> function, Function<K, Item.Properties> properties, UnaryOperator<ItemDefinition<T>> unaryOperator) {
        ItemDefinitionSet<T, K> set = new ItemDefinitionSet<>(keyType);

        for (K key : keyType.getEnumConstants()) {
            String name = nameFunction.apply(key.getSerializedName());
            set.put(key, unaryOperator.apply(register(name, () -> function.apply(key, properties.apply(key)))));
        }

        return set;
    }

    public <K extends Enum<K> & StringRepresentable, T extends Item> ItemDefinitionSet<T, K> registerSet(Class<K> keyType, String name, String langName, BiFunction<K, Item.Properties, T> function, Function<K, Item.Properties> properties) {
        return this.registerSet(keyType, s -> s + "_" + name, langName::formatted, function, properties);
    }

    public <K extends Enum<K> & StringRepresentable, T extends Item> ItemDefinitionSet<T, K> registerSet(Class<K> keyType, UnaryOperator<String> nameFunction, UnaryOperator<String> langNameFunction, BiFunction<K, Item.Properties, T> function, Function<K, Item.Properties> properties) {
        ItemDefinitionSet<T, K> set = new ItemDefinitionSet<>(keyType);

        for (K key : keyType.getEnumConstants()) {
            String name = nameFunction.apply(key.getSerializedName());
            String langName = langNameFunction.apply(TextHelpers.toEnglishName(key.getSerializedName()));
            set.put(key, register(name, langName, () -> function.apply(key, properties.apply(key))));
        }

        return set;
    }

    public <K extends Enum<K> & StringRepresentable, T extends Item> ItemDefinitionSet<T, K> registerSet(Class<K> keyType, String name, String langName, BiFunction<K, Item.Properties, T> function, Function<K, Item.Properties> properties, UnaryOperator<ItemDefinition<T>> unaryOperator) {
        return this.registerSet(keyType, s -> s + "_" + name, langName::formatted, function, properties);
    }

    public <K extends Enum<K> & StringRepresentable, T extends Item> ItemDefinitionSet<T, K> registerSet(Class<K> keyType, UnaryOperator<String> nameFunction, UnaryOperator<String> langNameFunction, BiFunction<K, Item.Properties, T> function, Function<K, Item.Properties> properties, UnaryOperator<ItemDefinition<T>> unaryOperator) {
        ItemDefinitionSet<T, K> set = new ItemDefinitionSet<>(keyType);

        for (K key : keyType.getEnumConstants()) {
            String name = nameFunction.apply(key.getSerializedName());
            String langName = langNameFunction.apply(TextHelpers.toEnglishName(key.getSerializedName()));
            set.put(key, unaryOperator.apply(register(name, langName, () -> function.apply(key, properties.apply(key)))));
        }

        return set;
    }
}
