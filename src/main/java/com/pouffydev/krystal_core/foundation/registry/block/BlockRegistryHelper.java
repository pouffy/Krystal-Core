package com.pouffydev.krystal_core.foundation.registry.block;

import com.pouffydev.krystal_core.foundation.registry.RegistryHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class BlockRegistryHelper extends RegistryHelper {

    public BlockRegistryHelper(String modId, IEventBus eventBus) {
        super(modId, eventBus);
    }

    public final DeferredRegister<Block> BLOCKS = createRegister(Registries.BLOCK);

    public <T extends Block> BlockHolderSet<T, DyeColor> registerColorEntrySet(String name, Function<DyeColor, Function<BlockBehaviour.Properties, ? extends T>> function, Function<DyeColor, BlockBehaviour.Properties> properties) {
        return this.registerSet(DyeColor.class, name, function, properties);
    }

    public <T extends Block> BlockHolderSet<T, DyeColor> registerColorEntrySet(UnaryOperator<String> name, Function<DyeColor, Function<BlockBehaviour.Properties, ? extends T>> function, Function<DyeColor, BlockBehaviour.Properties> properties) {
        return this.registerSet(DyeColor.class, name, function, properties);
    }

    public <T extends Block> BlockHolderSet<T, DyeColor> registerColorEntrySet(String name, Function<DyeColor, Function<BlockBehaviour.Properties, ? extends T>> function, Function<DyeColor, BlockBehaviour.Properties> properties, UnaryOperator<DeferredHolder<Block, T>> unaryOperator) {
        return this.registerSet(DyeColor.class, name, function, properties, unaryOperator);
    }

    public <T extends Block> BlockHolderSet<T, DyeColor> registerColorEntrySet(UnaryOperator<String> name, Function<DyeColor, Function<BlockBehaviour.Properties, ? extends T>> function, Function<DyeColor, BlockBehaviour.Properties> properties, UnaryOperator<DeferredHolder<Block, T>> unaryOperator) {
        return this.registerSet(DyeColor.class, name, function, properties, unaryOperator);
    }

    public <K extends Enum<K> & StringRepresentable, T extends Block> BlockHolderSet<T, K> registerSet(Class<K> keyType, String name, Function<K, Function<BlockBehaviour.Properties, ? extends T>> function, Function<K, BlockBehaviour.Properties> properties) {
        return this.registerSet(keyType, s -> s + "_" + name, function, properties);
    }

    public <K extends Enum<K> & StringRepresentable, T extends Block> BlockHolderSet<T, K> registerSet(Class<K> keyType, UnaryOperator<String> nameFunction, Function<K, Function<BlockBehaviour.Properties, ? extends T>> function, Function<K, BlockBehaviour.Properties> properties) {
        BlockHolderSet<T, K> set = new BlockHolderSet<>(keyType);

        for (K key : keyType.getEnumConstants()) {
            String name = nameFunction.apply(key.getSerializedName());
            set.put(key, BLOCKS.register(name, () -> function.apply(key).apply(properties.apply(key))));
        }

        return set;
    }

    public <K extends Enum<K> & StringRepresentable, T extends Block> BlockHolderSet<T, K> registerSet(Class<K> keyType, String name, Function<K, Function<BlockBehaviour.Properties, ? extends T>> function, Function<K, BlockBehaviour.Properties> properties, UnaryOperator<DeferredHolder<Block, T>> unaryOperator) {
        return this.registerSet(keyType, s -> s + "_" + name, function, properties, unaryOperator);
    }

    public <K extends Enum<K> & StringRepresentable, T extends Block> BlockHolderSet<T, K> registerSet(Class<K> keyType, UnaryOperator<String> nameFunction, Function<K, Function<BlockBehaviour.Properties, ? extends T>> function, Function<K, BlockBehaviour.Properties> properties, UnaryOperator<DeferredHolder<Block, T>> unaryOperator) {
        BlockHolderSet<T, K> set = new BlockHolderSet<>(keyType);

        for (K key : keyType.getEnumConstants()) {
            String name = nameFunction.apply(key.getSerializedName());
            set.put(key, unaryOperator.apply(BLOCKS.register(name, () -> function.apply(key).apply(properties.apply(key)))));
        }

        return set;
    }
}
