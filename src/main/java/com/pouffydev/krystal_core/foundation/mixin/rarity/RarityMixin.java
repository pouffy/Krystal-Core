package com.pouffydev.krystal_core.foundation.mixin.rarity;

import com.pouffydev.krystal_core.foundation.event.AddRarityEvent;
import com.pouffydev.krystal_core.foundation.event.RetrieveRaritiesEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.common.NeoForge;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;
import java.util.function.UnaryOperator;

@Mixin(Rarity.class)
public class RarityMixin {
    // Allows new entries.
    @SuppressWarnings("InvokerTarget")
    @Invoker("<init>")
    private static Rarity newRarity(String internalName, int internalId, int index, String name, UnaryOperator<Style> styleFunction) {
        throw new AssertionError();
    }

    // Get rarity field.
    @SuppressWarnings("ShadowTarget")
    @Shadow
    private static @Final
    @Mutable Rarity[] $VALUES;

    // Injects data.
    @Inject(method = "<clinit>", at = @At(
            value = "FIELD",
            opcode = Opcodes.PUTSTATIC,
            target = "Lnet/minecraft/world/item/Rarity;$VALUES:[Lnet/minecraft/world/item/Rarity;",
            shift = At.Shift.AFTER))
    private static void addCustomRarity(CallbackInfo ci) {
        // Get rarity list.
        var rarities = new ArrayList<>(Arrays.asList($VALUES));
        var last = rarities.get(rarities.size() - 1);
        Map<AddRarityEvent.Entry, Rarity> custom = new HashMap<>();

        AddRarityEvent event = NeoForge.EVENT_BUS.post(new AddRarityEvent());
        for (var entry : event.entries) {
            String id = entry.namespace() + "_" + entry.name();
            var rarity = newRarity(id.toUpperCase(Locale.ROOT), last.ordinal() + 1, rarities.size(), id, style -> entry.styleFunction().apply(style));
            custom.put(entry, rarity);
            rarities.add(rarity);
        }
        NeoForge.EVENT_BUS.post(new RetrieveRaritiesEvent(custom));

        // Inject.
        $VALUES = rarities.toArray(new Rarity[0]);
    }
}
