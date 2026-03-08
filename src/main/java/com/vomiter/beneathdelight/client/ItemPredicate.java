package com.vomiter.beneathdelight.client;

import com.soytutta.mynethersdelight.common.registry.MNDItems;
import com.vomiter.beneathdelight.Helpers;
import com.vomiter.beneathdelight.registry.ModItems;
import net.dries007.tfc.common.capabilities.food.FoodCapability;
import net.dries007.tfc.common.capabilities.food.FoodHandler;
import net.dries007.tfc.common.capabilities.food.IFood;
import net.dries007.tfc.common.items.Food;
import net.dries007.tfc.common.items.TFCItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class ItemPredicate {
    private static ResourceLocation id(){
        return Helpers.id("nether_burger");
    }

    public static void addPredicate(){
        final Item bread = TFCItems.FOOD.get(Food.WHEAT_BREAD_SANDWICH).get();
        ItemProperties.register(
                bread,
                id(),
                (stack, level, entity, seed) -> {
                    IFood food = FoodCapability.get(stack);
                    if(!(food instanceof FoodHandler.Dynamic dynamic)) return 0;
                    int crimsonCount = 0;
                    int warpedCount = 0;
                    int loinCount = 0;
                    for (ItemStack ingredient : dynamic.getIngredients()) {
                        if (Ingredient.of(ModItems.BOILED_CRIMSON_FUNGUS.get(), ModItems.BOILED_CRIMSON_ROOTS.get()).test(ingredient)) crimsonCount++;
                        else if(Ingredient.of(ModItems.BOILED_WARPED_FUNGUS.get(), ModItems.BOILED_WARPED_ROOTS.get()).test(ingredient)) warpedCount++;
                        else if(ingredient.is(MNDItems.COOKED_LOIN.get())) loinCount++;
                    }
                    if(loinCount > 0 && warpedCount > 0 && crimsonCount > 0) return 1;
                    return 0;
                }
        );
    }

}
