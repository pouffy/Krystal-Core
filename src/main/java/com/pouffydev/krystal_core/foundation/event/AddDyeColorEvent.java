package com.pouffydev.krystal_core.foundation.event;

import com.pouffydev.krystal_core.KrystalCore;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.Event;

import java.util.ArrayList;
import java.util.List;

public class AddDyeColorEvent extends Event {

    public List<Entry> entries = new ArrayList<>();

    public void register(String name, int textureDefuseColor, MapColor mapColor, int fireworkColor, int textColor) {
        if (entries.stream().anyMatch(entry -> entry.name().equals(name))) {
            KrystalCore.LOGGER.error("Dye color with name '{}' is already registered. Skipping.", name);
            return;
        }
        entries.add(new Entry(name, textureDefuseColor, mapColor, fireworkColor, textColor));
    }

    public record Entry(String name, int textureDefuseColor, MapColor mapColor, int fireworkColor, int textColor) {

    }
}
