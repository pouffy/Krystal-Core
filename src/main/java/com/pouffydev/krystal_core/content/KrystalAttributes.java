package com.pouffydev.krystal_core.content;

import com.pouffydev.krystal_core.KrystalCore;
import com.pouffydev.krystal_core.content.player.attribute.AttributesHelper;
import com.pouffydev.krystal_core.core.KCTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.projectile.Projectile;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashMap;
import java.util.UUID;
import java.util.function.Function;

public class KrystalAttributes {
    public static final HashMap<DeferredHolder<Attribute, Attribute>, UUID> UUIDS = new HashMap<>();
    public static final DeferredRegister<Attribute> ATTRIBUTES = KrystalCore.getRegistryHelper().createRegister(Registries.ATTRIBUTE);

    //Critical Strike
    public static final DeferredHolder<Attribute, Attribute> MELEE_CRIT_CHANCE = registerAttribute("critical_strike_chance.melee", (id) -> new RangedAttribute("attribute.name.krystal_core.melee_critical_strike_chance", 0.04F, 0.0, 1024.0).setSyncable(true), "9ec6dddd-804a-4db6-becd-757961b8b2d7");
    public static final DeferredHolder<Attribute, Attribute> RANGED_CRIT_CHANCE = registerAttribute("critical_strike_chance.ranged", (id) -> new RangedAttribute("attribute.name.krystal_core.ranged_critical_strike_chance", 0.04F, 0.0, 1024.0).setSyncable(true), "fa7bc11c-8325-43ae-8831-ddbd4b9dcc7c");
    public static final DeferredHolder<Attribute, Attribute> MAGIC_CRIT_CHANCE = registerAttribute("critical_strike_chance.magic", (id) -> new RangedAttribute("attribute.name.krystal_core.magic_critical_strike_chance", 0.04F, 0.0, 1024.0).setSyncable(true), "86967424-e13b-44ca-b8c6-5b31131f7855");
    public static final DeferredHolder<Attribute, Attribute> GLOBAL_CRIT_CHANCE = registerAttribute("critical_strike_chance", (id) -> new RangedAttribute("attribute.name.krystal_core.critical_strike_chance", 0.04F, 0.0, 1024.0).setSyncable(true), "0f0a61b7-046e-4cb6-ad69-d5daa5f9d4db");

    //Critical Damage
    public static final DeferredHolder<Attribute, Attribute> MELEE_CRIT_DAMAGE = registerAttribute("critical_strike_damage.melee", (id) -> new RangedAttribute("attribute.name.krystal_core.melee_critical_strike_damage", 1.5, 0.0, 1024.0).setSyncable(true), "9fa9c88c-6c97-4904-aa24-00451117ab04");
    public static final DeferredHolder<Attribute, Attribute> RANGED_CRIT_DAMAGE = registerAttribute("critical_strike_damage.ranged", (id) -> new RangedAttribute("attribute.name.krystal_core.ranged_critical_strike_damage", 1.5, 0.0, 1024.0).setSyncable(true), "34168037-d4a3-4952-9487-815472a4d985");
    public static final DeferredHolder<Attribute, Attribute> MAGIC_CRIT_DAMAGE = registerAttribute("critical_strike_damage.magic", (id) -> new RangedAttribute("attribute.name.krystal_core.magic_critical_strike_damage", 1.5, 0.0, 1024.0).setSyncable(true), "964d0de7-4b25-46d7-a0b0-66f0fcef4f3a");
    public static final DeferredHolder<Attribute, Attribute> GLOBAL_CRIT_DAMAGE = registerAttribute("critical_strike_damage", (id) -> new RangedAttribute("attribute.name.krystal_core.critical_strike_damage", 1.5, 0.0, 1024.0).setSyncable(true), "6689de48-e7dc-4a67-bb07-f50133e96507");

    //Damage
    public static final DeferredHolder<Attribute, Attribute> MELEE_DAMAGE = registerAttribute("damage.melee", (id) -> new RangedAttribute("attribute.name.krystal_core.melee_damage", 1.0F, 0.0, 1024.0).setSyncable(true), "ee7dcc87-87db-4ef5-b0b1-25ae43ca7f35");
    public static final DeferredHolder<Attribute, Attribute> RANGED_DAMAGE = registerAttribute("damage.ranged", (id) -> new RangedAttribute("attribute.name.krystal_core.ranged_damage", 1.0F, 0.0, 1024.0).setSyncable(true), "0890bcd4-d79e-455b-b8ba-05014a213a76");
    public static final DeferredHolder<Attribute, Attribute> MAGIC_DAMAGE = registerAttribute("damage.magic", (id) -> new RangedAttribute("attribute.name.krystal_core.magic_damage", 1.0F, 0.0, 1024.0).setSyncable(true), "ee07f84f-ea40-4fd0-9ecb-a767713d0128");
    public static final DeferredHolder<Attribute, Attribute> SUMMON_DAMAGE = registerAttribute("damage.summon", (id) -> new RangedAttribute("attribute.name.krystal_core.summon_damage", 1.0F, 0.0, 1024.0).setSyncable(true), "7dd4121e-6589-4bc0-8d42-0c6338deda54");

    //Projectile
    public static final DeferredHolder<Attribute, Attribute> PROJECTILE_KNOCKBACK = registerAttribute("projectile.knockback", (id) -> new RangedAttribute("attribute.name.krystal_core.projectile_knockback", 1.0F, 0.0, 1024.0).setSyncable(true), "113497c5-e526-43cb-811e-70ebbcbaf5dc");
    public static final DeferredHolder<Attribute, Attribute> PROJECTILE_VELOCITY = registerAttribute("projectile.velocity", (id) -> new RangedAttribute("attribute.name.krystal_core.projectile_velocity", 1.0F, 0.0, 1024.0).setSyncable(true), "deb95c2d-ae27-40db-b263-0fd964dd17de");
    public static final DeferredHolder<Attribute, Attribute> DRAW_SPEED = registerAttribute("draw_speed", (id) -> new RangedAttribute("attribute.name.krystal_core.draw_speed", 1.0F, 0.0, 1024.0).setSyncable(true), "6a30da25-3d8d-4031-956c-7844988a69d2");

    public static DeferredHolder<Attribute, Attribute> registerAttribute(String name, Function<String, Attribute> attribute, String uuid) {
        return registerAttribute(name, attribute, UUID.fromString(uuid));
    }

    public static DeferredHolder<Attribute, Attribute> registerAttribute(String name, Function<String, Attribute> attribute, UUID uuid) {
        DeferredHolder<Attribute, Attribute> registryObject = ATTRIBUTES.register(name, () -> attribute.apply(name));
        UUIDS.put(registryObject, uuid);
        return registryObject;
    }

    @SubscribeEvent
    public static void modifyEntityAttributes(EntityAttributeModificationEvent event) {
        event.getTypes().forEach(e -> {
            for (DeferredHolder<Attribute, ? extends Attribute> entry : ATTRIBUTES.getEntries()) {
                event.add(e, entry);
            }
        });
    }

    public static float nonRangedCritChance(LivingEntity attacker, DamageSource source) {
        AttributesHelper helper = AttributesHelper.create(attacker);
        double chance = helper.getAttributeSafe(GLOBAL_CRIT_CHANCE, 0.04);
        if (source.is(KCTags.Damage.MELEE.tag())) {
            chance += helper.getAttributeSafe(MELEE_CRIT_CHANCE, 1.0);
        } else if (source.is(KCTags.Damage.MAGIC.tag())) {
            chance += helper.getAttributeSafe(MAGIC_CRIT_CHANCE, 1.0);
        }
        return (float) chance;
    }

    public static double rangedCritChance(LivingEntity attacker, Projectile proj) {
        AttributesHelper helper = AttributesHelper.create(attacker);
        double chance = helper.getAttributeSafe(GLOBAL_CRIT_CHANCE, 0.04);
        if (proj.getType().is(KCTags.Entities.MAGIC_PROJECTILE.tag())) {
            chance += helper.getAttributeSafe(MAGIC_CRIT_CHANCE, 1.0);
        } else {
            chance += helper.getAttributeSafe(RANGED_CRIT_CHANCE, 1.0);
        }
        return chance;
    }

    public static float nonRangedCritDamage(LivingEntity attacker, DamageSource source) {
        AttributesHelper helper = AttributesHelper.create(attacker);
        double damage = helper.getAttributeSafe(GLOBAL_CRIT_DAMAGE, 1.5);
        if (isPhysical(source)) {
            damage += helper.getAttributeSafe(MELEE_CRIT_DAMAGE, 0.0);
        } else {
            damage += helper.getAttributeSafe(MAGIC_CRIT_DAMAGE, 0.0);
        }
        return (float) damage;
    }

    public static float rangedCritDamage(LivingEntity attacker, Projectile proj) {
        AttributesHelper helper = AttributesHelper.create(attacker);
        double damage = helper.getAttributeSafe(GLOBAL_CRIT_DAMAGE, 1.5);
        if (isPhysical(proj)) {
            damage += helper.getAttributeSafe(RANGED_CRIT_DAMAGE, 0.0);
        } else {
            damage += helper.getAttributeSafe(MAGIC_CRIT_DAMAGE, 0.0);
        }
        return (float) damage;
    }

    public static boolean isPhysical(DamageSource source) {
        return source.is(KCTags.Damage.MELEE.tag()) && !source.is(KCTags.Damage.MAGIC.tag());
    }

    public static boolean isPhysical(Projectile proj) {
        return !proj.getType().is(KCTags.Entities.MAGIC_PROJECTILE.tag());
    }

    public static void staticInit() {}
}
