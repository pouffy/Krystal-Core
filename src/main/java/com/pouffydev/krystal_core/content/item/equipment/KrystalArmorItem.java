package com.pouffydev.krystal_core.content.item.equipment;

import com.google.common.collect.ImmutableMap;
import com.pouffydev.krystal_core.client.model.item.WornArmorModel;
import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class KrystalArmorItem extends ArmorItem {

    public KrystalArmorItem(Holder<ArmorMaterial> pMaterial, Type pType, Properties pProperties) {
        super(pMaterial, pType, pProperties);
    }

    public boolean beforeIncomingAttack(LivingEntity entity, DamageSource source, float amount) {
        return true;
    }

    public float modifyIncomingAttackDamage(LivingEntity entity, DamageSource source, float baseAmount) {
        return baseAmount;
    }

    public float modifyOutgoingAttackDamage(LivingEntity entity, LivingEntity target, DamageSource source, float baseAmount) {
        return baseAmount;
    }

    public void afterIncomingAttack(LivingEntity entity, DamageSource source, float amount) {}

    public void afterOutgoingAttack(LivingEntity entity, LivingEntity victim, DamageSource source, float amount) {}

    public boolean tick(LivingEntity entity) {
        return false;
    }

    public boolean clientTick(LivingEntity entity) {
        return false;
    }

    // Rendering
    public Map<EquipmentSlot, List<String>> hiddenLimbs() {
        return ImmutableMap.of();
    }

    public @Nullable WornArmorModel getModel() {
        return null;
    }
}
