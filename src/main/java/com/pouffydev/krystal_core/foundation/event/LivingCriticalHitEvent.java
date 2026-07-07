package com.pouffydev.krystal_core.foundation.event;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import org.jetbrains.annotations.Nullable;

@Getter @Setter
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
}
