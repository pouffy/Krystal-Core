package com.pouffydev.krystal_core.foundation.data.lang;

import com.pouffydev.krystal_core.KrystalCore;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class KrystalCoreLang {
    /**
     * legacy-ish. Use Lang.translate and other builder methods where possible
     *
     * @param key
     * @param args
     * @return
     */
    public static MutableComponent translateDirect(String key, Object... args) {
        return Components.translatable(KrystalCore.ID + "." + key, resolveBuilders(args));
    }
    public static MutableComponent translateDirect(String namespace, String key, Object... args) {
        return Components.translatable(namespace + "." + key, resolveBuilders(args));
    }
    
    public static String asId(String name) {
        return name.toLowerCase(Locale.ROOT);
    }
    
    public static String nonPluralId(String name) {
        String asId = asId(name);
        return asId.endsWith("s") ? asId.substring(0, asId.length() - 1) : asId;
    }
    
    public static List<Component> translatedOptions(String prefix, String... keys) {
        List<Component> result = new ArrayList<>(keys.length);
        for (String key : keys)
            result.add(translate((prefix != null ? prefix + "." : "") + key).component());
        return result;
    }
    
    //
    
    public static KrystalCoreLangBuilder builder() {
        return new KrystalCoreLangBuilder(KrystalCore.ID);
    }
    
    public static KrystalCoreLangBuilder builder(String namespace) {
        return new KrystalCoreLangBuilder(namespace);
    }
    
    //
    
    public static KrystalCoreLangBuilder blockName(BlockState state) {
        return builder().add(state.getBlock()
                .getName());
    }
    
    public static KrystalCoreLangBuilder itemName(ItemStack stack) {
        return builder().add(stack.getHoverName()
                .copy());
    }
    
    public static KrystalCoreLangBuilder fluidName(FluidStack stack) {
        return builder().add(stack.getDisplayName()
                .copy());
    }
    
    public static KrystalCoreLangBuilder number(double d) {
        return builder().text(LangNumberFormat.format(d));
    }
    
    public static KrystalCoreLangBuilder translate(String langKey, Object... args) {
        return builder().translate(langKey, args);
    }
    
    public static KrystalCoreLangBuilder translate(String namespace, String langKey, Object... args) {
        return builder(namespace).translate(langKey, args);
    }
    
    public static KrystalCoreLangBuilder text(String text) {
        return builder().text(text);
    }
    
    //
    
    public static Object[] resolveBuilders(Object[] args) {
        for (int i = 0; i < args.length; i++)
            if (args[i]instanceof KrystalCoreLangBuilder cb)
                args[i] = cb.component();
        return args;
    }
}
