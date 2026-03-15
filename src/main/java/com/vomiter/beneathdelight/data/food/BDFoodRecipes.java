package com.vomiter.beneathdelight.data.food;

import com.eerussianguy.beneath.common.items.BeneathItems;
import com.soytutta.mynethersdelight.common.registry.MNDBlocks;
import com.soytutta.mynethersdelight.common.registry.MNDItems;
import com.soytutta.mynethersdelight.common.tag.MNDTags;
import com.vomiter.beneathdelight.BeneathDelight;
import com.vomiter.beneathdelight.Helpers;
import com.vomiter.beneathdelight.registry.ModItems;
import com.vomiter.survivorsdelight.data.food.SDFoodAndRecipeGenerator;
import com.vomiter.survivorsdelight.data.food.SDFoodDataProvider;
import com.vomiter.survivorsdelight.data.tags.SDTags;
import com.vomiter.survivorsdelight.util.SDUtils;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.capabilities.food.FoodData;
import net.dries007.tfc.common.items.Food;
import net.dries007.tfc.common.recipes.ingredients.NotRottenIngredient;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.data.builder.CuttingBoardRecipeBuilder;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class BDFoodRecipes {
    TagKey<Fluid> milks = TagKey.create(Registries.FLUID, SDUtils.RLUtils.build("tfc", "milks"));
    TagKey<Fluid> oils = SDTags.FluidTags.COOKING_OILS;
    TagKey<Fluid> lavas = FluidTags.LAVA;
    TagKey<Item> cooked_sausage = SDUtils.TagUtils.itemTag("forge", "cooked_sausage");
    TagKey<Item> sweetener = SDTags.ItemTags.TFC_SWEETENER;
    TagKey<Item> bread = SDUtils.TagUtils.itemTag("tfc", "sandwich_bread");

    private SDFoodAndRecipeGenerator.CookingBuilder cook(String id, ItemLike outItem, int count, int time, double exp) {
        return BeneathDelight.foodAndCookingGenerator.cooking(id, outItem, count, time, (float) exp, null);
    }

    private SDFoodAndRecipeGenerator.CookingBuilder cook(String id, ItemLike outItem, int count, int time, double exp, ItemLike container) {
        return BeneathDelight.foodAndCookingGenerator.cooking(id, outItem, count, time, (float) exp, container);
    }

    private SDFoodAndRecipeGenerator.ShapedCraftingBuilder craft(String id, ItemLike outItem, int count){
        return BeneathDelight.foodAndCookingGenerator.crafting(id, outItem, count);
    }

    private SDFoodDataProvider.Builder buildFood(String id){
        return BeneathDelight.foodAndCookingGenerator.provider().newBuilder(id);
    }

    private FoodData getTFCFoodData(Food food){
        return BeneathDelight.foodAndCookingGenerator.provider().readTfcFoodJson(food);
    }

    private FoodData getOtherFoodData(Item food){
        return SDFoodAndRecipeGenerator.foodDataMap.get(food);
    }

    public void save(Consumer<FinishedRecipe> out){

        var provider = BeneathDelight.foodAndCookingGenerator.provider();
        provider.newBuilder("ingredient/hoglin_loin")
                .item(MNDItems.HOGLIN_LOIN.get())
                .from(Food.PORK)
                .save();
        provider.newBuilder("ingredient/cooked_hoglin_loin")
                .item(MNDItems.COOKED_LOIN.get())
                .from(Food.COOKED_PORK)
                .save();
        provider.newBuilder("ingredient/hoglin_sausage")
                .item(MNDItems.HOGLIN_SAUSAGE.get())
                .slicedFrom(Food.PORK, 2)
                .save();
        provider.newBuilder("ingredient/cooked_hoglin_sausage")
                .item(MNDItems.ROASTED_SAUSAGE.get())
                .slicedFrom(Food.COOKED_PORK, 2)
                .save();
        provider.newBuilder("food/roast_ear")
                .item(MNDItems.ROAST_EAR.get())
                .slicedFrom(Food.COOKED_PORK, 2)
                .save();

        provider.newBuilder("feast/striderloaf")
                .ingredient(Ingredient.of(MNDItems.STRIDERLOAF_BLOCK.get(), MNDItems.COLD_STRIDERLOAF_BLOCK.get()).toJson())
                .multipliedFrom(Food.MUTTON, 4)
                .save();

        provider.newBuilder("feast/striderloaf_serving")
                .ingredient(Ingredient.of(MNDItems.STRIDERLOAF.get(), MNDItems.COLD_STRIDERLOAF.get()).toJson())
                .from(Food.MUTTON)
                .save();

        provider.newBuilder("ingredient/strider_slice")
                .item(MNDItems.STRIDER_SLICE.get())
                .from(Food.MUTTON)
                .save();

        provider.newBuilder("ingredient/minced_strider")
                .item(MNDItems.MINCED_STRIDER.get())
                .slicedFrom(Food.MUTTON, 2)
                .save();

        Map.of(
                ModItems.BOILED_CRIMSON_FUNGUS, Items.CRIMSON_FUNGUS,
                ModItems.BOILED_CRIMSON_FUNGUS_COLONY, MNDItems.CRIMSON_FUNGUS_COLONY.get(),
                ModItems.BOILED_CRIMSON_ROOTS, Items.CRIMSON_ROOTS,
                ModItems.BOILED_WARPED_FUNGUS, Items.WARPED_FUNGUS,
                ModItems.BOILED_WARPED_FUNGUS_COLONY, MNDItems.WARPED_FUNGUS_COLONY.get(),
                ModItems.BOILED_WARPED_ROOTS, Items.WARPED_ROOTS
        ).forEach((boiled, ingredient) -> {
            cook("ingredient/" + boiled.getId().getPath(), boiled.get(), 1, 200, 1)
                    .nonfood(sweetener)
                    .nonfood(ingredient)
                    .fluid(Fluids.WATER, 100)
                    .build(out)
                    .getFoodData()
                    .type(null)
                    .setDecay(1)
                    .setVegetables(boiled.getId().getPath().contains("colony") ? 2.5: 0.5)
                    .setDairy(boiled.getId().getPath().contains("colony") ? 0.5: 0)
                    .setHunger(1)
                    .setSaturation(0.1)
                    .save();
        });

        var ham = vectorwing.farmersdelight.common.registry.ModItems.HAM.get();
        var warpedColony = ModItems.BOILED_WARPED_FUNGUS_COLONY.get();
        var crimsonColony = ModItems.BOILED_CRIMSON_FUNGUS_COLONY.get();
        var loin = MNDItems.HOGLIN_LOIN.get();

        craft("food/nether_salad", vectorwing.farmersdelight.common.registry.ModItems.NETHER_SALAD.get(), 1)
                .shape("CRW", " B ")
                .defineFood('R', Ingredient.of(ModItems.BOILED_CRIMSON_ROOTS.get(), ModItems.BOILED_WARPED_ROOTS.get()))
                .defineFood('C', Ingredient.of(ModItems.BOILED_CRIMSON_FUNGUS.get()))
                .defineFood('W', Ingredient.of(ModItems.BOILED_WARPED_FUNGUS.get()))
                .defineFood('B', Items.BOWL)
                .build(out)
                .saveFoodData();

        craft("ingredient/stuffed_hoglin", MNDItems.RAW_STUFFED_HOGLIN.get(), 1)
                .shape("hWh", "LHL", "hCh")
                .defineFood('h', ham, getOtherFoodData(ham))
                .defineFood('W', warpedColony, getOtherFoodData(warpedColony))
                .defineFood('C', crimsonColony, getOtherFoodData(crimsonColony))
                .defineFood('L', loin, getOtherFoodData(loin))
                .defineNonFood('H', MNDItems.HOGLIN_HIDE.get())
                .build(out)
                .saveFoodData();

        cook("feast/stuffed_hoglin", MNDBlocks.STUFFED_HOGLIN.get(), 1, 600, 10, Items.BOWL)
                .food(MNDItems.RAW_STUFFED_HOGLIN.get())
                .food(vectorwing.farmersdelight.common.registry.ModItems.NETHER_SALAD.get())
                .nonfood(MNDTags.HOT_SPICE)
                .fluid(oils, 500)
                .build(out)
                .getFoodData()
                .type("dynamic")
                .save();


        cook("food/sausage_and_potatoes", MNDItems.SAUSAGE_AND_POTATOES.get(), 1, 200, 1, Items.BOWL)
                .food(cooked_sausage)
                .food(cooked_sausage)
                .food(SDUtils.getTFCFoodItem(Food.POTATO))
                .fluid(oils, 100)
                .build(out)
                .saveFoodData();

        cook("food/breakfast_sampler", MNDItems.BREAKFAST_SAMPLER.get(), 1, 40, 1, Items.BOWL)
                .food(cooked_sausage)
                .food(cooked_sausage)
                .food(sweetener)
                .food(bread)
                .food(SDUtils.getTFCFoodItem(Food.COOKED_EGG))
                .food(SDUtils.getTFCFoodItem(Food.COOKED_EGG))
                .fluid(oils, 100)
                .build(out)
                .saveFoodData();

        cook("food/fried_hoglin_chop", MNDItems.FRIED_HOGLIN_CHOP.get(), 1, 200, 1, Items.BOWL)
                .food(MNDTags.LOIN_HOGLIN)
                .food(SDTags.ItemTags.TFC_DOUGHS)
                .food(Items.EGG)
                .nonfood(MNDTags.HOT_SPICE)
                .food(MNDItems.BULLET_PEPPER.get())
                .fluid(milks, 100)
                .build(out)
                .saveFoodData();

        CuttingBoardRecipeBuilder.cuttingRecipe(
                        NotRottenIngredient.of(Ingredient.of(MNDItems.STRIDER_SLICE.get())),
                        Ingredient.of(TFCTags.Items.KNIVES),
                        MNDItems.MINCED_STRIDER.get(), 2)
                .addResult(Items.STRING, 2)
                .addResultWithChance(Items.STRING, 0.5f, 2)
                .build(out, Helpers.id("cutting/strider_slice"));

        ShapelessRecipeBuilder.shapeless(
                RecipeCategory.FOOD,
                MNDItems.STRIDERLOAF_BLOCK.get())
                .requires(NotRottenIngredient.of(Ingredient.of(MNDItems.STRIDER_SLICE.get())))
                .requires(NotRottenIngredient.of(Ingredient.of(MNDItems.MINCED_STRIDER.get())))
                .requires(NotRottenIngredient.of(Ingredient.of(MNDItems.MINCED_STRIDER.get())))
                .requires(NotRottenIngredient.of(Ingredient.of(MNDItems.MINCED_STRIDER.get())))
                .requires(NotRottenIngredient.of(Ingredient.of(MNDItems.MINCED_STRIDER.get())))
                .requires(NotRottenIngredient.of(Ingredient.of(MNDItems.MINCED_STRIDER.get())))
                .requires(NotRottenIngredient.of(Ingredient.of(MNDItems.MINCED_STRIDER.get())))
                .requires(Items.BOWL)
                .unlockedBy(
                        "get_strider_slice",
                        InventoryChangeTrigger.TriggerInstance.hasItems(MNDItems.STRIDER_SLICE.get())
                )
                .save(out, Helpers.id("feast/striderloaf"));
        ;
    }

}
