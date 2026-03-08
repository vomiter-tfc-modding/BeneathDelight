package com.vomiter.beneathdelight;

import com.mojang.logging.LogUtils;
import com.vomiter.beneathdelight.client.ItemPredicate;
import com.vomiter.beneathdelight.common.event.EventHandler;
import com.vomiter.beneathdelight.common.food.ExtraNutrients;
import com.vomiter.beneathdelight.compat.ValidBlockEntityExpansion;
import com.vomiter.beneathdelight.data.ModDataGenerator;
import com.vomiter.beneathdelight.registry.ModRegistries;
import com.vomiter.survivorsdelight.common.food.FoodContainerExpansion;
import com.vomiter.survivorsdelight.data.food.SDFoodAndRecipeGenerator;
import net.dries007.tfc.common.items.TFCItems;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.slf4j.Logger;

@Mod(BeneathDelight.MOD_ID)
public class BeneathDelight
{
    //DONE: stove compat

    //TODO: decaying
    /*
    * TODO: DECAY BLOCK ENTITY
    *  [X] STUFFED HOGLIN
    *  [X] MAGMA CAKE
    *  [X] BREAD LOAF
    *  [X] STRIDER LOAF
    *  [X] COLD STRIDER LOAF
     */
    //TODO: [X] brick cabinet
    //TODO: [X] make hoglins drop loin
    //TODO: [X] Hot cream, make "red/blue steel bucket" able to replace the bucket as container
    //TODO: [X] make soul rich soil support mushroom
    //TODO: [X] make resurgent farm valid to grow nether crop in overworld
    //TODO: [X] register cooked fungus to be food
    //TODO: [ ] make wart not plantable on resurgent soil

    //TODO: hoglin trophy gold ingot -> brass mechanism




    public static final String MOD_ID = "beneathdelight";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final SDFoodAndRecipeGenerator foodAndCookingGenerator = new SDFoodAndRecipeGenerator(MOD_ID);

    public BeneathDelight(FMLJavaModLoadingContext context) {
        EventHandler.init();
        IEventBus modBus = context.getModEventBus();
        modBus.addListener(this::commonSetup);
        modBus.addListener(ExtraNutrients::onCommonSetup);
        modBus.addListener(ModDataGenerator::generateData);
        ModRegistries.register(modBus);
        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        if(FMLEnvironment.dist.isClient()){
            modBus.addListener(this::onClientSetup);
        }
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        ValidBlockEntityExpansion.commonSetup(event);
        event.enqueueWork(() -> {
            FoodContainerExpansion.register(
                    Items.BUCKET,
                    (stack -> stack.is(TFCItems.RED_STEEL_BUCKET.get())
                            || stack.is(TFCItems.BLUE_STEEL_BUCKET.get())
                    )
            );
        });
    }

    public void onClientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemPredicate.addPredicate();
        });
    }


}
