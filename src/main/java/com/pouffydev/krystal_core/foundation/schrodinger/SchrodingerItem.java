package com.pouffydev.krystal_core.foundation.schrodinger;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.fml.ModList;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class SchrodingerItem implements SchrodingersObject<Item> {
    public static final SchrodingerItem NULL = null;
    public final Either<ResourceLocation, Item> item;

    public SchrodingerItem(Either<ResourceLocation, Item> item) {
        this.item = item;
    }

    public static SchrodingerItem stable(Item item) {
        return new SchrodingerItem(Either.right(item));
    }

    public static SchrodingerItem unstable(ResourceLocation item) {
        return new SchrodingerItem(Either.left(item));
    }

    @Override
    public @Nullable Item get() {
        return item.map((location) -> {
            if (ModList.get().isLoaded(location.getNamespace())) {
                Optional<Holder.Reference<Item>> holder = BuiltInRegistries.ITEM.getHolder(location);
                return holder.map(Holder::value).orElse(null);
            }
            return null;
        }, (item) -> item);
    }
}
