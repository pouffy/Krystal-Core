package com.pouffydev.krystal_core.content.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

public record UseRemainder(ItemStack remainder) {
    public static final Codec<UseRemainder> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ItemStack.CODEC.fieldOf("stack").forGetter(UseRemainder::remainder)
    ).apply(instance, UseRemainder::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, UseRemainder> STREAM_CODEC;

    static {
        STREAM_CODEC = StreamCodec.composite(
                ItemStack.STREAM_CODEC, UseRemainder::remainder,
                UseRemainder::new
        );
    }
}
