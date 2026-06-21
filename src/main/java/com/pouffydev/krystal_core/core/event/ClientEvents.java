package com.pouffydev.krystal_core.core.event;

import com.pouffydev.krystal_core.content.item.equipment.KrystalArmorItem;
import com.pouffydev.krystal_core.foundation.registry.RegistryHelper;
import com.pouffydev.krystal_core.integration.curios.CurioHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

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
        AtomicBoolean hideHead = new AtomicBoolean(false), hideHat = new AtomicBoolean(false), hideBody = new AtomicBoolean(false), hideLeftArm = new AtomicBoolean(false), hideRightArm = new AtomicBoolean(false), hideLeftLeg = new AtomicBoolean(false), hideRightLeg = new AtomicBoolean(false);
        CurioHandler.hideLimbs(player, hideHead, hideHat, hideBody, hideLeftArm, hideRightArm, hideLeftLeg, hideRightLeg);
        for (ItemStack stack : player.getArmorSlots()) {
            if (stack.getItem() instanceof KrystalArmorItem armorItem) {
                for (String limb : armorItem.hiddenLimbs().get(armorItem.getType().getSlot())) {
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
        var model = event.getRenderer().getModel();
        model.head.visible = !hideHead.get();
        model.hat.visible = !hideHat.get();
        model.body.visible = !hideBody.get();
        model.leftArm.visible = !hideLeftArm.get();
        model.rightArm.visible = !hideRightArm.get();
        model.leftLeg.visible = !hideLeftLeg.get();
        model.rightLeg.visible = !hideRightLeg.get();
    }
}
