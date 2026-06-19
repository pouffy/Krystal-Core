package com.pouffydev.krystal_core.core.event;

import com.pouffydev.krystal_core.foundation.event.EventHandler;
import net.neoforged.bus.api.IEventBus;

public class KCEventHandler extends EventHandler {
    public KCEventHandler(IEventBus modEventBus) {
        super(modEventBus);
    }

    @Override
    public void registerModEvents(IEventBus eventBus) {

    }

    @Override
    public void registerForgeEvents(IEventBus eventBus) {
        eventBus.register(new PlayerEvents());
        eventBus.register(new ClientEvents());
        eventBus.register(new AttributeEvents());
    }
}
