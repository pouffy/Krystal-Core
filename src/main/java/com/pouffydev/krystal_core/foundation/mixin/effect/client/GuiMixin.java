package com.pouffydev.krystal_core.foundation.mixin.effect.client;

import com.llamalad7.mixinextras.sugar.Local;
import com.pouffydev.krystal_core.content.effect.ExtendedMobEffect;
import net.minecraft.client.gui.Gui;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Gui.class)
public class GuiMixin {

    @Shadow @Final private static ResourceLocation EFFECT_BACKGROUND_AMBIENT_SPRITE;
    @Shadow @Final private static ResourceLocation EFFECT_BACKGROUND_SPRITE;

    @ModifyArg(method = "renderEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V"), index = 0)
    private ResourceLocation renderEffects(ResourceLocation sprite, @Local MobEffectInstance effect) {
        if (effect.getEffect() instanceof ExtendedMobEffect extendedMobEffect) {
            if (sprite.equals(EFFECT_BACKGROUND_AMBIENT_SPRITE)) {
                return extendedMobEffect.getHudBackground().getFirst();
            }
            if (sprite.equals(EFFECT_BACKGROUND_SPRITE)) {
                return extendedMobEffect.getHudBackground().getSecond();
            }
        }
        return sprite;
    }
}
