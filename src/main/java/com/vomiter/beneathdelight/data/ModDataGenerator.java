package com.vomiter.beneathdelight.data;

import com.vomiter.beneathdelight.BeneathDelight;
import com.vomiter.beneathdelight.data.loot.ModLootTables;
import com.vomiter.beneathdelight.data.recipe.ModRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

public class ModDataGenerator {
    public static void generateData(GatherDataEvent event){
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper helper = event.getExistingFileHelper();
        generator.addProvider(event.includeClient(), new ModBlockStateProvider(output, helper));
        generator.addProvider(event.includeServer(), new ModLootTables(output));
        new ModTagProviders(event);
        generator.addProvider(event.includeServer(), new ModRecipeProvider(output));
        generator.addProvider(event.includeServer(), BeneathDelight.foodAndCookingGenerator.provider());
    }
}
