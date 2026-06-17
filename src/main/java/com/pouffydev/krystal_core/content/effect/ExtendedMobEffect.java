package com.pouffydev.krystal_core.content.effect;

import com.pouffydev.krystal_core.foundation.utility.Couple;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import javax.annotation.Nullable;
import java.util.Map;

public class ExtendedMobEffect extends MobEffect {

    public ExtendedMobEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    public Component getDisplayName(@Nullable MobEffectInstance instance) {
        MutableComponent name = instance == null ? Component.translatable(getDescriptionId()) : instance.getEffect().value().getDisplayName().copy();

        if (instance != null && instance.getAmplifier() > 0 && instance.getAmplifier() < 10)
            name.append(CommonComponents.SPACE).append(Component.translatable("enchantment.level." + (instance.getAmplifier() + 1)));

        return name;
    }

    public boolean tick(LivingEntity entity, @Nullable MobEffectInstance effectInstance, int amplifier) {
        return true;
    }

    public boolean canApply(LivingEntity entity, MobEffectInstance effectInstance) {
        return true;
    }

    public boolean canApplyOther(LivingEntity entity, MobEffectInstance otherEffectInstance) {
        return true;
    }

    public void onApplication(@Nullable MobEffectInstance effectInstance, @Nullable Entity source, LivingEntity entity, int amplifier) {}

    public MobEffectInstance onReapplication(MobEffectInstance existingEffectInstance, MobEffectInstance newEffectInstance, LivingEntity entity) {
        return existingEffectInstance;
    }

    public boolean onRemove(MobEffectInstance effectInstance, LivingEntity entity) {
        return true;
    }

    public void onExpiry(MobEffectInstance effectInstance, LivingEntity entity) {}

    public boolean shouldTickEffect(@Nullable MobEffectInstance effectInstance, @Nullable LivingEntity entity, int ticksRemaining, int amplifier) {
        return false;
    }

    @Override
    public final void addAttributeModifiers(AttributeMap attributes, int amplifier) {
        addAttributeModifiers(null, attributes, amplifier);
    }

    public void addAttributeModifiers(@Nullable LivingEntity entity, AttributeMap attributeMap, int amplifier) {
        for (Map.Entry<Holder<Attribute>, AttributeTemplate> entry : this.attributeModifiers.entrySet()) {
            final Holder<Attribute> attribute = entry.getKey();
            final AttributeInstance attributeInstance = attributeMap.getInstance(attribute);

            if (attributeInstance != null) {
                AttributeTemplate template = entry.getValue();
                AttributeModifier modifier = entry.getValue().create(amplifier);
                double dynamicAmount = getAttributeModifierValue(entity, attribute, modifier.amount(), amplifier);

                if (dynamicAmount != modifier.amount())
                    modifier = new AttributeModifier(template.id(), dynamicAmount, modifier.operation());

                attributeInstance.removeModifier(template.id());
                attributeInstance.addPermanentModifier(modifier);
            }
        }
    }

    public double getAttributeModifierValue(@Nullable LivingEntity entity, @Nullable Holder<Attribute> attribute, double baseModifierAmount, int effectAmplifier) {
        return baseModifierAmount * (effectAmplifier + 1);
    }

    public boolean beforeIncomingAttack(LivingEntity entity, MobEffectInstance effectInstance, DamageSource source, float amount) {
        return true;
    }

    public float modifyIncomingAttackDamage(LivingEntity entity, MobEffectInstance effectInstance, DamageSource source, float baseAmount) {
        return baseAmount;
    }

    public float modifyOutgoingAttackDamage(LivingEntity entity, LivingEntity target, MobEffectInstance effectInstance, DamageSource source, float baseAmount) {
        return baseAmount;
    }

    public void afterIncomingAttack(LivingEntity entity, MobEffectInstance effectInstance, DamageSource source, float amount) {}

    public void afterOutgoingAttack(LivingEntity entity, LivingEntity victim, MobEffectInstance effectInstance, DamageSource source, float amount) {}

    public boolean shouldCureEffect(MobEffectInstance effectInstance, ItemStack stack, LivingEntity entity) {
        return stack.getItem() == Items.MILK_BUCKET;
    }

    public boolean shouldBeRemovedByTotemOfDeath(MobEffectInstance effectInstance, LivingEntity entity) {
        return true;
    }

    public boolean doClientSideEffectTick(MobEffectInstance effectInstance, LivingEntity entity) {
        return false;
    }

    public void read(CompoundTag nbt, MobEffectInstance effectInstance) {}

    public void write(CompoundTag nbt, MobEffectInstance effectInstance) {}

    @Override
    public final Component getDisplayName() {
        return getDisplayName(null);
    }

    @Override
    public final boolean applyEffectTick(LivingEntity entity, int amplifier) {
        return tick(entity, null, amplifier);
    }

    @Override
    public final void applyInstantenousEffect(@Nullable Entity source, @Nullable Entity indirectSource, LivingEntity entity, int amplifier, double sourceModifier) {
        onApplication(null, source, entity, amplifier);

        if (!isInstantenous())
            tick(entity, null, amplifier);
    }

    @Override
    public final boolean shouldApplyEffectTickThisTick(int ticksRemaining, int amplifier) {
        return shouldTickEffect(null, null, ticksRemaining, amplifier);
    }

    public Couple<ResourceLocation> getInventoryBackground() {
        return Couple.create(ResourceLocation.withDefaultNamespace("container/inventory/effect_background_large"), ResourceLocation.withDefaultNamespace("container/inventory/effect_background_small"));
    }

    public Couple<ResourceLocation> getHudBackground() {
        return Couple.create(ResourceLocation.withDefaultNamespace("hud/effect_background_ambient"), ResourceLocation.withDefaultNamespace("hud/effect_background"));
    }
}
