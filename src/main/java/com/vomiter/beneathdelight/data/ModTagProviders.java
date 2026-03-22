package com.vomiter.beneathdelight.data;

import com.eerussianguy.beneath.common.blocks.BeneathBlockTags;
import com.soytutta.mynethersdelight.common.registry.MNDBlocks;
import com.soytutta.mynethersdelight.common.registry.MNDItems;
import com.soytutta.mynethersdelight.common.tag.MNDTags;
import com.vomiter.beneathdelight.BeneathDelight;
import com.vomiter.beneathdelight.Helpers;
import com.vomiter.beneathdelight.registry.ModItems;
import com.vomiter.survivorsdelight.data.tags.SDTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModTagProviders {
    DataGenerator generator;
    PackOutput output;
    CompletableFuture<HolderLookup.Provider> lookupProvider;
    ExistingFileHelper helper;
    public ModTagProviders(GatherDataEvent event){
        generator = event.getGenerator();
        output = generator.getPackOutput();
        lookupProvider = event.getLookupProvider();
        helper = event.getExistingFileHelper();

        var blockTags = new BlockTags();
        var itemTags = new ItemTags(blockTags);
        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(), itemTags);

    }


    class BlockTags extends BlockTagsProvider{

        public BlockTags() {
            super(output, lookupProvider, BeneathDelight.MOD_ID, helper);
        }

        @Override
        protected void addTags(HolderLookup.@NotNull Provider lookupProvider) {
            tag(BeneathBlockTags.NETHER_BUSH_PLANTABLE_ON).add(MNDBlocks.RESURGENT_SOIL.get());

        }
    }

    class ItemTags extends ItemTagsProvider {
        public ItemTags(BlockTags blockTags) {
            super(output, lookupProvider, blockTags.contentsGetter(), BeneathDelight.MOD_ID, helper);
        }
        static TagKey<Item> create(ResourceLocation id){
            return TagKey.create(
                    Registries.ITEM,
                    id
            );
        }

        @Override
        protected void addTags(HolderLookup.@NotNull Provider p_256380_) {
            tag(BDTags.GHAST_DOUGHS)
                    .add(MNDItems.GHAST_DOUGH.get())
                    .add(MNDItems.GHAST_SOURDOUGH.get());

            var usableInSandwich = create(Helpers.id("tfc", "foods/usable_in_sandwich"));
            tag(usableInSandwich).add(
                    ModItems.BOILED_CRIMSON_FUNGUS.get(),
                    ModItems.BOILED_CRIMSON_ROOTS.get(),
                    ModItems.BOILED_WARPED_FUNGUS.get(),
                    ModItems.BOILED_WARPED_ROOTS.get()
            );

            tag(SDTags.ItemTags.SLICES_AND_SERVINGS)
                    .add(
                            MNDItems.SLICES_OF_BREAD.get(),
                            MNDItems.MAGMA_CAKE_SLICE.get(),
                            MNDItems.PLATE_OF_STUFFED_HOGLIN_HAM.get(),
                            MNDItems.PLATE_OF_STUFFED_HOGLIN_SNOUT.get(),
                            MNDItems.PLATE_OF_STUFFED_HOGLIN.get(),
                            MNDItems.STRIDERLOAF.get(),
                            MNDItems.COLD_STRIDERLOAF.get()
                    );
        }
    }


}
