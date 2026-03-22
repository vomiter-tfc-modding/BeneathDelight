package com.vomiter.beneathdelight.common.food;

import com.soytutta.mynethersdelight.common.registry.MNDItems;
import com.vomiter.beneathdelight.Helpers;
import com.vomiter.survivorsdelight.adapter.cooking_pot.CookingPotExtraNutrientRules;
import com.vomiter.survivorsdelight.util.SDUtils;
import net.dries007.tfc.common.capabilities.food.FoodCapability;
import net.dries007.tfc.common.capabilities.food.Nutrient;
import net.dries007.tfc.common.items.Food;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ExtraNutrients {
    public static void onCommonSetup(FMLCommonSetupEvent event){
        event.enqueueWork(() -> {
            CookingPotExtraNutrientRules.register(
                    Helpers.id("stuffed_hoglin"),
                    2000,
                    new CookingPotExtraNutrientRules.SimpleRule(
                            (level, stack, nutrient, data) ->
                                    stack.is(MNDItems.RAW_STUFFED_HOGLIN.get())
                                    && (nutrient.equals(Nutrient.PROTEIN) || nutrient.equals(Nutrient.VEGETABLES))
                            , (level, stack, nutrient, data) -> {
                                if(nutrient.equals(Nutrient.PROTEIN)) return data.protein() * 0.6f;
                                else if(nutrient.equals(Nutrient.VEGETABLES)) return data.vegetables() * 1f;
                                return 0;
                            }
                    )
            );

            CookingPotExtraNutrientRules.register(
                    Helpers.id("ghast_sourdough"),
                    2000,
                    new CookingPotExtraNutrientRules.SimpleRule(
                            (level, stack, nutrient, data) ->
                                    stack.is(MNDItems.GHAST_SOURDOUGH.get())
                                            && (nutrient.equals(Nutrient.GRAIN))
                            , (level, stack, nutrient, data) -> {
                        if(nutrient.equals(Nutrient.GRAIN))
                            return FoodCapability.get(new ItemStack(SDUtils.getTFCFoodItem(Food.WHEAT_BREAD))).getData().grain();
                        return 0;
                    })
            );

        });
    }
}
