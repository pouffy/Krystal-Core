package com.pouffydev.krystal_core.foundation.mixin.remainder;

import com.pouffydev.krystal_core.content.KrystalDataComponents;
import net.minecraft.core.component.DataComponentHolder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.MutableDataComponentHolder;
import net.neoforged.neoforge.common.extensions.IItemStackExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements DataComponentHolder, MutableDataComponentHolder, IItemStackExtension {

    @Shadow
    public abstract Item getItem();

    @Override
    public ItemStack getCraftingRemainingItem() {
        if (has(KrystalDataComponents.USE_REMAINDER)) {
            return get(KrystalDataComponents.USE_REMAINDER).remainder();
        }
        return getItem().getCraftingRemainingItem((ItemStack) (Object) this);
    }

    @Override
    public boolean hasCraftingRemainingItem() {
        if (has(KrystalDataComponents.USE_REMAINDER)) {
            return !get(KrystalDataComponents.USE_REMAINDER).remainder().isEmpty();
        }
        return getItem().hasCraftingRemainingItem((ItemStack) (Object) this);
    }
}
