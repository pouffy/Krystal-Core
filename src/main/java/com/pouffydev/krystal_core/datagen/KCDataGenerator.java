package com.pouffydev.krystal_core.datagen;

import com.pouffydev.krystal_core.KrystalCore;
import com.pouffydev.krystal_core.datagen.client.KCLangProvider;
import com.pouffydev.krystal_core.datagen.client.KCSoundsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

public class KCDataGenerator {

    public static void gatherDataEvent(GatherDataEvent event) {
        KrystalCore.LOGGER.info("[Krystal Core] Data Generation starts.");
        String modId = KrystalCore.ID;
        DataGenerator dataGenerator = event.getGenerator();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        PackOutput packOutput = dataGenerator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        KCSoundsProvider sounds = new KCSoundsProvider(packOutput, fileHelper);
        KCLangProvider language = new KCLangProvider(packOutput, sounds);

        dataGenerator.addProvider(event.includeClient(), sounds);

        dataGenerator.addProvider(event.includeClient() && event.includeServer(), language);
    }
}
