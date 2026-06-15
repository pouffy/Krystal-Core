package com.pouffydev.krystal_core.datagen.client;

import com.pouffydev.krystal_core.KrystalCore;
import com.pouffydev.krystal_core.foundation.data.provider.client.KrystalLanguageProvider;
import com.pouffydev.krystal_core.foundation.data.provider.client.KrystalSoundsProvider;
import net.minecraft.data.PackOutput;

public class KCLangProvider extends KrystalLanguageProvider {
    public KCLangProvider(PackOutput output, KrystalSoundsProvider soundsProvider) {
        super(output, KrystalCore.ID, "en_us", soundsProvider);
    }

    @Override
    protected void extraTranslations() {
        ui("when_eaten", "When Eaten:");
        ui("when_drank", "When Drank:");
        ui("when_worn", "When Worn:");
        ui("soulbound", "Soulbound");
    }
}
