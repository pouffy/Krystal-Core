package com.pouffydev.krystal_core.client;

import com.pouffydev.krystal_core.KrystalCore;
import com.pouffydev.krystal_core.client.model.item.WornArmorModel;
import com.pouffydev.krystal_core.content.item.equipment.KrystalArmorItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ArmorClientItemExtensions implements IClientItemExtensions {
    private final Supplier<WornArmorModel> model;

    public ArmorClientItemExtensions(Supplier<WornArmorModel> model) {
        this.model = model;
    }

    @Override
    public @NotNull WornArmorModel getHumanoidArmorModel(LivingEntity entity, @NotNull ItemStack itemStack, @NotNull EquipmentSlot armorSlot, @NotNull HumanoidModel _default) {
        float pTicks = (float) (Minecraft.getInstance().getFrameTimeNs() / 20000000000L);
        float f = Mth.rotLerp(pTicks, entity.yBodyRotO, entity.yBodyRot);
        float f1 = Mth.rotLerp(pTicks, entity.yHeadRotO, entity.yHeadRot);
        float netHeadYaw = f1 - f;
        float netHeadPitch = Mth.lerp(pTicks, entity.xRotO, entity.getXRot());
        WornArmorModel model = this.model.get();
        model.slot = armorSlot;
        model.copyFromDefault(_default);
        model.setupAnim(entity, entity.walkAnimation.position(), entity.walkAnimation.speed(), entity.tickCount + pTicks, netHeadYaw, netHeadPitch);
        return model;
    }

    public static void register(RegisterClientExtensionsEvent event, Holder<KrystalArmorItem> armorItemHolder) {
        KrystalArmorItem armorItem = armorItemHolder.value();
        event.registerItem(new ArmorClientItemExtensions(armorItem::getModel), armorItemHolder.value());
        KrystalCore.LOGGER.debug("Registered Client Extension for item: {}", armorItemHolder.getRegisteredName());
    }
}
