package com.pouffydev.krystal_core.foundation.bundle;

import com.pouffydev.krystal_core.KrystalCore;
import com.pouffydev.krystal_core.foundation.bundle.runtime.AbstractBundleRecipeHandler;
import lombok.Getter;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.data.loading.DatagenModLoader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class Bundle {
    private final BundleManager manager;
    @Getter
    private boolean isLoaded;
    protected final IEventBus bus;
    @Getter
    private final String modid;
    @Getter
    public static String recipeProvName;

    public abstract List<String> getRequiredClasses();

    public Bundle(BundleManager manager) {
        this.manager = manager;
        this.bus = manager.getModEventBus();
        modid = getName();
        recipeProvName = getName();
    }

    public abstract String getName();

    public ResourceLocation getId() {
        return ResourceLocation.fromNamespaceAndPath(manager.getNamespace(), getName());
    }

    public void tryLoad() {
        Map<String, Boolean> classMap = new HashMap<>();
        boolean allClassesFound;
        KrystalCore.LOGGER.info("Checking required classes for bundle {}", getId());
        for (String className : getRequiredClasses()) {
            classMap.put(className, false);
        }
        for (Map.Entry<String, Boolean> entry : classMap.entrySet()) {
            if (!entry.getValue() && KrystalCore.isClassFound(entry.getKey())) {
                entry.setValue(true);
            }
        }
        // Check if all classes are found
        allClassesFound = classMap.values().stream().allMatch(Boolean::booleanValue);
        this.isLoaded = loadCheck(allClassesFound);
    }

    private boolean loadCheck(boolean allClassesFound) {
        if (allClassesFound) {
            this.onLoad();
            KrystalCore.LOGGER.info("Bundle {} is loaded", getId());
            return true;
        } else if (DatagenModLoader.isRunningDataGen()) {
            this.onLoad();
            KrystalCore.LOGGER.info("Skipping bundle class check for {} as data gen is running", getId());
            return true;
        }
        else {
            KrystalCore.LOGGER.info("Bundle {} could not be loaded", getId());
            return false;
        }
    }

    protected abstract void onLoad();

    public String getBundleContentName(String name) {
        return Objects.equals(modid, manager.getNamespace()) ? name : getId().getPath() + "/" + name;
    }

    public abstract void runDatagen(GatherDataEvent event);

    public abstract AbstractBundleRecipeHandler getRecipeHandler();
}
