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

public class MonsterCookingRecipe extends CustomRecipe<RecipeWrapper> {
    private String group = "";
    private MonsterPotRecipeBookTab tab = MonsterPotRecipeBookTab.MONSTER_MISC;
    private final NonNullList<Ingredient> ingredients = NonNullList.create();
    private ItemStack output;
    private ItemStack container = ItemStack.EMPTY;
    private float experience = 0.0F;
    private int cookingTime = 200;

    private final @Getter List<ICondition> conditions = new ArrayList<>();

    public MonsterCookingRecipe() {}

    public MonsterCookingRecipe(ItemLike result, int count, int cookingTime, float experience, @Nullable ItemLike container) {
        this(new ItemStack(result, count), cookingTime, experience, container);
    }

    public MonsterCookingRecipe(ItemStack resultIn, int cookingTime, float experience, @Nullable ItemLike container) {
        this.output = resultIn;
        this.cookingTime = cookingTime;
        this.experience = experience;
        this.container = container != null ? new ItemStack(container) : ItemStack.EMPTY;
        this.tab = null;
    }

    public static MonsterCookingRecipe monsterCookingPotRecipe(ItemLike mainResult, int count, int cookingTime, float experience) {
        return new MonsterCookingRecipe(mainResult, count, cookingTime, experience, null);
    }

    public static MonsterCookingRecipe  monsterCookingPotRecipe(ItemLike mainResult, int count, int cookingTime, float experience, ItemLike container) {
        return new MonsterCookingRecipe(mainResult, count, cookingTime, experience, container);
    }

    public MonsterCookingRecipe addIngredient(TagKey<Item> tagIn) {
        return addIngredient(Ingredient.of(tagIn));
    }

    public MonsterCookingRecipe addIngredient(ItemLike itemIn) {
        return addIngredient(itemIn, 1);
    }

    public MonsterCookingRecipe addIngredient(ItemLike itemIn, int quantity) {
        for (int i = 0; i < quantity; ++i) {
            addIngredient(Ingredient.of(itemIn));
        }
        return this;
    }

    public MonsterCookingRecipe addIngredient(Ingredient ingredientIn) {
        return addIngredient(ingredientIn, 1);
    }

    public MonsterCookingRecipe addIngredient(Ingredient ingredientIn, int quantity) {
        for (int i = 0; i < quantity; ++i) {
            ingredients.add(ingredientIn);
        }
        return this;
    }

    public MonsterCookingRecipe setRecipeBookTab(MonsterPotRecipeBookTab tab) {
        this.tab = tab;
        return this;
    }

    public MonsterCookingRecipe group(String group) {
        this.group = group;
        return this;
    }

    public MonsterCookingRecipe withCondition(ICondition condition) {
        conditions.add(condition);
        return this;
    }

    @Override
    public JsonObject serialize() {
        JsonObject json = new JsonObject();
        json.addProperty("type", "dungeonsdelight:monster_cooking");
        json.addProperty("group", this.group);
        json.addProperty("recipe_book_tab", this.tab.getSerializedName());
        Ingredient.LIST_CODEC_NONEMPTY.encodeStart(JsonOps.INSTANCE, this.ingredients).result().ifPresent((ingredients) -> json.add("ingredients", ingredients));
        ItemStack.STRICT_CODEC.encodeStart(JsonOps.INSTANCE, this.output).result().ifPresent((result) -> json.add("result", result));
        if (!this.container.isEmpty()) {
            ItemStack.STRICT_CODEC.encodeStart(JsonOps.INSTANCE, this.container).result().ifPresent((container) -> json.add("container", container));
        }
        json.addProperty("experience", this.experience);
        json.addProperty("cookingtime", this.cookingTime);
        return json;
    }

    public enum MonsterPotRecipeBookTab implements StringRepresentable {
        MONSTER_MEALS,
        MONSTER_DRINKS,
        MONSTER_MISC;

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }
}
