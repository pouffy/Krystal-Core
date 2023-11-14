package com.pouffydev.krystal_core.foundation;

import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

//import static com.pouffydev.krystal_core.helpers.data_driven.CompostableJsonListener.registerCompostable;

public class CommonEvents {
    
    public CommonEvents() {
    }
    public static void init(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            registerCompostable();
        });
    }
    
    public static void registerCompostable() {
    }
}
