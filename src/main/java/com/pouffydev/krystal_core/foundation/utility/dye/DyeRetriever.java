package com.pouffydev.krystal_core.foundation.utility.dye;

import com.pouffydev.krystal_core.foundation.event.PopulateKnownDyesEvent;
import com.pouffydev.krystal_core.foundation.schrodinger.SchrodingerBlock;
import com.pouffydev.krystal_core.foundation.schrodinger.SchrodingerItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.neoforged.neoforge.common.NeoForge;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class DyeRetriever {
    public final Map<String, String> knownDyes = new HashMap<>();

    public DyeRetriever() {
        PopulateKnownDyesEvent event = NeoForge.EVENT_BUS.post(new PopulateKnownDyesEvent(knownDyes));
        event.getKnownDyes().forEach(entry -> knownDyes.put(entry.getKey(), entry.getValue()));
    }

    @Nullable
    public SchrodingerBlock getCandle(@NotNull DyeColor color) {
        return getBlock(color, "%s_candle");
    }

    @Nullable
    public SchrodingerBlock getBlock(@NotNull DyeColor color, String nameFormat) {
        String dyeName = color.getName();
        if (knownDyes.containsKey(dyeName)) {
            ResourceLocation location = ResourceLocation.fromNamespaceAndPath(knownDyes.get(dyeName), nameFormat.formatted(dyeName));
            return SchrodingerBlock.unstable(location);
        }
        return SchrodingerBlock.NULL;
    }

    @Nullable
    public SchrodingerItem getItem(@NotNull DyeColor color, String nameFormat) {
        String dyeName = color.getName();
        if (knownDyes.containsKey(dyeName)) {
            ResourceLocation location = ResourceLocation.fromNamespaceAndPath(knownDyes.get(dyeName), nameFormat.formatted(dyeName));
            return SchrodingerItem.unstable(location);
        }
        return SchrodingerItem.NULL;
    }
}
