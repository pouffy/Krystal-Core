package com.pouffydev.krystal_core.core.event;

import com.pouffydev.krystal_core.core.registry.RegistryHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

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
}
