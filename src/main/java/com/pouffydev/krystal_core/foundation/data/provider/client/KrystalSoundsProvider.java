package com.pouffydev.krystal_core.foundation.data.provider.client;

import com.mojang.datafixers.util.Pair;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Supplier;

public abstract class KrystalSoundsProvider extends SoundDefinitionsProvider {
    private final Set<Pair<Supplier<SoundEvent>, String>> subtitles;
    private final String modId;

    protected KrystalSoundsProvider(PackOutput output, String modId, ExistingFileHelper helper) {
        super(output, modId, helper);
        this.modId = modId;
        this.subtitles = new LinkedHashSet<>();
    }

    public Set<Pair<Supplier<SoundEvent>, String>> getSubtitles() {
        return this.subtitles;
    }

    public void add(final Supplier<SoundEvent> soundEvent, String subtitle, final SoundDefinition definition) {
        this.add(soundEvent, definition.subtitle("%s.subtitle.%s".formatted(modId, soundEvent.get().getLocation().getPath())));
        subtitles.add(Pair.of(soundEvent, subtitle));
    }

    private SoundDefinition.Sound simpleSound(String name) {
        return SoundDefinition.Sound.sound(ResourceLocation.fromNamespaceAndPath(modId, name), SoundDefinition.SoundType.SOUND);
    }
}
