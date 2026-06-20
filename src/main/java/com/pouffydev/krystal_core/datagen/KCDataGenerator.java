package com.pouffydev.krystal_core.datagen;

import com.pouffydev.krystal_core.KrystalCore;
import com.pouffydev.krystal_core.datagen.client.KCItemModelProvider;
import com.pouffydev.krystal_core.datagen.client.KCLangProvider;
import com.pouffydev.krystal_core.datagen.client.KCSoundsProvider;
import com.pouffydev.krystal_core.datagen.server.KCDamageTagsProvider;
import com.pouffydev.krystal_core.datagen.server.KCEntityTagsProvider;
import com.pouffydev.krystal_core.datagen.server.KCFluidTagsProvider;
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

        KCFluidTagsProvider fluidTags = new KCFluidTagsProvider(packOutput, lookupProvider, fileHelper);
        KCDamageTagsProvider damageTags = new KCDamageTagsProvider(packOutput, lookupProvider, fileHelper);
        KCEntityTagsProvider entityTags = new KCEntityTagsProvider(packOutput, lookupProvider, fileHelper);

        KCItemModelProvider itemModels = new KCItemModelProvider(packOutput, fileHelper);
        KCSoundsProvider sounds = new KCSoundsProvider(packOutput, fileHelper);
        KCLangProvider language = new KCLangProvider(packOutput, sounds);

        dataGenerator.addProvider(event.includeServer(), fluidTags);
        dataGenerator.addProvider(event.includeServer(), damageTags);
        dataGenerator.addProvider(event.includeServer(), entityTags);

        dataGenerator.addProvider(event.includeClient(), itemModels);
        dataGenerator.addProvider(event.includeClient(), sounds);

        dataGenerator.addProvider(event.includeClient() && event.includeServer(), language);

        KrystalCore.INSTANCE.krystalCoreBundle.bundleDatagen(event);
    }
}
