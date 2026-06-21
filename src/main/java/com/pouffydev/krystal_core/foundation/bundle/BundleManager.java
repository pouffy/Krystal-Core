package com.pouffydev.krystal_core.foundation.bundle;

import com.pouffydev.krystal_core.KrystalCore;
import lombok.Getter;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class BundleManager {

    public final List<Bundle> BUNDLES;
    public final @Getter String namespace;

    public final @Getter IEventBus modEventBus;

    private BundleManager(String namespace, IEventBus modEventBus) {
        this.namespace = namespace;
        this.modEventBus = modEventBus;
        this.BUNDLES = new ArrayList<>();
    }

    public static BundleManager create(String namespace, IEventBus modEventBus) {
        var manager = new BundleManager(namespace, modEventBus);
        KrystalCore.INSTANCE.BUNDLE_MANAGERS.put(namespace, manager);
        return manager;
    }

    public final <B extends Bundle> BundleManager addBundle(Function<BundleManager, B> function) {
        B bundle = function.apply(this);
        if (KrystalCore.INSTANCE.BUNDLES.containsKey(bundle.getId())) {
            throw new IllegalArgumentException("A bundle with the name %s already exists!".formatted(bundle.getId()));
        }
        bundle.tryLoad();
        this.BUNDLES.add(bundle);
        KrystalCore.INSTANCE.BUNDLES.put(bundle.getId(), bundle);
        return this;
    }

    public void visit() {
        for (Bundle bundle : BUNDLES) {
            if (bundle.getName() == null || bundle.getName().isEmpty()) {
                KrystalCore.LOGGER.error("Bundle {} has no name set", bundle.getClass().getName());
            }
            if (bundle.isLoaded()) {
                KrystalCore.LOGGER.info("Load Complete for {}", bundle.getId());
            } else {
                KrystalCore.LOGGER.error("Failed to load {}. This is likely due to its required mod(s) not being present.", bundle.getId());
            }
        }
    }

    public void bundleDatagen(GatherDataEvent event) {
        for (Bundle bundle : BUNDLES) {
            bundle.runDatagen(event);
        }
    }

    public void forEach(Consumer<Bundle> consumer, boolean loaded) {
        if (loaded) {
            this.BUNDLES.stream().filter(Bundle::isLoaded).forEach(consumer);
        } else {
            this.BUNDLES.forEach(consumer);
        }
    }
}
