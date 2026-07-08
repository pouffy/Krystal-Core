package com.pouffydev.krystal_core.foundation.registry.definition.block;

import com.pouffydev.krystal_core.foundation.TextHelpers;
import com.pouffydev.krystal_core.foundation.registry.RegistryHelper;
import com.pouffydev.krystal_core.foundation.registry.RegistryManager;
import com.pouffydev.krystal_core.foundation.registry.definition.item.ItemRegistryHelper;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class BlockRegistryHelper extends RegistryHelper<Block> {
    public final List<BlockDefinition<?>> BLOCK_DEFINITIONS;
    public final ItemRegistryHelper itemRegistryHelper;

    public BlockRegistryHelper(ItemRegistryHelper itemRegistryHelper) {
        this.itemRegistryHelper = itemRegistryHelper;
        this.BLOCK_DEFINITIONS = new ArrayList<>();
    }

    public final DeferredRegister.Blocks BLOCKS = RegistryManager.getInstance().createBlocks();

    public <T extends Block> BlockDefinition<T> registerNoItem(String name, Supplier<T> block, BlockProperties properties) {
        DeferredBlock<T> deferred = BLOCKS.register(name, block);
        BlockDefinition<T> definition = BlockDefinition.fromHolder(deferred, properties);
        BLOCK_DEFINITIONS.add(definition);
        return definition;
    }

    public <T extends Block> BlockDefinition<T> registerNoItem(String name, Supplier<T> block) {
        return registerNoItem(name, block, BlockProperties.custom(""));
    }

    public <T extends Block> BlockDefinition<T> registerNoItem(String name, Supplier<T> block, String customLang) {
        return registerNoItem(name, block, BlockProperties.custom(customLang));
    }

    public <T extends Block> BlockDefinition<T> register(String name, Supplier<T> block, BlockProperties properties) {
        BlockDefinition<T> definition = registerNoItem(name, block, properties);
        itemRegistryHelper.register(name, () -> new BlockItem(definition.get(), new Item.Properties()));
        return definition;
    }

    public <T extends Block> BlockDefinition<T> register(String name, Supplier<T> block) {
        return register(name, block, BlockProperties.custom(""));
    }

    public <T extends Block> BlockDefinition<T> register(String name, Supplier<T> block, String customLang) {
        return register(name, block, BlockProperties.custom(customLang));
    }

    public <T extends Block> BlockDefinitionSet<T, DyeColor> registerColorEntrySet(String name, Function<DyeColor, Function<BlockBehaviour.Properties, ? extends T>> function, Function<DyeColor, BlockBehaviour.Properties> properties) {
        return this.registerSet(DyeColor.class, name, function, properties);
    }

    public <T extends Block> BlockDefinitionSet<T, DyeColor> registerColorEntrySet(UnaryOperator<String> name, Function<DyeColor, Function<BlockBehaviour.Properties, ? extends T>> function, Function<DyeColor, BlockBehaviour.Properties> properties) {
        return this.registerSet(DyeColor.class, name, function, properties);
    }

    public <T extends Block> BlockDefinitionSet<T, DyeColor> registerColorEntrySet(String name, Function<DyeColor, Function<BlockBehaviour.Properties, ? extends T>> function, Function<DyeColor, BlockBehaviour.Properties> properties, UnaryOperator<BlockDefinition<T>> unaryOperator) {
        return this.registerSet(DyeColor.class, name, function, properties, unaryOperator);
    }

    public <T extends Block> BlockDefinitionSet<T, DyeColor> registerColorEntrySet(UnaryOperator<String> name, Function<DyeColor, Function<BlockBehaviour.Properties, ? extends T>> function, Function<DyeColor, BlockBehaviour.Properties> properties, UnaryOperator<BlockDefinition<T>> unaryOperator) {
        return this.registerSet(DyeColor.class, name, function, properties, unaryOperator);
    }

    public <T extends Block> BlockDefinitionSet<T, DyeColor> registerColorEntrySetNoItem(String name, Function<DyeColor, Function<BlockBehaviour.Properties, ? extends T>> function, Function<DyeColor, BlockBehaviour.Properties> properties) {
        return this.registerSetNoItem(DyeColor.class, name, function, properties);
    }

    public <T extends Block> BlockDefinitionSet<T, DyeColor> registerColorEntrySetNoItem(UnaryOperator<String> name, Function<DyeColor, Function<BlockBehaviour.Properties, ? extends T>> function, Function<DyeColor, BlockBehaviour.Properties> properties) {
        return this.registerSetNoItem(DyeColor.class, name, function, properties);
    }

    public <T extends Block> BlockDefinitionSet<T, DyeColor> registerColorEntrySetNoItem(String name, Function<DyeColor, Function<BlockBehaviour.Properties, ? extends T>> function, Function<DyeColor, BlockBehaviour.Properties> properties, UnaryOperator<BlockDefinition<T>> unaryOperator) {
        return this.registerSetNoItem(DyeColor.class, name, function, properties, unaryOperator);
    }

    public <T extends Block> BlockDefinitionSet<T, DyeColor> registerColorEntrySetNoItem(UnaryOperator<String> name, Function<DyeColor, Function<BlockBehaviour.Properties, ? extends T>> function, Function<DyeColor, BlockBehaviour.Properties> properties, UnaryOperator<BlockDefinition<T>> unaryOperator) {
        return this.registerSetNoItem(DyeColor.class, name, function, properties, unaryOperator);
    }

    public <K extends Enum<K> & StringRepresentable, T extends Block> BlockDefinitionSet<T, K> registerSet(Class<K> keyType, String name, Function<K, Function<BlockBehaviour.Properties, ? extends T>> function, Function<K, BlockBehaviour.Properties> properties) {
        return this.registerSet(keyType, s -> s + "_" + name, function, properties);
    }

    public <K extends Enum<K> & StringRepresentable, T extends Block> BlockDefinitionSet<T, K> registerSet(Class<K> keyType, UnaryOperator<String> nameFunction, Function<K, Function<BlockBehaviour.Properties, ? extends T>> function, Function<K, BlockBehaviour.Properties> properties) {
        BlockDefinitionSet<T, K> set = new BlockDefinitionSet<>(keyType);

        for (K key : keyType.getEnumConstants()) {
            String name = nameFunction.apply(key.getSerializedName());
            set.put(key, register(name, () -> function.apply(key).apply(properties.apply(key))));
        }

        return set;
    }

    public <K extends Enum<K> & StringRepresentable, T extends Block> BlockDefinitionSet<T, K> registerSet(Class<K> keyType, String name, Function<K, Function<BlockBehaviour.Properties, ? extends T>> function, Function<K, BlockBehaviour.Properties> properties, UnaryOperator<BlockDefinition<T>> unaryOperator) {
        return this.registerSet(keyType, s -> s + "_" + name, function, properties, unaryOperator);
    }

    public <K extends Enum<K> & StringRepresentable, T extends Block> BlockDefinitionSet<T, K> registerSet(Class<K> keyType, UnaryOperator<String> nameFunction, Function<K, Function<BlockBehaviour.Properties, ? extends T>> function, Function<K, BlockBehaviour.Properties> properties, UnaryOperator<BlockDefinition<T>> unaryOperator) {
        BlockDefinitionSet<T, K> set = new BlockDefinitionSet<>(keyType);

        for (K key : keyType.getEnumConstants()) {
            String name = nameFunction.apply(key.getSerializedName());
            set.put(key, unaryOperator.apply(register(name, () -> function.apply(key).apply(properties.apply(key)))));
        }

        return set;
    }

    public <K extends Enum<K> & StringRepresentable, T extends Block> BlockDefinitionSet<T, K> registerSet(Class<K> keyType, String name, String langName, Function<K, Function<BlockBehaviour.Properties, ? extends T>> function, Function<K, BlockBehaviour.Properties> properties) {
        return this.registerSet(keyType, s -> s + "_" + name, langName::formatted, function, properties);
    }

    public <K extends Enum<K> & StringRepresentable, T extends Block> BlockDefinitionSet<T, K> registerSet(Class<K> keyType, UnaryOperator<String> nameFunction, UnaryOperator<String> langNameFunction, Function<K, Function<BlockBehaviour.Properties, ? extends T>> function, Function<K, BlockBehaviour.Properties> properties) {
        BlockDefinitionSet<T, K> set = new BlockDefinitionSet<>(keyType);

        for (K key : keyType.getEnumConstants()) {
            String name = nameFunction.apply(key.getSerializedName());
            String langName = langNameFunction.apply(TextHelpers.toEnglishName(key.getSerializedName()));
            set.put(key, register(name, () -> function.apply(key).apply(properties.apply(key)), langName));
        }

        return set;
    }

    public <K extends Enum<K> & StringRepresentable, T extends Block> BlockDefinitionSet<T, K> registerSet(Class<K> keyType, String name, String langName, Function<K, Function<BlockBehaviour.Properties, ? extends T>> function, Function<K, BlockBehaviour.Properties> properties, UnaryOperator<BlockDefinition<T>> unaryOperator) {
        return this.registerSet(keyType, s -> s + "_" + name, langName::formatted, function, properties);
    }

    public <K extends Enum<K> & StringRepresentable, T extends Block> BlockDefinitionSet<T, K> registerSet(Class<K> keyType, UnaryOperator<String> nameFunction, UnaryOperator<String> langNameFunction, Function<K, Function<BlockBehaviour.Properties, ? extends T>> function, Function<K, BlockBehaviour.Properties> properties, UnaryOperator<BlockDefinition<T>> unaryOperator) {
        BlockDefinitionSet<T, K> set = new BlockDefinitionSet<>(keyType);

        for (K key : keyType.getEnumConstants()) {
            String name = nameFunction.apply(key.getSerializedName());
            String langName = langNameFunction.apply(TextHelpers.toEnglishName(key.getSerializedName()));
            set.put(key, unaryOperator.apply(register(name, () -> function.apply(key).apply(properties.apply(key)), langName)));
        }

        return set;
    }

    public <K extends Enum<K> & StringRepresentable, T extends Block> BlockDefinitionSet<T, K> registerSetNoItem(Class<K> keyType, String name, Function<K, Function<BlockBehaviour.Properties, ? extends T>> function, Function<K, BlockBehaviour.Properties> properties) {
        return this.registerSetNoItem(keyType, s -> s + "_" + name, function, properties);
    }

    public <K extends Enum<K> & StringRepresentable, T extends Block> BlockDefinitionSet<T, K> registerSetNoItem(Class<K> keyType, UnaryOperator<String> nameFunction, Function<K, Function<BlockBehaviour.Properties, ? extends T>> function, Function<K, BlockBehaviour.Properties> properties) {
        BlockDefinitionSet<T, K> set = new BlockDefinitionSet<>(keyType);

        for (K key : keyType.getEnumConstants()) {
            String name = nameFunction.apply(key.getSerializedName());
            set.put(key, registerNoItem(name, () -> function.apply(key).apply(properties.apply(key))));
        }

        return set;
    }

    public <K extends Enum<K> & StringRepresentable, T extends Block> BlockDefinitionSet<T, K> registerSetNoItem(Class<K> keyType, String name, Function<K, Function<BlockBehaviour.Properties, ? extends T>> function, Function<K, BlockBehaviour.Properties> properties, UnaryOperator<BlockDefinition<T>> unaryOperator) {
        return this.registerSetNoItem(keyType, s -> s + "_" + name, function, properties, unaryOperator);
    }

    public <K extends Enum<K> & StringRepresentable, T extends Block> BlockDefinitionSet<T, K> registerSetNoItem(Class<K> keyType, UnaryOperator<String> nameFunction, Function<K, Function<BlockBehaviour.Properties, ? extends T>> function, Function<K, BlockBehaviour.Properties> properties, UnaryOperator<BlockDefinition<T>> unaryOperator) {
        BlockDefinitionSet<T, K> set = new BlockDefinitionSet<>(keyType);

        for (K key : keyType.getEnumConstants()) {
            String name = nameFunction.apply(key.getSerializedName());
            set.put(key, unaryOperator.apply(registerNoItem(name, () -> function.apply(key).apply(properties.apply(key)))));
        }

        return set;
    }

    public <K extends Enum<K> & StringRepresentable, T extends Block> BlockDefinitionSet<T, K> registerSetNoItem(Class<K> keyType, String name, String langName, Function<K, Function<BlockBehaviour.Properties, ? extends T>> function, Function<K, BlockBehaviour.Properties> properties) {
        return this.registerSetNoItem(keyType, s -> s + "_" + name, langName::formatted, function, properties);
    }

    public <K extends Enum<K> & StringRepresentable, T extends Block> BlockDefinitionSet<T, K> registerSetNoItem(Class<K> keyType, UnaryOperator<String> nameFunction, UnaryOperator<String> langNameFunction, Function<K, Function<BlockBehaviour.Properties, ? extends T>> function, Function<K, BlockBehaviour.Properties> properties) {
        BlockDefinitionSet<T, K> set = new BlockDefinitionSet<>(keyType);

        for (K key : keyType.getEnumConstants()) {
            String name = nameFunction.apply(key.getSerializedName());
            String langName = langNameFunction.apply(TextHelpers.toEnglishName(key.getSerializedName()));
            set.put(key, registerNoItem(name, () -> function.apply(key).apply(properties.apply(key)), langName));
        }

        return set;
    }

    public <K extends Enum<K> & StringRepresentable, T extends Block> BlockDefinitionSet<T, K> registerSetNoItem(Class<K> keyType, String name, String langName, Function<K, Function<BlockBehaviour.Properties, ? extends T>> function, Function<K, BlockBehaviour.Properties> properties, UnaryOperator<BlockDefinition<T>> unaryOperator) {
        return this.registerSetNoItem(keyType, s -> s + "_" + name, langName::formatted, function, properties, unaryOperator);
    }

    public <K extends Enum<K> & StringRepresentable, T extends Block> BlockDefinitionSet<T, K> registerSetNoItem(Class<K> keyType, UnaryOperator<String> nameFunction, UnaryOperator<String> langNameFunction, Function<K, Function<BlockBehaviour.Properties, ? extends T>> function, Function<K, BlockBehaviour.Properties> properties, UnaryOperator<BlockDefinition<T>> unaryOperator) {
        BlockDefinitionSet<T, K> set = new BlockDefinitionSet<>(keyType);

        for (K key : keyType.getEnumConstants()) {
            String name = nameFunction.apply(key.getSerializedName());
            String langName = langNameFunction.apply(TextHelpers.toEnglishName(key.getSerializedName()));
            set.put(key, unaryOperator.apply(registerNoItem(name, () -> function.apply(key).apply(properties.apply(key)), langName)));
        }

        return set;
    }
}
