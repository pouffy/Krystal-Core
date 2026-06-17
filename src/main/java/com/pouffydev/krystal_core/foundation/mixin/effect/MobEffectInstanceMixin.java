package com.pouffydev.krystal_core.foundation.mixin.effect;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.pouffydev.krystal_core.content.effect.ExtendedMobEffect;
import com.pouffydev.krystal_core.content.effect.ExtendedMobEffectHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MobEffectInstance.class)
public class MobEffectInstanceMixin implements ExtendedMobEffectHolder {

    @Unique
    Object krystalCore$data;

    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/effect/MobEffect;applyEffectTick(Lnet/minecraft/world/entity/LivingEntity;I)Z"))
    private boolean onEffectTick(MobEffect effect, LivingEntity entity, int amplifier, Operation<Boolean> original) {
        if (effect instanceof ExtendedMobEffect extendedEffect)
            return extendedEffect.tick(entity, (MobEffectInstance)(Object)this, amplifier);

        return original.call(effect, entity, amplifier);
    }

    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/effect/MobEffect;shouldApplyEffectTickThisTick(II)Z"))
    private boolean checkEffectTick(MobEffect effect, int duration, int amplifier, Operation<Boolean> original, @Local(argsOnly = true) LivingEntity entity) {
        if (!(effect instanceof ExtendedMobEffect extendedEffect))
            return original.call(effect, duration, amplifier);
        return extendedEffect.shouldTickEffect((MobEffectInstance)(Object)this, entity, duration, amplifier);
    }

    @Override
    public Object krystalCore$getExtendedMobEffectData() {
        return this.krystalCore$data;
    }

    @Override
    public void krystalCore$setExtendedMobEffectData(Object data) {
        this.krystalCore$data = data;
    }
}
