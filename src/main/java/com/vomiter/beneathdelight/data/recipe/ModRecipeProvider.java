package com.vomiter.beneathdelight.data.recipe;

import com.vomiter.beneathdelight.BeneathDelight;
import com.vomiter.beneathdelight.data.food.BDFoodRecipes;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput p_248933_) {
        super(p_248933_);
        BeneathDelight.foodAndCookingGenerator.injectPackOutput(p_248933_);
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> out) {
        new ModNonFoodRecipes().save(out);
        new BDFoodRecipes().save(out);
    }
}
