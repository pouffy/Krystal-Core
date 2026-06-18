package com.pouffydev.krystal_core.foundation.mixin.remainder;

import com.pouffydev.krystal_core.content.KrystalDataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.extensions.IItemExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;

@Mixin(Item.class)
public abstract class ItemMixin implements IItemExtension {

    @Shadow
    @Deprecated
    public abstract boolean hasCraftingRemainingItem();

    @Shadow
    @Deprecated
    @Nullable
    public abstract Item getCraftingRemainingItem();

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack stack) {
        if (!hasCraftingRemainingItem(stack)) {
            return ItemStack.EMPTY;
        }
        if (stack.has(KrystalDataComponents.USE_REMAINDER)) {
            return stack.get(KrystalDataComponents.USE_REMAINDER).remainder();
        }
        return new ItemStack(getCraftingRemainingItem());
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        if (stack.has(KrystalDataComponents.USE_REMAINDER)) {
            return !stack.get(KrystalDataComponents.USE_REMAINDER).remainder().isEmpty();
        }
        return hasCraftingRemainingItem();
    }
}
