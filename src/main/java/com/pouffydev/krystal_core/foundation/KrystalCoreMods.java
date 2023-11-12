package com.pouffydev.krystal_core.foundation;

import com.pouffydev.krystal_core.foundation.data.lang.KrystalCoreLang;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

public enum KrystalCoreMods {
    create("create"),
    milkyway("mw_core"),
    the_edge("the_edge"),
    bundled_delights("bundledelight"),
    arcanus("create_arcanus"),
    ;
    private final String id;
    
    public boolean reversedMetalPrefix;
    public boolean strippedIsSuffix;
    public boolean omitWoodSuffix;
    
    private KrystalCoreMods(String id) {
        this(id, b -> {
        });
    }
    
    private KrystalCoreMods(String id, Consumer<Builder> props) {
        props.accept(new Builder());
        this.id = id;
    }
    
    public ResourceLocation ingotOf(String type) {
        return new ResourceLocation(id, reversedMetalPrefix ? "ingot_" + type : type + "_ingot");
    }
    
    public ResourceLocation nuggetOf(String type) {
        return new ResourceLocation(id, reversedMetalPrefix ? "nugget_" + type : type + "_nugget");
    }
    
    public ResourceLocation oreOf(String type) {
        return new ResourceLocation(id, reversedMetalPrefix ? "ore_" + type : type + "_ore");
    }
    
    public ResourceLocation deepslateOreOf(String type) {
        return new ResourceLocation(id, reversedMetalPrefix ? "deepslate_ore_" + type : "deepslate_" + type + "_ore");
    }
    
    public ResourceLocation asResource(String id) {
        return new ResourceLocation(this.id, id);
    }
    
    public String recipeId(String id) {
        return "compat/" + this.id + "/" + id;
    }
    
    public String getId() {
        return id;
    }
    public Component getDisplayName() {
        return KrystalCoreLang.translateDirect("mod." + id);
    }
    
    class Builder {
        
        Builder reverseMetalPrefix() {
            reversedMetalPrefix = true;
            return this;
        }
        
        Builder strippedWoodIsSuffix() {
            strippedIsSuffix = true;
            return this;
        }
        
        Builder omitWoodSuffix() {
            omitWoodSuffix = true;
            return this;
        }
        
    }
}
