package com.pouffydev.krystal_core.foundation.event;

import com.pouffydev.krystal_core.KrystalCore;
import net.minecraft.world.item.DyeColor;
import net.neoforged.bus.api.Event;

import java.util.Map;
import java.util.Set;

public class PopulateKnownDyesEvent extends Event {
    private final Map<String, String> knownDyes;

    public PopulateKnownDyesEvent(Map<String, String> knownDyes) {
        this.knownDyes = knownDyes;
    }

    public void addVanilla(DyeColor color) {
        this.knownDyes.put(color.getName(), "minecraft");
    }

    //Dye Depot should override because they are cooler
    public void addDyeDepot(String name) {
        this.knownDyes.put(name, "dye_depot");
    }

    public void addDye(String namespace, String name) {
        if (knownDyes.containsKey(name)) {
            KrystalCore.LOGGER.error("Dye color with name '{}' is already known. Skipping.", name);
            return;
        }
        this.knownDyes.put(name, namespace);
    }

    public Set<Map.Entry<String, String>> getKnownDyes() {
        return this.knownDyes.entrySet();
    }
}
