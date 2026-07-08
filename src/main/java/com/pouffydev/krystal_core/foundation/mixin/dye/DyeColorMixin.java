package com.pouffydev.krystal_core.foundation.mixin.dye;

import com.pouffydev.krystal_core.foundation.event.AddDyeColorEvent;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.material.MapColor;
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

@Mixin(DyeColor.class)
public class DyeColorMixin {
    // Allows new entries.
    @SuppressWarnings("InvokerTarget")
    @Invoker("<init>")
    private static DyeColor newDyeColor(String internalName, int internalId, int id, String name, int textureDefuseColor, MapColor mapColor, int fireworkColor, int textColor) {
        throw new AssertionError();
    }

    // Get dye color field.
    @SuppressWarnings("ShadowTarget")
    @Shadow
    private static @Final
    @Mutable DyeColor[] $VALUES;

    // Injects data.
    @Inject(method = "<clinit>", at = @At(
            value = "FIELD",
            opcode = Opcodes.PUTSTATIC,
            target = "Lnet/minecraft/world/item/DyeColor;$VALUES:[Lnet/minecraft/world/item/DyeColor;",
            shift = At.Shift.AFTER))
    private static void addCustomDye(CallbackInfo ci) {
        // Get dye color list.
        var dyeColors = new ArrayList<>(Arrays.asList($VALUES));
        var last = dyeColors.get(dyeColors.size() - 1);
        Map<AddDyeColorEvent.Entry, DyeColor> custom = new HashMap<>();

        AddDyeColorEvent event = NeoForge.EVENT_BUS.post(new AddDyeColorEvent());
        for (var entry : event.entries) {
            var dyeColor = newDyeColor(entry.name().toUpperCase(Locale.ROOT), last.ordinal() + 1, dyeColors.size(), entry.name(), entry.textureDefuseColor(), entry.mapColor(), entry.fireworkColor(), entry.textColor());
            custom.put(entry, dyeColor);
            dyeColors.add(dyeColor);
        }
    }
}
