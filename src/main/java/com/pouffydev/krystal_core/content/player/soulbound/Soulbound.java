package com.pouffydev.krystal_core.content.player.soulbound;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.pouffydev.krystal_core.foundation.TextHelpers;
import net.minecraft.ChatFormatting;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.function.Consumer;

public record Soulbound(boolean showInTooltip, int color) implements TooltipProvider {
    public static final Codec<Soulbound> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.optionalFieldOf("show_in_tooltip", true).forGetter(Soulbound::showInTooltip),
            Codec.INT.optionalFieldOf("color", 0xABCECC).forGetter(Soulbound::color)
    ).apply(instance, Soulbound::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, Soulbound> STREAM_CODEC;

    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> consumer, TooltipFlag flag) {
        consumer.accept(TextHelpers.withColour(Component.translatable("ui.krystal_core.soulbound").withStyle(ChatFormatting.ITALIC), color));
    }

    static {
        STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.BOOL, Soulbound::showInTooltip,
                ByteBufCodecs.INT, Soulbound::color,
                Soulbound::new
        );
    }
}
