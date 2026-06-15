package com.pouffydev.krystal_core.content.item.exploration;

import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.maps.MapId;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

public class CustomFilledMap extends MapItem {
    public CustomFilledMap(Properties properties) {
        super(properties);
    }

    public static ItemStack create(ItemLike filled, Level level, int levelX, int levelZ, byte scale, boolean trackingPosition, boolean unlimitedTracking) {
        ItemStack itemstack = new ItemStack(filled);
        MapId mapid = createNewSavedData(level, levelX, levelZ, scale, trackingPosition, unlimitedTracking, level.dimension());
        itemstack.set(DataComponents.MAP_ID, mapid);
        return itemstack;
    }

    private static MapId createNewSavedData(Level level, int x, int z, int scale, boolean trackingPosition, boolean unlimitedTracking, ResourceKey<Level> dimension) {
        MapItemSavedData savedData = MapItemSavedData.createFresh(x, z, (byte)scale, trackingPosition, unlimitedTracking, dimension);
        MapId mapid = level.getFreeMapId();
        level.setMapData(mapid, savedData);
        return mapid;
    }
}
