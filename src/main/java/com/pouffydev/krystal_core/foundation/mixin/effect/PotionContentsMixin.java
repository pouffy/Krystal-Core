package com.pouffydev.krystal_core.foundation.mixin.effect;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.pouffydev.krystal_core.content.effect.IDamageAltering;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.alchemy.PotionContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.function.Consumer;

@Mixin(PotionContents.class)
public class PotionContentsMixin {

    @Inject(method = "addPotionTooltip(Ljava/lang/Iterable;Ljava/util/function/Consumer;FF)V", at = @At(value = "TAIL", target = "Lnet/neoforged/neoforge/common/util/AttributeUtil;addPotionTooltip(Ljava/util/List;Ljava/util/function/Consumer;)V"))
    private static void damageAltering(Iterable<MobEffectInstance> effects, Consumer<Component> tooltipAdder, float durationFactor, float ticksPerSecond, CallbackInfo ci) {
        List<Pair<Holder<Attribute>, AttributeModifier>> effectAttributes = Lists.newArrayList();
        List<MutableComponent> damageAlteredTooltips = Lists.newArrayList();
        for (MobEffectInstance mobeffectinstance : effects) {
            Holder<MobEffect> holder = mobeffectinstance.getEffect();
            holder.value().createModifiers(mobeffectinstance.getAmplifier(), (attributeHolder, modifier) -> effectAttributes.add(new Pair<>(attributeHolder, modifier)));
            if (mobeffectinstance.getEffect().value() instanceof IDamageAltering damageAltering) {
                damageAlteredTooltips.add(damageAltering.tooltipName(mobeffectinstance.getAmplifier()).withStyle(holder.value().getCategory().getTooltipFormatting()));
            }
        }
        if (!damageAlteredTooltips.isEmpty()) {
            if (effectAttributes.isEmpty()) {
                tooltipAdder.accept(CommonComponents.EMPTY);
                tooltipAdder.accept(Component.translatable("potion.whenDrank").withStyle(ChatFormatting.DARK_PURPLE));
            }
            for(var tooltip : damageAlteredTooltips) {
                tooltipAdder.accept(tooltip);
            }
        }
    }
}
