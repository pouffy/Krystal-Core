package com.pouffydev.krystal_core.content.item.tool;

import com.pouffydev.krystal_core.foundation.TextHelpers;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SignApplicator;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.SignText;

import java.util.List;

public class FontChangingItem extends Item implements SignApplicator {
    private final ResourceLocation font;
    private final String fontDisplay;

    public FontChangingItem(Properties properties, ResourceLocation font, String fontDisplay) {
        super(properties);
        this.font = font;
        this.fontDisplay = fontDisplay;
    }

    public FontChangingItem(Properties properties, ResourceLocation font) {
        this(properties, font, "LOREM IPSUM");
    }

    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        Component grimspeak = TextHelpers.hint(TextHelpers.withFont(font, Component.literal(fontDisplay)));
        Component useKey = Component.keybind("key.use");
        Component desc = TextHelpers.tooltip("item.krystal_core.font_changing.description", TextHelpers.hint(useKey), grimspeak);
        tooltipComponents.add(desc);
    }

    public SoundEvent useSound() {
        return SoundEvents.VILLAGER_WORK_CARTOGRAPHER;
    }

    @Override
    public boolean tryApplyToSign(Level level, SignBlockEntity sign, boolean front, Player player) {
        if (sign.updateText(text -> changeFont(text, this.font), front)) {
            level.playSound(null, sign.getBlockPos(), useSound(), SoundSource.BLOCKS, 1.0f, 1.0f);
            return true;
        }
        return false;
    }

    private static final ResourceLocation DEFAULT_FONT = Component.empty().getStyle().getFont();

    public SignText changeFont(SignText text, ResourceLocation itemFont) {
        for (int i = 0; i < 4; i++) {
            Component message = text.getMessage(i, false);
            if (message.getString().isEmpty()) continue;
            ResourceLocation currentFont = message.getStyle().getFont();

            ResourceLocation font;
            if (currentFont.equals(itemFont)) font = DEFAULT_FONT;
            else font = itemFont;

            text = text.setMessage(i, text.getMessage(i, false).copy().withStyle(t -> t.withFont(font)));
        }
        return text;
    }

    public boolean canApplyToSign(SignText text, Player player) {
        int counter = 0;
        for (int i = 0; i < 4; i++) {
            Component message = text.getMessage(i, false);
            if (message.getString().isEmpty()) continue;
            ResourceLocation currentFont = message.getStyle().getFont();
            if (currentFont.equals(this.font)) counter++;
        }
        boolean allTextIsFont = counter >= 4;
        return text.hasMessage(player) && !allTextIsFont;
    }
}
