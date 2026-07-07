package com.pouffydev.krystal_core.foundation;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.TooltipFlag;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static net.neoforged.neoforge.common.extensions.IAttributeExtension.FORMAT;
import static net.neoforged.neoforge.common.extensions.IAttributeExtension.isNullOrAddition;

public class TextHelpers {
    public static final Style hintStyle = Style.EMPTY.withColor(ChatFormatting.GREEN);
    public static final Style tooltipStyle = Style.EMPTY.withColor(ChatFormatting.BLUE);
    public static final Style negativeStyle = Style.EMPTY.withColor(ChatFormatting.RED);

    public static MutableComponent key(String key) {
        return Component.keybind(key).setStyle(hintStyle);
    }

    public static MutableComponent withFont(ResourceLocation font, Component in) {
        Style oldStyle = in.getStyle();
        return MutableComponent.create(in.getContents()).setStyle(oldStyle.withFont(font));
    }

    public static MutableComponent withColour(Component in, int colour) {
        Style oldStyle = in.getStyle();
        return MutableComponent.create(in.getContents()).setStyle(oldStyle.withColor(colour));
    }

    public static MutableComponent hint(Component in) {
        Style oldStyle = in.getStyle();
        return MutableComponent.create(in.getContents()).setStyle(oldStyle.withColor(hintStyle.getColor()));
    }

    public static MutableComponent negative(Component in) {
        Style oldStyle = in.getStyle();
        return MutableComponent.create(in.getContents()).setStyle(oldStyle.withColor(negativeStyle.getColor()));
    }

    public static MutableComponent tooltip(Component in) {
        Style oldStyle = in.getStyle();
        return MutableComponent.create(in.getContents()).setStyle(oldStyle.withColor(tooltipStyle.getColor()));
    }

    public static MutableComponent tooltip(String key, Object... args) {
        return Component.translatable(key, args).setStyle(tooltipStyle);
    }

    public static MutableComponent whenEaten() {
        return Component.translatable("ui.krystal_core.when_eaten").withStyle(ChatFormatting.GRAY);
    }

    public static MutableComponent whenDrank() {
        return Component.translatable("ui.krystal_core.when_drank").withStyle(ChatFormatting.GRAY);
    }

    public static MutableComponent whenWorn() {
        return Component.translatable("ui.krystal_core.when_worn").withStyle(ChatFormatting.GRAY);
    }

    public static MutableComponent attribute(AttributeModifier.Operation operation, Holder<Attribute> attribute, double value, TooltipFlag flag) {
        String key = value > 0 ? "neoforge.modifier.plus" : "neoforge.modifier.take";
        ChatFormatting color = attribute.value().getStyle(value > 0);

        Component attrDesc = Component.translatable(attribute.value().getDescriptionId());
        Component valueComp = toValueComponent(operation, value);
        MutableComponent comp = Component.translatable(key, valueComp, attrDesc).withStyle(color);

        return comp.append(getDebugInfo(operation, value, flag));
    }

    public static List<MutableComponent> foodEffects(FoodProperties foodProperties, float tickRate) {
        List<MutableComponent> list = new ArrayList<>();
        for (FoodProperties.PossibleEffect possibleEffect : foodProperties.effects()) {
            list.add(potion(possibleEffect.effect(), tickRate));
        }
        return list;
    }

    public static MutableComponent potion(MobEffectInstance instance, float tickRate) {
        MutableComponent result = Component.translatable(instance.getDescriptionId());
        boolean negative = !instance.getEffect().value().isBeneficial();
        if (instance.getAmplifier() > 0) {
            result = Component.translatable("potion.withAmplifier", result, Component.translatable("potion.potency." + instance.getAmplifier()));
        }

        if (instance.getDuration() > 20) {
            result = Component.translatable("potion.withDuration", result, MobEffectUtil.formatDuration(instance, 1.0f, tickRate));
        }
        return negative ? negative(result) : tooltip(result);
    }

    static MutableComponent toValueComponent(@Nullable AttributeModifier.Operation op, double value) {
        if (isNullOrAddition(op)) {
            return Component.translatable("neoforge.value.flat", FORMAT.format(value));
        }

        return Component.translatable("neoforge.value.percent", FORMAT.format(value * 100));
    }

    static Component getDebugInfo(AttributeModifier.Operation op, double value, TooltipFlag flag) {
        Component debugInfo = CommonComponents.EMPTY;

        if (flag.isAdvanced()) {
            // Advanced Tooltips show the underlying operation and the "true" value. We offset MULTIPLY_TOTAL by 1 due to how the operation is calculated.
            double advValue = (op == AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL ? 1 : 0) + value;
            String valueStr = FORMAT.format(advValue);
            String txt = switch (op) {
                case ADD_VALUE -> String.format(Locale.ROOT, advValue > 0 ? "[+%s]" : "[%s]", valueStr);
                case ADD_MULTIPLIED_BASE -> String.format(Locale.ROOT, advValue > 0 ? "[+%sx]" : "[%sx]", valueStr);
                case ADD_MULTIPLIED_TOTAL -> String.format(Locale.ROOT, "[x%s]", valueStr);
            };
            debugInfo = Component.literal(" ").append(Component.literal(txt).withStyle(ChatFormatting.GRAY));
        }
        return debugInfo;
    }

    public static String toEng(Object internalName) {
        String[] parts = internalName.toString().split("\\.");
        String toTranslate = parts[parts.length - 1];
        return toEnglishName(toTranslate);
    }

    public static String toEnglishName(Object internalName) {
        return Arrays.stream(internalName.toString().toLowerCase(Locale.ROOT).split("_"))
                .map(StringUtils::capitalize)
                .collect(Collectors.joining(" "));
    }
}
