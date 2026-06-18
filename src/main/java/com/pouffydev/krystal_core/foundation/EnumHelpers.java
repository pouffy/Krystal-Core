package com.pouffydev.krystal_core.foundation;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Rarity;

public class EnumHelpers {
    public static StringRepresentable.EnumCodec<Rarity> RARITY_CODEC = StringRepresentable.fromEnum(Rarity::values);

    public static Rarity getRarity(String id) {
        return RARITY_CODEC.byName(id, Rarity.COMMON);
    }

    public static Boat.Type getBoatType(String id) {
        return Boat.Type.byName(id);
    }
}
