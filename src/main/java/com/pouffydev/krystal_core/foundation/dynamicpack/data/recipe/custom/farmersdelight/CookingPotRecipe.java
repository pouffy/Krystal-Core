package com.pouffydev.krystal_core.foundation.dynamicpack.data.recipe.custom.farmersdelight;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import com.pouffydev.krystal_core.foundation.dynamicpack.data.recipe.custom.CustomRecipe;
import lombok.Getter;
import net.minecraft.core.NonNullList;
import net.minecraft.tags.TagKey;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CookingPotRecipe extends CustomRecipe<RecipeWrapper> {
    private CookingPotRecipeBookTab tab;
    private final NonNullList<Ingredient> ingredients = NonNullList.create();
    private Item result;
    private ItemStack resultStack;
    private int cookingTime = 200;
    private float experience = 0.0F;
    private ItemStack container = ItemStack.EMPTY;
    private String group = "";

    private final @Getter List<ICondition> conditions = new ArrayList<>();

    public CookingPotRecipe() {}

    public CookingPotRecipe(ItemLike result, int count, int cookingTime, float experience, @Nullable ItemLike container) {
        this(new ItemStack(result, count), cookingTime, experience, container);
    }

    public CookingPotRecipe(ItemStack resultIn, int cookingTime, float experience, @Nullable ItemLike container) {
        this.result = resultIn.getItem();
        this.resultStack = resultIn;
        this.cookingTime = cookingTime;
        this.experience = experience;
        this.container = container != null ? new ItemStack(container) : ItemStack.EMPTY;
        this.tab = null;
    }

    public static CookingPotRecipe cookingPotRecipe(ItemLike mainResult, int count, int cookingTime, float experience) {
        return new CookingPotRecipe(mainResult, count, cookingTime, experience, null);
    }

    public static CookingPotRecipe cookingPotRecipe(ItemLike mainResult, int count, int cookingTime, float experience, ItemLike container) {
        return new CookingPotRecipe(mainResult, count, cookingTime, experience, container);
    }

    public CookingPotRecipe addIngredient(TagKey<Item> tagIn) {
        return addIngredient(Ingredient.of(tagIn));
    }

    public CookingPotRecipe addIngredient(ItemLike itemIn) {
        return addIngredient(itemIn, 1);
    }

    public CookingPotRecipe addIngredient(ItemLike itemIn, int quantity) {
        for (int i = 0; i < quantity; ++i) {
            addIngredient(Ingredient.of(itemIn));
        }
        return this;
    }

    public CookingPotRecipe addIngredient(Ingredient ingredientIn) {
        return addIngredient(ingredientIn, 1);
    }

    public CookingPotRecipe addIngredient(Ingredient ingredientIn, int quantity) {
        for (int i = 0; i < quantity; ++i) {
            ingredients.add(ingredientIn);
        }
        return this;
    }

    public CookingPotRecipe setRecipeBookTab(CookingPotRecipeBookTab tab) {
        this.tab = tab;
        return this;
    }

    public CookingPotRecipe group(String group) {
        this.group = group;
        return this;
    }

    public CookingPotRecipe withCondition(ICondition condition) {
        conditions.add(condition);
        return this;
    }

    @Override
    public JsonObject serialize() {
        JsonObject json = new JsonObject();
        json.addProperty("type", "farmersdelight:cooking");
        json.addProperty("group", this.group);
        json.addProperty("recipe_book_tab", this.tab.getSerializedName());
        Ingredient.LIST_CODEC_NONEMPTY.encodeStart(JsonOps.INSTANCE, this.ingredients).result().ifPresent((ingredients) -> json.add("ingredients", ingredients));
        ItemStack.STRICT_CODEC.encodeStart(JsonOps.INSTANCE, this.resultStack).result().ifPresent((result) -> json.add("result", result));
        if (!this.container.isEmpty()) {
            ItemStack.STRICT_CODEC.encodeStart(JsonOps.INSTANCE, this.container).result().ifPresent((container) -> json.add("container", container));
        }
        json.addProperty("experience", this.experience);
        json.addProperty("cookingtime", this.cookingTime);
        return json;
    }

    public enum CookingPotRecipeBookTab implements StringRepresentable {
        MEALS, DRINKS, MISC;

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }
}
