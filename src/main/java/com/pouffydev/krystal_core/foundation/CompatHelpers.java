package com.pouffydev.krystal_core.foundation;

import net.neoforged.fml.ModList;
import net.neoforged.neoforge.data.loading.DatagenModLoader;

public class CompatHelpers {

    public static boolean isLoaded(String namespace) {
        return ModList.get().isLoaded(namespace) || DatagenModLoader.isRunningDataGen();
    }
}
