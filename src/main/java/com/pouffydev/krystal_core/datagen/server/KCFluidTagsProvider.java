package com.pouffydev.krystal_core.datagen.server;

import com.pouffydev.krystal_core.KrystalCore;
import com.pouffydev.krystal_core.core.KCTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class KCFluidTagsProvider extends FluidTagsProvider {
    public KCFluidTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, KrystalCore.ID, existingFileHelper);
    }

    public void addTags(HolderLookup.Provider lookupProvider) {
        this.tag(KCTags.Fluids.HONEY.tag()).addOptional(KrystalCore.HONEY.getId()).addOptional(KrystalCore.FLOWING_HONEY.getId());
        this.tag(KCTags.Fluids.POWDER_SNOW.tag()).addOptional(KrystalCore.POWDER_SNOW.getId()).addOptional(KrystalCore.FLOWING_POWDER_SNOW.getId());
        this.tagWithOptionalLegacy(KCTags.Fluids.HONEY.tag());
        this.tagWithOptionalLegacy(KCTags.Fluids.POWDER_SNOW.tag());
    }

    private IntrinsicHolderTagsProvider.IntrinsicTagAppender<Fluid> tagWithOptionalLegacy(TagKey<Fluid> tag) {
        IntrinsicHolderTagsProvider.IntrinsicTagAppender<Fluid> tagAppender = this.tag(tag);
        tagAppender.addOptionalTag(ResourceLocation.fromNamespaceAndPath("forge", tag.location().getPath()));
        return tagAppender;
    }
}
