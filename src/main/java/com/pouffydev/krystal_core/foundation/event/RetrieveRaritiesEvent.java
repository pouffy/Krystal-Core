package com.pouffydev.krystal_core.foundation.event;

import com.google.common.collect.ImmutableTable;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.Event;

import java.util.Map;

public class RetrieveRaritiesEvent extends Event {
    private final ImmutableTable<String, String, Rarity> registered;

    public RetrieveRaritiesEvent(Map<AddRarityEvent.Entry, Rarity> registered) {
        ImmutableTable.Builder<String, String, Rarity> builder = ImmutableTable.builder();
        for (var entry : registered.entrySet()) {
            builder.put(entry.getKey().namespace(), entry.getKey().name(), entry.getValue());
        }
        this.registered = builder.build();
    }

    public Rarity get(String modid, String id) {
        return this.registered.get(modid, id);
    }

    public Map<String, Rarity> forMod(String modid) {
        return this.registered.columnMap().get(modid);
    }
}
