package com.pouffydev.krystal_core.foundation.event;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;

public abstract class EventHandler {
    private final IEventBus modEventBus;

    public EventHandler(IEventBus modEventBus) {
        this.modEventBus = modEventBus;
    }

    public abstract void registerModEvents(IEventBus eventBus);
    public abstract void registerForgeEvents(IEventBus eventBus);

    public void register() {
        registerModEvents(modEventBus);
        registerForgeEvents(NeoForge.EVENT_BUS);
    }
}
