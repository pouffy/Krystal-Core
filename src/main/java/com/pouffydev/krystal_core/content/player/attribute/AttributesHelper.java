package com.pouffydev.krystal_core.content.player.attribute;

import com.pouffydev.krystal_core.content.KrystalAttachmentTypes;
import com.pouffydev.krystal_core.content.KrystalAttributes;
import com.pouffydev.krystal_core.core.KCTags;
import com.pouffydev.krystal_core.foundation.event.LivingCriticalHitEvent;
import net.minecraft.core.Holder;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class AttributesHelper {
    private final LivingEntity owner;

    private AttributesHelper(LivingEntity owner) {
        this.owner = owner;
    }

    public static AttributesHelper create(LivingEntity owner) {
        return new AttributesHelper(owner);
    }

    public double getAttributeSafe(Holder<Attribute> attribute, double fallback) {
        if (this.owner.getAttributes().hasAttribute(attribute)) {
            return this.owner.getAttributeValue(attribute);
        }
        return fallback;
    }

    @SafeVarargs
    private double sumAttributes(Holder<Attribute>... attributes) {
        double sum = 0.0;
        for (Holder<Attribute> attribute : attributes) {
            sum += getAttributeSafe(attribute, 0);
        }
        return sum;
    }

    public float useDuration(ItemStack stack, int ticks) {
        if (stack.is(KCTags.Items.USE_DURATION.tag())) {
            float speedMultiplier = (float) getAttributeSafe(KrystalAttributes.DRAW_SPEED, 1.0f);
            return speedMultiplier != 1 ? ticks * (1+(1-speedMultiplier)) : ticks;
        }
        return ticks;
    }

    public void projectileFired(Projectile proj) {
        var damageAttribute = KrystalAttributes.RANGED_DAMAGE;
        var critAttribute = KrystalAttributes.RANGED_CRIT_CHANCE;
        // Magical Projectiles should use magic-specific attributes. Not ranged.
        if (proj.getType().is(KCTags.Entities.MAGIC_PROJECTILE.tag())) {
            damageAttribute = KrystalAttributes.MAGIC_DAMAGE;
            critAttribute = KrystalAttributes.MAGIC_CRIT_CHANCE;
        }

        proj.setData(KrystalAttachmentTypes.DAMAGE_MULTIPLIER, getAttributeSafe(damageAttribute, 1.0f));
        proj.setData(KrystalAttachmentTypes.CRIT_CHANCE, KrystalAttributes.rangedCritChance(this.owner, proj));
        var velocity = proj.getDeltaMovement().scale(getAttributeSafe(KrystalAttributes.PROJECTILE_VELOCITY, 1.0f));
        proj.setDeltaMovement(velocity);
    }

    public float modifyProjectileKnockback(float original) {
        double knockback = getAttributeSafe(KrystalAttributes.PROJECTILE_KNOCKBACK, 1.0f);
        return (float) (original * knockback);
    }

    public static void incomingDamage(LivingIncomingDamageEvent event) {
        Entity directCause = event.getSource().getDirectEntity();
        Entity cause = event.getSource().getEntity();
        double multiplier = 1.0;
        if (directCause != null) {
            float damageMulti = directCause.getData(KrystalAttachmentTypes.DAMAGE_MULTIPLIER).floatValue();
            if (damageMulti != 1.0) {
                event.setAmount(event.getAmount() * damageMulti);
            }
            float critChance = directCause.getData(KrystalAttachmentTypes.CRIT_CHANCE).floatValue();
            boolean isCrit = critChance <= directCause.getRandom().nextFloat();

            if (cause instanceof LivingEntity living && directCause instanceof Projectile projectile) {
                var critEvent = NeoForge.EVENT_BUS.post(new LivingCriticalHitEvent(living, isCrit ? 1.5F : 1.0F, isCrit, null, projectile));
                if (critEvent.isCriticalHit()) {
                    event.setAmount(event.getAmount() * critEvent.getDamageMultiplier());
                }
            } else {
                if (isCrit) {
                    event.setAmount(event.getAmount() * 2);
                }
            }
            if (directCause instanceof OwnableEntity ownable) {
                LivingEntity owner = ownable.getOwner();
                AttributesHelper helper = create(owner);
                double summonDamage = helper.getAttributeSafe(KrystalAttributes.SUMMON_DAMAGE, 1.0);
                multiplier *= summonDamage;
            }
        }
        if (cause instanceof LivingEntity living) {
            AttributesHelper helper = create(living);
            if (event.getSource().is(KCTags.Damage.MELEE.tag())) {
                multiplier *= helper.getAttributeSafe(KrystalAttributes.MELEE_DAMAGE, 1.0);
            }
            if (event.getSource().is(KCTags.Damage.MAGIC.tag())) {
                multiplier *= helper.getAttributeSafe(KrystalAttributes.MAGIC_DAMAGE, 1.0);
            }
        }
        float result = (float) (event.getAmount() * multiplier);
        event.setAmount(result);
    }

    public static void entityAdded(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Projectile proj) {
            if (proj.getOwner() instanceof LivingEntity user) {
                AttributesHelper helper = create(user);
                helper.projectileFired(proj);
            }
        }
    }
}
