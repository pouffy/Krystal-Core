package com.pouffydev.krystal_core.foundation.schrodinger;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.fml.ModList;

import javax.annotation.Nullable;
import java.util.Optional;

public class SchrodingerEffectInstance implements SchrodingersObject<MobEffectInstance> {
    public static final SchrodingerEffectInstance NULL = null;
    public final Either<ResourceLocation, Holder<MobEffect>> effect;
    private int duration;
    private int amplifier;
    private boolean ambient;
    private boolean visible;
    private boolean showIcon;
    @Nullable
    private final Either<SchrodingerEffectInstance, MobEffectInstance> hiddenEffect;

    public static SchrodingerEffectInstance stable(Holder<MobEffect> effect) {
        return new SchrodingerEffectInstance(Either.right(effect));
    }

    public static SchrodingerEffectInstance unstable(ResourceLocation effect) {
        return new SchrodingerEffectInstance(Either.left(effect));
    }

    public SchrodingerEffectInstance(Either<ResourceLocation, Holder<MobEffect>> effect) {
        this(effect, 0, 0);
    }

    public SchrodingerEffectInstance(Either<ResourceLocation, Holder<MobEffect>> effect, int duration) {
        this(effect, duration, 0);
    }

    public SchrodingerEffectInstance(Either<ResourceLocation, Holder<MobEffect>> effect, int duration, int amplifier) {
        this(effect, duration, amplifier, false, true);
    }

    public SchrodingerEffectInstance(Either<ResourceLocation, Holder<MobEffect>> effect, int duration, int amplifier, boolean ambient, boolean visible) {
        this(effect, duration, amplifier, ambient, visible, visible);
    }

    public SchrodingerEffectInstance(Either<ResourceLocation, Holder<MobEffect>> effect, int duration, int amplifier, boolean ambient, boolean visible, boolean showIcon) {
        this(effect, duration, amplifier, ambient, visible, showIcon, Either.left(NULL));
    }

    public SchrodingerEffectInstance(Either<ResourceLocation, Holder<MobEffect>> effect, int duration, int amplifier, boolean ambient, boolean visible, boolean showIcon, @Nullable Either<SchrodingerEffectInstance, MobEffectInstance> hiddenEffect) {
        this.effect = effect;
        this.duration = duration;
        this.amplifier = Mth.clamp(amplifier, 0, 255);
        this.ambient = ambient;
        this.visible = visible;
        this.showIcon = showIcon;
        this.hiddenEffect = hiddenEffect;
    }

    public SchrodingerEffectInstance duration(int duration) {
        this.duration = duration;
        return this;
    }

    public SchrodingerEffectInstance amplifier(int amplifier) {
        this.amplifier = amplifier;
        return this;
    }

    public SchrodingerEffectInstance ambient(boolean ambient) {
        this.ambient = ambient;
        return this;
    }

    public SchrodingerEffectInstance visible(boolean visible) {
        this.visible = visible;
        return this;
    }

    public SchrodingerEffectInstance showIcon(boolean showIcon) {
        this.showIcon = showIcon;
        return this;
    }

    @Override
    public MobEffectInstance get() {
        return effect.map((location) -> {
            if (ModList.get().isLoaded(location.getNamespace())) {
                var hidden = mapHidden();
                Optional<Holder.Reference<MobEffect>> holder = BuiltInRegistries.MOB_EFFECT.getHolder(location);
                if (holder.isPresent()) {
                    return new MobEffectInstance(holder.get(), duration, amplifier, ambient, visible, showIcon, hidden);
                }
            }
            return null;
        }, (effect) -> {
            var hidden = mapHidden();
            return new MobEffectInstance(effect, duration, amplifier, ambient, visible, showIcon, hidden);
        });
    }

    public MobEffectInstance mapHidden() {
        if (hiddenEffect == null)
            return null;
        return hiddenEffect.map(SchrodingerEffectInstance::get, (effect) -> effect);
    }
}
