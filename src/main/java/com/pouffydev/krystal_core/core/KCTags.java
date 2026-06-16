package com.pouffydev.krystal_core.core;

import com.pouffydev.krystal_core.KrystalCore;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

public class KCTags {

    public enum Items {
        USE_DURATION("attributes_affected/use_duration")
        ;

        private final String name;
        Items(String name) {this.name = name;}
        Items() {this.name = this.name().toLowerCase();}
        public TagKey<Item> tag() {
            return TagKey.create(Registries.ITEM, KrystalCore.location(this.name));
        }
    }

    public enum Entities {
        MAGIC_PROJECTILE
        ;
        private final String name;
        Entities(String name) {
            this.name = name;
        }
        Entities() {
            this.name = this.name().toLowerCase();
        }
        public TagKey<EntityType<?>> tag() {
            return TagKey.create(Registries.ENTITY_TYPE, KrystalCore.location(this.name));
        }
    }

    public enum Damage {
        MELEE,
        MAGIC
        ;
        private final String name;
        Damage(String name) {
            this.name = name;
        }
        Damage() {
            this.name = this.name().toLowerCase();
        }
        public TagKey<DamageType> tag() {
            return TagKey.create(Registries.DAMAGE_TYPE, KrystalCore.location(this.name));
        }
    }
}
