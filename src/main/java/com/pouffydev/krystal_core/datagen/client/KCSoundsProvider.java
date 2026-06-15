package com.pouffydev.krystal_core.datagen.client;

import com.pouffydev.krystal_core.KrystalCore;
import com.pouffydev.krystal_core.foundation.data.provider.client.KrystalSoundsProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class KCSoundsProvider extends KrystalSoundsProvider {
    public KCSoundsProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, KrystalCore.ID, helper);
    }

    @Override
    public void registerSounds() {

    }
}
