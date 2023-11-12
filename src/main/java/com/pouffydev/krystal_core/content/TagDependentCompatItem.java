package com.pouffydev.krystal_core.content;

import com.pouffydev.krystal_core.foundation.data.lang.KrystalCoreLang;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITagManager;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Creates an item that is hidden if the tag is not present.
 * <p>
 *     This is mainly used for compatibility with other mods in Krystal's Material Compats.
 * <p>
 *  This is a modified version of the TagDependentIngredientItem class from Create which only hides the item if the tag is not present.
 *  <p>
 *      If a tag is not present, the item will be shown with a red tooltip indicating the tag required.
 */
public class TagDependentCompatItem extends Item {
    private TagKey<Item> tag;
    
    public TagDependentCompatItem(Properties properties, TagKey<Item> tag) {
        super(properties);
        this.tag = tag;
    }
    
    @Override
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> list) {
        if (!shouldHide())
            super.fillItemCategory(tab, list);
    }
    
    public boolean shouldHide() {
        ITagManager<Item> tagManager = ForgeRegistries.ITEMS.tags();
        return !tagManager.isKnownTagName(tag) || tagManager.getTag(tag).isEmpty();
    }
    public String getTagID () {
        return tag.location().toString();
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        if (shouldHide()) {
            tooltip.add(KrystalCoreLang.translateDirect("item.missing_tag", getTagID()).withStyle(ChatFormatting.DARK_RED));
        }
        
    }
}
