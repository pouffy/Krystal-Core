package com.pouffydev.krystal_core.foundation.event;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.Event;

import java.util.List;
import java.util.function.Supplier;

public class AddBoatTypesEvent extends Event {
    public List<Entry> entries;

    public void register(String name, Supplier<Block> planks, Supplier<Item> stickItem, Supplier<Item> boatItem, Supplier<Item> chestBoatItem, boolean raft) {
        this.entries.add(new Entry(name, planks, stickItem, boatItem, chestBoatItem, raft));
    }

    public void register(String name, Supplier<Block> planks, boolean raft) {
        this.entries.add(new Entry(name, planks, () -> Items.STICK, () -> Items.AIR, () -> Items.AIR, raft));
    }

    public void register(String name, Supplier<Block> planks) {
        this.register(name, planks, false);
    }

    public record Entry(String name, Supplier<Block> planks, Supplier<Item> stickItem, Supplier<Item> boatItem, Supplier<Item> chestBoatItem, boolean raft) { }
}
