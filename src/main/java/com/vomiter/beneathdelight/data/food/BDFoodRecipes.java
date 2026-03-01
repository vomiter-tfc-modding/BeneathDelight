package com.vomiter.beneathdelight.data.food;

import com.soytutta.mynethersdelight.common.registry.MNDItems;
import com.soytutta.mynethersdelight.common.tag.MNDTags;
import com.vomiter.beneathdelight.BeneathDelight;
import com.vomiter.beneathdelight.registry.ModItems;
import com.vomiter.survivorsdelight.data.food.SDFoodAndRecipeGenerator;
import com.vomiter.survivorsdelight.data.food.SDFoodDataProvider;
import com.vomiter.survivorsdelight.data.tags.SDTags;
import com.vomiter.survivorsdelight.util.SDUtils;
import net.dries007.tfc.common.capabilities.food.FoodData;
import net.dries007.tfc.common.items.Food;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.registries.RegistryObject;

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
        provider.newBuilder("hoglin_loin")
                .item(MNDItems.HOGLIN_LOIN.get())
                .from(Food.PORK)
                .save();
        provider.newBuilder("cooked_hoglin_loin")
                .item(MNDItems.COOKED_LOIN.get())
                .from(Food.COOKED_PORK)
                .save();
        provider.newBuilder("hoglin_sausage")
                .item(MNDItems.HOGLIN_SAUSAGE.get())
                .slicedFrom(Food.PORK, 2)
                .save();
        provider.newBuilder("cooked_hoglin_sausage")
                .item(MNDItems.ROASTED_SAUSAGE.get())
                .slicedFrom(Food.COOKED_PORK, 2)
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
                    .setVegetables(0.5)
                    .setHunger(1)
                    .setSaturation(0.1)
                    .save();
        });


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
                .nonfood(Items.EGG)
                .food(MNDItems.BULLET_PEPPER.get())
                .fluid(milks, 100)
                .build(out)
                .saveFoodData();


    }

}
