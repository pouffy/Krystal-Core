package com.pouffydev.krystal_core.helpers.data_driven;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.Mth;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CompostableJsonListener extends SimpleJsonListener {
    public static List<JsonElement> compostable;
    public static final CompostableJsonListener instance = new CompostableJsonListener();
    public CompostableJsonListener() {
        super("compostable");
        compostable = new ArrayList<>();
    }
    
    @Override
    protected void apply(Map<ResourceLocation, JsonElement> files, @NotNull ResourceManager resourceManager, @NotNull ProfilerFiller profilerFiller) {
        files.forEach((resourceLocation, jsonElement) -> {
            LOGGER.info("%s ||| %s".formatted(resourceLocation, jsonElement));
            compostable.add(jsonElement);
        });
        for (JsonElement jsonElement : compostable) {
            JsonObject json = jsonElement.getAsJsonObject();
            String item = json.get("item").getAsString();
            Item itemLike = ForgeRegistries.ITEMS.getValue(new ResourceLocation(item)).asItem();
            if (hasCompostableRegistered(itemLike)) {
                LOGGER.warn("Registered compostable item: %s".formatted(item));
            }
        }
    }
    public static void add(Item ingredient, float f) {
        ComposterBlock.COMPOSTABLES.put(ingredient, Mth.clamp(f, 0F, 1F));
    }
    
    public static boolean hasCompostableRegistered(ItemLike item) {
        return ComposterBlock.COMPOSTABLES.containsKey(item.asItem());
    }
    public static void registerCompostable() {
        for (JsonElement jsonElement : compostable) {
            JsonObject json = jsonElement.getAsJsonObject();
            Float chance = json.get("chance").getAsFloat();
            String item = json.get("item").getAsString();
            Item itemLike = ForgeRegistries.ITEMS.getValue(new ResourceLocation(item)).asItem();
            add(itemLike, chance);
        }
    }
    
}
