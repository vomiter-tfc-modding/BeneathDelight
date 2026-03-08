package com.vomiter.beneathdelight.common.food;

import com.soytutta.mynethersdelight.common.registry.MNDItems;
import com.vomiter.beneathdelight.Helpers;
import com.vomiter.survivorsdelight.adapter.cooking_pot.CookingPotExtraNutrientRules;
import net.dries007.tfc.common.capabilities.food.Nutrient;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ExtraNutrients {
    public static void onCommonSetup(FMLCommonSetupEvent event){
        event.enqueueWork(() -> {
            CookingPotExtraNutrientRules.register(
                    Helpers.id("special_dough_override"),
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
        });
    }
}
