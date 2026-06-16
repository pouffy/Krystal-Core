package com.pouffydev.krystal_core.core.event;

import com.pouffydev.krystal_core.content.KrystalAttributes;
import com.pouffydev.krystal_core.content.player.attribute.AttributesHelper;
import com.pouffydev.krystal_core.foundation.event.LivingCriticalHitEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.TridentItem;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class AttributeEvents {

    private boolean canBenefitFromDrawSpeed(ItemStack stack) {
        return stack.getItem() instanceof ProjectileWeaponItem || stack.getItem() instanceof TridentItem;
    }

    @SubscribeEvent
    public void drawSpeed(LivingEntityUseItemEvent.Tick event) {
        if (event.getEntity() instanceof Player player) {
            AttributesHelper helper = AttributesHelper.create(player);
            double speed = helper.getAttributeSafe(KrystalAttributes.DRAW_SPEED, 0) - 1;
            if (speed == 0 || !this.canBenefitFromDrawSpeed(event.getItem())) return;

            int offset = -1;
            if (speed < 0) {
                offset = 1;
                speed = -speed;
            }

            while (speed > 1) {
                event.setDuration(event.getDuration() + offset);
                speed--;
            }
            if (speed > 0.5F) {
                if (event.getEntity().tickCount % 2 == 0) event.setDuration(event.getDuration() + offset);
                speed -= 0.5F;
            }
            int mod = (int) Math.floor(1 / Math.min(1, speed));
            if (event.getEntity().tickCount % mod == 0) event.setDuration(event.getDuration() + offset);
            speed--;
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void criticalStrike(LivingIncomingDamageEvent event) {
        LivingEntity attacker = event.getSource().getEntity() instanceof LivingEntity living ? living : null;
        if (attacker == null) return;

        double critChance = KrystalAttributes.nonRangedCritChance(attacker, event.getSource());

        RandomSource rand = event.getEntity().getRandom();
        float damage = event.getAmount();
        boolean isCrit = rand.nextFloat() <= critChance;

        var critEvent = NeoForge.EVENT_BUS.post(new LivingCriticalHitEvent(attacker, isCrit ? 1.5F : 1.0F, isCrit, event.getSource(), null));
        if (critEvent.isCriticalHit()) {
            damage *= critEvent.getDamageMultiplier();
        }

        event.setAmount(damage);
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void critDamage(LivingCriticalHitEvent event) {
        var projectile = event.getProjectile();
        float damage = projectile != null ? KrystalAttributes.rangedCritDamage(event.getEntity(), projectile) : KrystalAttributes.nonRangedCritDamage(event.getEntity(), event.getSource());
        if (event.isVanillaCritical()) {
            event.setDamageMultiplier(Math.max(event.getDamageMultiplier(), damage));
        }
    }
}
