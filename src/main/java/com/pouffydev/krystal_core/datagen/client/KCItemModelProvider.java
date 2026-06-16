package com.pouffydev.krystal_core.datagen.client;

import com.pouffydev.krystal_core.KrystalCore;
import com.pouffydev.krystal_core.foundation.data.provider.client.KrystalItemModelProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class KCItemModelProvider extends KrystalItemModelProvider {

    public KCItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, KrystalCore.ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(KrystalCore.HONEY_BUCKET);
    }
}
