package com.pouffydev.krystal_core.foundation.mixin.effect.client;

import com.llamalad7.mixinextras.sugar.Local;
import com.pouffydev.krystal_core.content.effect.ExtendedMobEffect;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(EffectRenderingInventoryScreen.class)
public class EffectRenderingInventoryScreenMixin {
    @Shadow private static final ResourceLocation EFFECT_BACKGROUND_LARGE_SPRITE = ResourceLocation.withDefaultNamespace("container/inventory/effect_background_large");
    @Shadow private static final ResourceLocation EFFECT_BACKGROUND_SMALL_SPRITE = ResourceLocation.withDefaultNamespace("container/inventory/effect_background_small");

    @Inject(method = "getEffectName", at = @At(value = "HEAD"), cancellable = true)
    private void getEffectName(MobEffectInstance effectInstance, CallbackInfoReturnable<Component> callback) {
        if (effectInstance.getEffect().value() instanceof ExtendedMobEffect extendedEffect)
            callback.setReturnValue(extendedEffect.getDisplayName(effectInstance));
    }

    @ModifyArg(method = "renderBackgrounds", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V"), index = 0)
    private ResourceLocation renderBackgrounds(ResourceLocation sprite, @Local MobEffectInstance effect, @Local(argsOnly = true) Iterable<MobEffectInstance> effects) {
        List<Holder<MobEffect>> effects2 = new ArrayList<>();
        for (MobEffectInstance instance : effects) {
            effects2.add(instance.getEffect());
        }
        if (effect.getEffect() instanceof ExtendedMobEffect extendedMobEffect) {
            if (sprite.equals(EFFECT_BACKGROUND_LARGE_SPRITE)) {
                return extendedMobEffect.getInventoryBackground().getFirst();
            }
            if (sprite.equals(EFFECT_BACKGROUND_SMALL_SPRITE)) {
                return extendedMobEffect.getInventoryBackground().getSecond();
            }
        }
        return sprite;
    }
}
