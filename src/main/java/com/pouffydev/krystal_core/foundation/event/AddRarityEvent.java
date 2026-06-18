package com.pouffydev.krystal_core.foundation.event;

import net.minecraft.network.chat.Style;
import net.neoforged.bus.api.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class AddRarityEvent extends Event {

    public List<Entry> entries = new ArrayList<>();

    public void register(String namespace, String name, UnaryOperator<Style> styleFunction) {
        entries.add(new Entry(namespace, name, styleFunction));
    }


    public record Entry(String namespace, String name, UnaryOperator<Style> styleFunction) {

    }
}
