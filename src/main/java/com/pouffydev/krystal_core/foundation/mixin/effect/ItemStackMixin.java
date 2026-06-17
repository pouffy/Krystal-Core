package com.pouffydev.krystal_core.foundation.mixin.effect;

import com.pouffydev.krystal_core.content.effect.ExtendedMobEffect;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentHolder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @Inject(method = "finishUsingItem", at = @At("HEAD"))
    public void onItemUseFinish(Level level, LivingEntity entity, CallbackInfoReturnable<ItemStack> cir) {
        if (!level.isClientSide && ((DataComponentHolder)this).has(DataComponents.FOOD) && !entity.getActiveEffectsMap().isEmpty())
            krystalCore$checkCustomCuring(entity, (ItemStack)(Object)this);
    }

    @Unique
    private static void krystalCore$checkCustomCuring(LivingEntity entity, ItemStack stack) {
        final Set<Holder<MobEffect>> removingEffects = new ObjectOpenHashSet<>();
        for (MobEffectInstance effect : entity.getActiveEffects()) {
            if (effect.getEffect().value() instanceof ExtendedMobEffect extendedEffect && extendedEffect.shouldCureEffect(effect, stack, entity))
                removingEffects.add(effect.getEffect());
        }
        for (Holder<MobEffect> effect : removingEffects) {
            entity.removeEffect(effect);
        }
    }
}
