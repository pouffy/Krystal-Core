package com.pouffydev.krystal_core.foundation.mixin.boat;

import com.pouffydev.krystal_core.foundation.event.AddBoatTypesEvent;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.NeoForge;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Supplier;

@Mixin(Boat.Type.class)
public class BoatTypeMixin {
    @SuppressWarnings("InvokerTarget")
    @Invoker("<init>")
    private static Boat.Type newBoatType(String internalName, int ordinal, Supplier<Block> planks, String name, Supplier<Item> boatItem, Supplier<Item> chestBoatItem, Supplier<Item> stickItem, boolean raft) {
        throw new AssertionError();
    }

    @SuppressWarnings("ShadowTarget")
    @Shadow
    private static @Final
    @Mutable
    Boat.Type[] $VALUES;

    @Inject(method = "<clinit>", at = @At(value = "FIELD", opcode = 179, target = "Lnet/minecraft/world/entity/vehicle/Boat$Type;$VALUES:[Lnet/minecraft/world/entity/vehicle/Boat$Type;", shift = At.Shift.AFTER))
    private static void addBoatTypes(CallbackInfo ci) {
        var values = new ArrayList<>(Arrays.asList($VALUES));
        var last = values.getLast();

        AddBoatTypesEvent event = NeoForge.EVENT_BUS.post(new AddBoatTypesEvent());
        for (var entry : event.entries) {
            var boatType = newBoatType(entry.name().toUpperCase(), last.ordinal() + 1, entry.planks(), entry.name(), entry.boatItem(), entry.chestBoatItem(), entry.stickItem(), entry.raft());
            values.add(boatType);
        }

        $VALUES = values.toArray(new Boat.Type[0]);
    }
}
