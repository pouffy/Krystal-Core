package com.pouffydev.krystal_core.datagen.server;

import com.pouffydev.krystal_core.KrystalCore;
import com.pouffydev.krystal_core.core.KCTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class KCDamageTagsProvider extends DamageTypeTagsProvider {
    public KCDamageTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, KrystalCore.ID, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    protected void addTags(HolderLookup.Provider lookupProvider) {
        this.tag(KCTags.Damage.MAGIC.tag()).addTags(Tags.DamageTypes.IS_MAGIC);
        this.tag(KCTags.Damage.MELEE.tag()).addTags(Tags.DamageTypes.IS_PHYSICAL).remove(DamageTypeTags.IS_FALL, DamageTypeTags.IS_PROJECTILE, DamageTypeTags.IS_EXPLOSION, DamageTypeTags.WITCH_RESISTANT_TO);
    }
}
