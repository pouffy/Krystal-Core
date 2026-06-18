package com.pouffydev.krystal_core.foundation.dynamicpack.data.recipe.custom;

import static com.pouffydev.krystal_core.foundation.dynamicpack.data.recipe.custom.CreateRecipe.*;

public class RecipeTemplates {

    public static class Create {
        // Create
        public static CreateRecipe CRUSHING = new SimpleCreateRecipe("crushing", 1, 7, false, true);
        public static CreateRecipe CUTTING = new SimpleCreateRecipe("cutting", 1, 4, false, true);
        public static CreateRecipe MILLING = new SimpleCreateRecipe("milling", 1, 4, false, true);
        public static CreateRecipe MIXING = new SimpleCreateRecipe("mixing", 64, 4, true, true, 2, 2);
        public static CreateRecipe COMPACTING = new SimpleCreateRecipe("compacting", 64, 4, true, true, 2, 2);
        public static CreateRecipe PRESSING = new SimpleCreateRecipe("pressing", 1, 2);
        public static CreateRecipe SANDPAPER = new SimpleCreateRecipe("sandpaper_polishing", 1, 1);
        public static CreateRecipe SPLASHING = new SimpleCreateRecipe("splashing", 1, 12);
        public static CreateRecipe HAUNTING = new SimpleCreateRecipe("haunting", 1, 12);
        public static CreateRecipe FILLING = new SimpleCreateRecipe("filling", 1, 1, 1, 0);
        public static CreateRecipe EMPTYING = new SimpleCreateRecipe("emptying", 1, 1, 0, 1);
        public static CreateRecipe ITEM_APPLICATION = new ItemApplication();
        public static CreateRecipe DEPLOYING = new Deploying();
        // TFMG
        public static CreateRecipe CASTING = new SimpleCreateRecipe("tfmg:casting", 0, 1, false, true, 1, 0);
        public static CreateRecipe COKING = new SimpleCreateRecipe("tfmg:coking", 1, 1, false, true, 0, 2);
        public static CreateRecipe DISTILLATION = new SimpleCreateRecipe("tfmg:distillation", 0, 0, true, true, 1, 6);
        public static CreateRecipe HOT_BLAST = new SimpleCreateRecipe("tfmg:hot_blast", 0, 0, false, true, 2, 2);
        public static CreateRecipe INDUSTRIAL_BLASTING = new IndustrialBlasting();
        public static CreateRecipe POLARIZING = new SimpleCreateRecipe("tfmg:polarizing", 1, 1, false, true);
        public static CreateRecipe VAT_MACHINE = new VatRecipe();
        public static CreateRecipe WINDING = new SimpleCreateRecipe("tfmg:winding", 2, 1, false, true);
    }
}
