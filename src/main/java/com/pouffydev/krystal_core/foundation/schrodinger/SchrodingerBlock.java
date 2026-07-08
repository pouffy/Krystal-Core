package com.pouffydev.krystal_core.foundation.schrodinger;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.ModList;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class SchrodingerBlock implements SchrodingersObject<Block> {
    public static final SchrodingerBlock NULL = null;
    public final Either<ResourceLocation, Block> block;

    public SchrodingerBlock(Either<ResourceLocation, Block> block) {
        this.block = block;
    }

    public static SchrodingerBlock stable(Block block) {
        return new SchrodingerBlock(Either.right(block));
    }

    public static SchrodingerBlock unstable(ResourceLocation block) {
        return new SchrodingerBlock(Either.left(block));
    }

    @Override
    public @Nullable Block get() {
        return block.map((location) -> {
            if (ModList.get().isLoaded(location.getNamespace())) {
                Optional<Holder.Reference<Block>> holder = BuiltInRegistries.BLOCK.getHolder(location);
                return holder.map(Holder::value).orElse(null);
            }
            return null;
        }, (block) -> block);
    }
}
