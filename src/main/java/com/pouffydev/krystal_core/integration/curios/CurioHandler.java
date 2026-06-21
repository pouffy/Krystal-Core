package com.pouffydev.krystal_core.integration.curios;

import com.pouffydev.krystal_core.content.item.IRenderableCurio;
import com.pouffydev.krystal_core.foundation.CompatHelpers;
import com.pouffydev.krystal_core.foundation.CurioHelpers;
import net.minecraft.world.entity.player.Player;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class CurioHandler {

    public static void hideLimbs(Player player, AtomicBoolean hideHead, AtomicBoolean hideHat, AtomicBoolean hideBody, AtomicBoolean hideLeftArm, AtomicBoolean hideRightArm, AtomicBoolean hideLeftLeg, AtomicBoolean hideRightLeg) {
        if (CompatHelpers.isLoaded("curios")) LoadedOnly.hideLimbs(player, hideHead, hideHat, hideBody, hideLeftArm, hideRightArm, hideLeftLeg, hideRightLeg);
    }

    public static class LoadedOnly {

        public static void hideLimbs(Player player, AtomicBoolean hideHead, AtomicBoolean hideHat, AtomicBoolean hideBody, AtomicBoolean hideLeftArm, AtomicBoolean hideRightArm, AtomicBoolean hideLeftLeg, AtomicBoolean hideRightLeg) {
            var inventoryOp = CuriosApi.getCuriosInventory(player);
            if (inventoryOp.isPresent()) {
                var inventory = inventoryOp.get();
                List<SlotResult> all = CurioHelpers.findAllCurios(inventory, inventory.getWearer(), stack -> true);
                for (SlotResult result : all) {
                    if (!result.slotContext().visible()) continue;
                    if (result.stack().getItem() instanceof IRenderableCurio renderableCurio) {
                        for (String limb : renderableCurio.hiddenLimbs()) {
                            switch (limb) {
                                case "head": hideHead.set(true); break;
                                case "hat": hideHat.set(true); break;
                                case "body": hideBody.set(true); break;
                                case "left_arm": hideLeftArm.set(true); break;
                                case "right_arm": hideRightArm.set(true); break;
                                case "left_leg": hideLeftLeg.set(true); break;
                                case "right_leg": hideRightLeg.set(true); break;
                            }
                        }
                    }
                }
            }
        }
    }
}
