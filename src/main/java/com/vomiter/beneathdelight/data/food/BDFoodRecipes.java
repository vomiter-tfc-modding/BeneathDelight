package com.vomiter.beneathdelight.data.food;

import com.soytutta.mynethersdelight.common.registry.MNDBlocks;
import com.soytutta.mynethersdelight.common.registry.MNDItems;
import com.soytutta.mynethersdelight.common.tag.MNDTags;
import com.vomiter.beneathdelight.BeneathDelight;
import com.vomiter.beneathdelight.Helpers;
import com.vomiter.beneathdelight.data.BDTags;
import com.vomiter.beneathdelight.registry.ModItems;
import com.vomiter.survivorsdelight.data.food.SDFoodAndRecipeGenerator;
import com.vomiter.survivorsdelight.data.food.SDFoodDataProvider;
import com.vomiter.survivorsdelight.data.tags.SDTags;
import com.vomiter.survivorsdelight.util.SDUtils;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.capabilities.food.FoodData;
import net.dries007.tfc.common.capabilities.food.Nutrient;
import net.dries007.tfc.common.items.Food;
import net.dries007.tfc.common.items.TFCItems;
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
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import vectorwing.farmersdelight.common.tag.ForgeTags;
import vectorwing.farmersdelight.data.builder.CuttingBoardRecipeBuilder;

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

        cook("ingredient/hot_cream", MNDItems.HOT_CREAM.get(), 1, 200, 0.5, Items.BUCKET)
                .food(Items.EGG)
                .food(ModItems.SWEETENED_MAGMA_CREAM.get())
                .food(ModItems.SWEETENED_MAGMA_CREAM.get())
                .nonfood(MNDTags.HOT_SPICE)
                .nonfood(MNDTags.HOT_SPICE)
                .fluid(lavas, 1000)
                .build(out)
                .saveFoodData();

        craft("cake/magma_cake", MNDBlocks.MAGMA_CAKE.get(), 1)
                .shape("mmm", "ghg", "sss")
                .defineFood('m', ModItems.SWEETENED_MAGMA_CREAM.get())
                .defineFood('h', MNDItems.HOT_CREAM.get())
                .defineNonFood('g', Items.GUNPOWDER)
                .defineNonFood('s', vectorwing.farmersdelight.common.registry.ModItems.STRAW.get())
                .build(out)
                .saveFoodData();

        craft("feast/ghasta_with_cream", MNDItems.GHASTA_WITH_CREAM_BLOCK.get(), 1)
                .shape("ggg", "gtc", "gbg")
                .defineFood('g', MNDTags.GHAST_MEATS)
                .defineNonFood('b', Items.BOWL)
                .defineNonFood('t', Items.GHAST_TEAR)
                .defineFood('c', ModItems.SWEETENED_MAGMA_CREAM.get())
                .build(out)
                .saveFoodData();


        cook("food/burnt_roll", MNDItems.BURNT_ROLL.get(), 2, 500, 0.5)
                .food(SDTags.ItemTags.TFC_RAW_MEATS)
                .nonfood(Items.MAGMA_CREAM)
                .nonfood(Items.MAGMA_CREAM)
                .build(out)
                .saveFoodData();

        cook("food/deviled_egg", MNDItems.DEVILED_EGG.get(), 2, 500, 1)
                .food(Food.BOILED_EGG)
                .nonfood(MNDTags.HOT_SPICE)
                .food(Ingredient.of(MNDItems.HOGLIN_SAUSAGE.get()))
                .build(out)
                .saveFoodData();

        cook("food/scotch_eggs", MNDItems.SCOTCH_EGGS.get(), 1, 600, 1, Items.BOWL)
                .food(Food.BOILED_EGG)
                .food(Food.BOILED_EGG)
                .food(Ingredient.of(MNDItems.HOGLIN_SAUSAGE.get(), vectorwing.farmersdelight.common.registry.ModItems.MINCED_BEEF.get()))
                .food(bread)
                .build(out)
                .saveFoodData();


        craft("food/ghast_salad", MNDItems.GHAST_SALAD.get(), 1)
                .row("gs")
                .defineFood('g', MNDTags.GHAST_MEATS)
                .defineFood('s', TFCItems.SALADS.get(Nutrient.VEGETABLES).get())
                .build(out)
                .saveFoodData();

        cook("food/spicy_noodle_soup", MNDItems.SPICY_NOODLE_SOUP.get(), 1, 600, 1, Items.BOWL)
                .food(MNDItems.GHASTA.get())
                .food(Items.EGG)
                .nonfood(MNDItems.BULLET_PEPPER.get())
                .food(vectorwing.farmersdelight.common.registry.ModItems.BONE_BROTH.get())
                .food(MNDTags.RAW_HOGLIN)
                .build(out)
                .saveFoodData();

        cook("food/twisted_ghasta", MNDItems.TWISTED_GHASTA.get(), 1, 600, 1, Items.BOWL)
                .food(MNDItems.GHASTA.get())
                .food(ModItems.BOILED_WARPED_FUNGUS.get())
                .food(ModItems.BOILED_WARPED_FUNGUS.get())
                .fluid(oils, 100)
                .build(out)
                .saveFoodData();


        cook("food/fried_gasta", MNDItems.FRIES_GHASTA.get(), 1, 600, 0.5, Items.PAPER)
                .food(MNDItems.GHASTA.get())
                .food(MNDItems.GHASTA.get())
                .fluid(oils, 50)
                .build(out)
                .saveFoodData();

        craft("food/spicy_cutton", MNDItems.SPICY_COTTON.get(), 1)
                .row("gp")
                .row("rg")
                .defineFood('g', MNDItems.GHASTA.get())
                .defineNonFood('p', MNDItems.BULLET_PEPPER.get())
                .defineNonFood('r', Items.BLAZE_ROD)
                .build(out)
                .saveFoodData();

        cook("food/giant_takoyaki", MNDItems.GIANT_TAKOYAKI.get(), 1, 600, 0.5, Items.BOWL)
                .food(MNDItems.GHASTA.get())
                .food(MNDItems.GHASTA.get())
                .food(SDTags.ItemTags.TFC_DOUGHS)
                .food(SDUtils.getTFCFoodItem(Food.ONION))
                .fluid(oils, 100)
                .build(out)
                .saveFoodData();

        craft("food/bacon_wrapped_sausage_on_a_stick", MNDItems.BACON_WRAPPED_SAUSAGE_STICK.get(), 1)
                .row("Sb")
                .row("s ")
                .defineFood('S', MNDItems.ROASTED_SAUSAGE.get())
                .defineFood('b', vectorwing.farmersdelight.common.registry.ModItems.COOKED_BACON.get())
                .defineNonFood('s', Items.STICK)
                .build(out)
                .saveFoodData();

        craft("food/blue_tenderloin_steak", MNDItems.BLUE_TENDERLOIN_STEAK.get(), 1)
                .row("lw")
                .row("rb")
                .defineFood('l', MNDItems.COOKED_LOIN.get())
                .defineFood('w', ModItems.BOILED_WARPED_FUNGUS.get())
                .defineFood('r', ModItems.BOILED_WARPED_ROOTS.get())
                .defineNonFood('b', Items.BOWL)
                .build(out)
                .saveFoodData();

        craft("food/red_loin_on_a_stick", MNDItems.RED_LOIN_STICK.get(), 1)
                .row("lc")
                .row("rs")
                .defineFood('l', MNDItems.COOKED_LOIN.get())
                .defineFood('c', ModItems.BOILED_CRIMSON_FUNGUS.get())
                .defineFood('r', Items.RED_MUSHROOM)
                .defineNonFood('s', Items.STICK)
                .build(out)
                .saveFoodData();

        cook("food/egg_soup", MNDItems.EGG_SOUP.get(), 1, 600, 1, Items.BOWL)
                .food(vectorwing.farmersdelight.common.registry.ModItems.BONE_BROTH.get())
                .food(Items.EGG)
                .food(Items.EGG)
                .food(SDUtils.getTFCFoodItem(Food.ONION))
                .build(out)
                .saveFoodData();

        cook("food/spicy_curry", MNDItems.SPICY_CURRY.get(), 1, 900, 1, Items.BOWL)
                .food(SDUtils.getTFCFoodItem(Food.RICE))
                .food(SDTags.ItemTags.TFC_VEGETABLES)
                .food(SDUtils.getTFCFoodItem(Food.PUMPKIN_CHUNKS))
                .food(SDTags.ItemTags.TFC_RAW_MEATS)
                .nonfood(MNDItems.BULLET_PEPPER.get())
                .nonfood(MNDItems.BULLET_PEPPER.get())
                .fluid(milks, 100).build(out).saveFoodData();


        cook("food/hot_wings", MNDItems.HOT_WINGS.get(), 1, 900, 1, Items.BOWL)
                .food(SDTags.ItemTags.RAW_POULTRY)
                .food(SDUtils.getTFCFoodItem(Food.ONION))
                .nonfood(MNDItems.BULLET_PEPPER.get())
                .fluid(oils, 100)
                .build(out).saveFoodData();

        cook("food/spicy_hoglin_stew_from_cooked", MNDItems.SPICY_HOGLIN_STEW.get(), 1, 900, 1, Items.BOWL)
                .food(MNDTags.COOKED_HOGLIN)
                .food(SDUtils.getTFCFoodItem(Food.CARROT))
                .food(SDUtils.getTFCFoodItem(Food.POTATO))
                .food(SDUtils.getTFCFoodItem(Food.ONION))
                .nonfood(MNDItems.BULLET_PEPPER.get())
                .build(out)
                .saveFoodData();


        cook("food/spicy_hoglin_stew", MNDItems.SPICY_HOGLIN_STEW.get(), 1, 900, 1, Items.BOWL)
                .food(MNDTags.RAW_HOGLIN)
                .food(SDUtils.getTFCFoodItem(Food.CARROT))
                .food(SDUtils.getTFCFoodItem(Food.POTATO))
                .food(SDUtils.getTFCFoodItem(Food.ONION))
                .nonfood(MNDItems.BULLET_PEPPER.get())
                .build(out)
                .saveFoodData();

        craft("food/spicy_skewer", MNDItems.SPICY_SKEWER.get(), 1)
                .row("ps")
                .row("rp")
                .defineFood('s', MNDTags.STRIDER_MEATS)
                .defineNonFood('p', MNDItems.BULLET_PEPPER.get())
                .defineFood('r', Items.BLAZE_ROD)
                .build(out)
                .saveFoodData();

        cook("food/stuffed_pepper", MNDItems.STUFFED_PEPPER.get(), 1, 200, 0.5)
                .food(ForgeTags.COOKED_PORK)
                .nonfood(MNDItems.BULLET_PEPPER.get())
                .fluid(milks, 100)
                .build(out)
                .saveFoodData();

        var provider = BeneathDelight.foodAndCookingGenerator.provider();
        provider.newBuilder("ingredient/ghasta")
                .item(MNDItems.GHASTA.get())
                .setDecay(0)
                .setProtein(0.5)
                .save();
        provider.newBuilder("ingredient/ghasmati")
                .item(MNDItems.GHASMATI.get())
                .setDecay(0)
                .setProtein(0.2)
                .save();
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

        provider.newBuilder("ingredient/sweetened_magma_cream")
                .item(ModItems.SWEETENED_MAGMA_CREAM.get())
                .from(Food.LEMON)
                .save();

        Map.of(
                ModItems.BOILED_CRIMSON_FUNGUS, Items.CRIMSON_FUNGUS,
                ModItems.BOILED_CRIMSON_FUNGUS_COLONY, MNDItems.CRIMSON_FUNGUS_COLONY.get(),
                ModItems.BOILED_CRIMSON_ROOTS, Items.CRIMSON_ROOTS,
                ModItems.BOILED_WARPED_FUNGUS, Items.WARPED_FUNGUS,
                ModItems.BOILED_WARPED_FUNGUS_COLONY, MNDItems.WARPED_FUNGUS_COLONY.get(),
                ModItems.BOILED_WARPED_ROOTS, Items.WARPED_ROOTS
        ).forEach((boiled, ingredient) -> {
            assert boiled.getId() != null;
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

        ShapelessRecipeBuilder.shapeless(
                        RecipeCategory.FOOD,
                        MNDItems.GHAST_SOURDOUGH.get())
                .requires(Ingredient.of(MNDItems.GHAST_DOUGH.get()))
                .requires(NotRottenIngredient.of(SDTags.ItemTags.TFC_DOUGHS))
                .requires(NotRottenIngredient.of(SDTags.ItemTags.TFC_DOUGHS))
                .requires(NotRottenIngredient.of(SDTags.ItemTags.TFC_DOUGHS))
                .unlockedBy(
                        "get_ghast_dough",
                        InventoryChangeTrigger.TriggerInstance.hasItems(MNDItems.GHAST_DOUGH.get())
                )
                .save(out, Helpers.id("ingredient/ghast_dough"));

        cook(
                "bread/breadloaf",
                MNDItems.BREAD_LOAF_BLOCK.get(),
                1, 200, 1
        ).food(MNDItems.GHAST_SOURDOUGH.get())
                .build(out)
                .getFoodData()
                .from(getTFCFoodData(Food.WHEAT_BREAD))
                .save();

        provider.newBuilder("ingredient/ghast_doughs")
                .ingredient(Ingredient.of(BDTags.GHAST_DOUGHS).toJson())
                .from(getTFCFoodData(Food.WHEAT_DOUGH))
                .setDecay(0)
                .save();

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, MNDItems.GHAST_DOUGH.get())
                .requires(MNDItems.GHASMATI.get())
                .requires(SDTags.ItemTags.TFC_DOUGHS)
                .unlockedBy(
                        "get_ghamati",
                        InventoryChangeTrigger.TriggerInstance.hasItems(MNDItems.GHASMATI.get())
                )
                .save(out, Helpers.id("crafting/ingredient/ghast_dough"));

        CuttingBoardRecipeBuilder.cuttingRecipe(
                        Ingredient.of(MNDItems.GHASTA.get()),
                        Ingredient.of(TFCTags.Items.KNIVES),
                        MNDItems.GHASMATI.get(), 1)
                .addResultWithChance(MNDItems.GHASMATI.get(), 0.5f, 1)
                .build(out, Helpers.id("cutting/ghasmati"));

        CuttingBoardRecipeBuilder.cuttingRecipe(
                        NotRottenIngredient.of(Ingredient.of(MNDItems.GHAST_SOURDOUGH.get())),
                        Ingredient.of(TFCTags.Items.KNIVES),
                        MNDItems.GHAST_DOUGH.get(), 3)
                .build(out, Helpers.id("cutting/ghast_dough"));

    }

}
