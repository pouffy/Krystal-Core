package com.pouffydev.krystal_core.datagen.server;

import com.pouffydev.krystal_core.KrystalCore;
import com.pouffydev.krystal_core.core.KCTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class KCEntityTagsProvider extends EntityTypeTagsProvider {
    public KCEntityTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, KrystalCore.ID, existingFileHelper);
    }

    public void addTags(HolderLookup.Provider lookupProvider) {
        this.tag(KCTags.Entities.MAGIC_PROJECTILE.tag()).add(
                EntityType.FIREBALL,
                EntityType.WIND_CHARGE,
                EntityType.BREEZE_WIND_CHARGE,
                EntityType.DRAGON_FIREBALL,
                EntityType.WITHER_SKULL,
                EntityType.SNOWBALL
        );
    }
}
