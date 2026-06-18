package com.pouffydev.krystal_core.core.event;

import com.pouffydev.krystal_core.content.item.IRenderableCurio;
import com.pouffydev.krystal_core.content.item.equipment.KrystalArmorItem;
import com.pouffydev.krystal_core.core.registry.RegistryHelper;
import com.pouffydev.krystal_core.foundation.CurioHelpers;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.List;

public class ClientEvents {

    @SubscribeEvent
    public void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        Item.TooltipContext context = event.getContext();
        List<Component> tooltip = event.getToolTip();
        TooltipFlag flag = event.getFlags();
        for (var registry : RegistryHelper.COMPONENT_REGISTRIES) {
            for (var type : registry.getEntries()) {
                var comp = stack.get(type);
                if (comp instanceof TooltipProvider tooltipProvider) {
                    tooltipProvider.addToTooltip(context, tooltip::add, flag);
                }
            }
        }
    }

    @SubscribeEvent
    public void onRenderPlayerPre(RenderPlayerEvent.Pre event) {
        Player player = event.getEntity();
        boolean hideHead = false, hideHat = false, hideBody = false, hideLeftArm = false, hideRightArm = false, hideLeftLeg = false, hideRightLeg = false;
        var inventoryOp = CuriosApi.getCuriosInventory(player);
        if (inventoryOp.isPresent()) {
            var inventory = inventoryOp.get();
            List<SlotResult> all = CurioHelpers.findAllCurios(inventory, inventory.getWearer(), stack -> true);
            for (SlotResult result : all) {
                if (!result.slotContext().visible()) continue;
                if (result.stack().getItem() instanceof IRenderableCurio renderableCurio) {
                    for (String limb : renderableCurio.hiddenLimbs()) {
                        switch (limb) {
                            case "head": hideHead = true; break;
                            case "hat": hideHat = true; break;
                            case "body": hideBody = true; break;
                            case "left_arm": hideLeftArm = true; break;
                            case "right_arm": hideRightArm = true; break;
                            case "left_leg": hideLeftLeg = true; break;
                            case "right_leg": hideRightLeg = true; break;
                        }
                    }
                }
            }
        }
        for (ItemStack stack : player.getArmorSlots()) {
            if (stack.getItem() instanceof KrystalArmorItem armorItem) {
                for (String limb : armorItem.hiddenLimbs().get(armorItem.getType().getSlot())) {
                    switch (limb) {
                        case "head": hideHead = true; break;
                        case "hat": hideHat = true; break;
                        case "body": hideBody = true; break;
                        case "left_arm": hideLeftArm = true; break;
                        case "right_arm": hideRightArm = true; break;
                        case "left_leg": hideLeftLeg = true; break;
                        case "right_leg": hideRightLeg = true; break;
                    }
                }
            }
        }
        var model = event.getRenderer().getModel();
        model.head.visible = !hideHead;
        model.hat.visible = !hideHat;
        model.body.visible = !hideBody;
        model.leftArm.visible = !hideLeftArm;
        model.rightArm.visible = !hideRightArm;
        model.leftLeg.visible = !hideLeftLeg;
        model.rightLeg.visible = !hideRightLeg;
    }
}
