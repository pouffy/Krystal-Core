package com.pouffydev.krystal_core.foundation.dynamicpack.data.recipe.custom.create;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pouffydev.krystal_core.KrystalCore;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VatRecipe extends CreateRecipe {
    private List<String> machines = new ArrayList<>();
    private List<String> allowedVatTypes = types;
    private int minSize = 0;
    private int heatLevel = 0;
    private int pressure = 0;

    public static List<String> types = List.of(
            "tfmg:steel_vat",
            "tfmg:cast_iron_vat",
            "tfmg:firebrick_lined_vat"
    );

    @Override
    protected ResourceLocation getId() {
        return KrystalCore.location("tfmg:vat_machine_recipe");
    }

    @Override
    protected int getMaxInputCount() {
        return 4;
    }

    @Override
    protected int getMaxOutputCount() {
        return 4;
    }

    @Override
    protected int getMaxFluidInputCount() {
        return 4;
    }

    @Override
    protected int getMaxFluidOutputCount() {
        return 4;
    }

    @Override
    protected boolean canSpecifyDuration() {
        return true;
    }

    @Override
    protected boolean canRequireHeat() {
        return true;
    }

    public VatRecipe minSize(int minSize) {
        this.minSize = minSize;
        return this;
    }

    public VatRecipe heatLevel(int heatLevel) {
        this.heatLevel = heatLevel;
        return this;
    }

    public VatRecipe pressure(int pressure) {
        this.pressure = pressure;
        return this;
    }

    public VatRecipe machines(String... machines) {
        this.machines = Arrays.asList(machines);
        return this;
    }

    public VatRecipe allowedVatTypes(String... types) {
        this.allowedVatTypes = Arrays.asList(types);
        return this;
    }

    @Override
    public JsonObject serializeExtra(JsonObject jsonObject) {
        if (this.minSize > 0) jsonObject.addProperty("min_size", this.minSize);
        if (this.heatLevel > 0) jsonObject.addProperty("heat_level", this.heatLevel);
        if (this.pressure > 0) jsonObject.addProperty("pressure", this.pressure);
        if (!this.machines.isEmpty()) {
            JsonArray machines = new JsonArray();
            this.machines.forEach(machines::add);
            jsonObject.add("machines", machines);
        }
        if (!this.allowedVatTypes.equals(types)) {
            JsonArray vatTypes = new JsonArray();
            this.allowedVatTypes.forEach(vatTypes::add);
            jsonObject.add("allowed_vat_types", vatTypes);
        }
        return jsonObject;
    }
}
