package com.pouffydev.krystal_core.foundation.event;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import org.jetbrains.annotations.Nullable;

public class LivingCriticalHitEvent extends LivingEvent {
    private final float vanillaDmgMultiplier;
    private final boolean isVanillaCritical;
    private final DamageSource source;
    private final @Nullable Projectile projectile;
    private float dmgMultiplier;
    private boolean isCriticalHit;
    private boolean disableSweep = true;

    public LivingCriticalHitEvent(LivingEntity entity, float vanillaDmgMultiplier, boolean isVanillaCritical, DamageSource source, Projectile projectile) {
        super(entity);
        this.vanillaDmgMultiplier = vanillaDmgMultiplier;
        this.isVanillaCritical = isVanillaCritical;
        this.projectile = projectile;
        this.source = source;
    }

    public float getDamageMultiplier() {
        return this.dmgMultiplier;
    }

    public void setDamageMultiplier(float dmgMultiplier) {
        if (dmgMultiplier < 0.0F) {
            throw new UnsupportedOperationException("Attempted to set a negative damage multiplier: " + dmgMultiplier);
        } else {
            this.dmgMultiplier = dmgMultiplier;
        }
    }

    public boolean isCriticalHit() {
        return this.isCriticalHit;
    }

    public void setCriticalHit(boolean isCriticalHit) {
        this.isCriticalHit = isCriticalHit;
    }

    public float getVanillaMultiplier() {
        return this.vanillaDmgMultiplier;
    }

    public boolean isVanillaCritical() {
        return this.isVanillaCritical;
    }

    public void setDisableSweep(boolean disableSweep) {
        this.disableSweep = disableSweep;
    }

    public boolean disableSweep() {
        return this.disableSweep;
    }

    public DamageSource getSource() {
        return this.source;
    }

    public boolean isRanged() {
        return this.projectile != null;
    }

    public Projectile getProjectile() {
        return this.projectile;
    }
}
