package com.vomiter.beneathdelight.data.recipe;

import com.eerussianguy.beneath.common.items.BeneathItems;
import com.soytutta.mynethersdelight.common.registry.MNDBlocks;
import com.soytutta.mynethersdelight.common.registry.MNDItems;
import com.soytutta.mynethersdelight.common.tag.MNDTags;
import com.vomiter.beneathdelight.Helpers;
import com.vomiter.beneathdelight.registry.ModBlocks;
import com.vomiter.beneathdelight.registry.ModItems;
import com.vomiter.survivorsdelight.data.tags.SDTags;
import com.vomiter.survivorsdelight.util.SDUtils;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.items.TFCItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherWartBlock;
import vectorwing.farmersdelight.data.builder.CuttingBoardRecipeBuilder;

import java.util.function.Consumer;

import static com.vomiter.survivorsdelight.SurvivorsDelight.MODID;

public class ModNonFoodRecipes {
    public void save(Consumer<FinishedRecipe> out){
        misc(out);
    }

    void misc(Consumer<FinishedRecipe> out){
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MNDItems.NETHER_STOVE.get())
                .pattern("NIN")
                .pattern("BfB")
                .pattern("BnB")
                .define('B', TFCBlocks.FIRE_BRICKS.get())
                .define('I', TFCItems.WROUGHT_IRON_GRILL.get())
                .define('N', Blocks.NETHER_BRICKS)
                .define('n', Blocks.NETHERRACK)
                .define('f', MNDTags.STOVE_FIRE_FUEL)
                .unlockedBy("has_firestick", InventoryChangeTrigger.TriggerInstance.hasItems(TFCItems.FIRESTARTER.get()))
                .save(out, Helpers.id("crafting/misc/nether_stove"));

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.POWDERY_CABINET.get())
                .pattern("LLL")
                .pattern("T T")
                .pattern("LLL")
                .define('L', MNDBlocks.POWDERY_PLANKS_SLAB.get())
                .define('T', MNDBlocks.POWDERY_TRAPDOOR.get())
                .group(MODID + ":cabinet")
                .unlockedBy("has_powdery_trapdoor",
                        InventoryChangeTrigger.TriggerInstance.hasItems(MNDBlocks.POWDERY_TRAPDOOR.get()))
                .save(out, Helpers.id(
                        "crafting/cabinet/powdery"
                ));

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, MNDBlocks.HOGLIN_TROPHY.get())
                .pattern("LHL")
                .pattern("bBb")
                .pattern("LML")
                .define('L', SDUtils.TagUtils.itemTag("tfc", "lumber"))
                .define('H', MNDItems.HOGLIN_HIDE.get())
                .define('b', Items.BONE)
                .define('B', Blocks.BONE_BLOCK)
                .define('M', TFCItems.BRASS_MECHANISMS.get())
                .unlockedBy("has_hoglin_hide",
                        InventoryChangeTrigger.TriggerInstance.hasItems(MNDItems.HOGLIN_HIDE.get()))
                .save(out, Helpers.id("crafting/hoglin_trophy"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.SWEETENED_MAGMA_CREAM.get())
                .requires(SDTags.ItemTags.TFC_SWEETENER)
                .requires(SDTags.ItemTags.TFC_SWEETENER)
                .requires(SDTags.ItemTags.TFC_SWEETENER)
                .requires(Items.MAGMA_CREAM)
                .unlockedBy("has_magma_cream", InventoryChangeTrigger.TriggerInstance.hasItems(Items.MAGMA_CREAM))
                .save(out, Helpers.id("crafting/sweetened_magma_cream"));

        CuttingBoardRecipeBuilder.cuttingRecipe(
                Ingredient.of(MNDItems.POWDER_CANNON.get()),
                Ingredient.of(TFCTags.Items.KNIVES),
                Items.GUNPOWDER)
                .addResultWithChance(Items.GUNPOWDER, 0.25f)
                .build(out, Helpers.id("cutting/powdery_cannon"));

        CuttingBoardRecipeBuilder.cuttingRecipe(
                        Ingredient.of(MNDItems.HOGLIN_HIDE.get()),
                        Ingredient.of(SDUtils.TagUtils.itemTag("tfc", "shears")),
                        BeneathItems.CURSED_HIDE.get())
                .addResultWithChance(BeneathItems.CURSED_HIDE.get(), 0.25f)
                .build(out, Helpers.id("cutting/hoglin_hide"));

        CuttingBoardRecipeBuilder.cuttingRecipe(
                Ingredient.of(MNDItems.CRIMSON_FUNGUS_COLONY.get()),
                Ingredient.of(TFCTags.Items.KNIVES),
                Items.CRIMSON_FUNGUS,
                5
        ).build(out, Helpers.id("cutting/crimson_fungus_colony"));

        CuttingBoardRecipeBuilder.cuttingRecipe(
                Ingredient.of(MNDItems.WARPED_FUNGUS_COLONY.get()),
                Ingredient.of(TFCTags.Items.KNIVES),
                Items.WARPED_FUNGUS,
                5
        ).build(out, Helpers.id("cutting/warped_fungus_colony"));


    }
}
