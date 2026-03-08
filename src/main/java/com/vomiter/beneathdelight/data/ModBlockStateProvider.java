package com.vomiter.beneathdelight.data;

import com.soytutta.mynethersdelight.MyNethersDelight;
import com.vomiter.beneathdelight.BeneathDelight;
import com.vomiter.beneathdelight.Helpers;
import com.vomiter.beneathdelight.registry.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlockStateProvider extends BlockStateProvider {
    ExistingFileHelper existingFileHelper;
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, BeneathDelight.MOD_ID, exFileHelper);
        this.existingFileHelper =exFileHelper;

    }

    private void trackTexture(String namespace, String pathNoExt) {
        existingFileHelper.trackGenerated(
                Helpers.id(namespace, pathNoExt),
                PackType.CLIENT_RESOURCES,
                ".png",
                "textures"
        );
    }
    private void trackTexture(String pathNoExt){
        trackTexture(BeneathDelight.MOD_ID, pathNoExt);
    }

    private void trackModel(String namespace, String pathNoExt) {
        existingFileHelper.trackGenerated(
                Helpers.id(namespace, pathNoExt),
                PackType.CLIENT_RESOURCES,
                ".json",
                "models"
        );
    }
    private void trackModel(String pathNoExt){
        trackModel(BeneathDelight.MOD_ID, pathNoExt);
    }

    @Override
    protected void registerStatesAndModels() {

        registerMNDCabinet(ModBlocks.BLACKSTONE_BRICKS_CABINET.get());
        registerMNDCabinet(ModBlocks.RED_NETHER_BRICKS_CABINET.get());
        registerMNDCabinet(ModBlocks.NETHER_BRICKS_CABINET.get());
        registerMNDCabinet(ModBlocks.POWDERY_CABINET.get());
        trackModel(MyNethersDelight.MODID, "item/crimson_fungus_colony");
        trackModel(MyNethersDelight.MODID, "item/warped_fungus_colony");

        itemModels().withExistingParent("boiled_crimson_fungus", mcLoc("crimson_fungus"));
        itemModels().withExistingParent("boiled_warped_fungus", mcLoc("warped_fungus"));
        itemModels().withExistingParent("boiled_crimson_roots", mcLoc("crimson_roots"));
        itemModels().withExistingParent("boiled_warped_roots", mcLoc("warped_roots"));
        itemModels().withExistingParent("boiled_crimson_fungus_colony", Helpers.id(MyNethersDelight.MODID,"crimson_fungus_colony"));
        itemModels().withExistingParent("boiled_warped_fungus_colony", Helpers.id(MyNethersDelight.MODID,"warped_fungus_colony"));
    }

    private void registerMNDCabinet(Block block){

        ResourceLocation rl = ForgeRegistries.BLOCKS.getKey(block);
        assert rl != null;
        String basePath = rl.getPath();
        trackTexture(basePath + "_front");
        trackTexture(basePath + "_front_open");
        trackTexture(basePath + "_side");
        trackTexture(basePath + "_top");


        existingFileHelper.trackGenerated(
                Helpers.id(MyNethersDelight.MODID, "block/" + basePath),
                PackType.CLIENT_RESOURCES,
                ".json",
                "models"
        );
        existingFileHelper.trackGenerated(
                Helpers.id(MyNethersDelight.MODID, "block/" + basePath + "_open"),
                PackType.CLIENT_RESOURCES,
                ".json",
                "models"
        );
        existingFileHelper.trackGenerated(
                Helpers.id(MyNethersDelight.MODID, "item/" + basePath),
                PackType.CLIENT_RESOURCES,
                ".json",
                "models"
        );

        ModelFile.ExistingModelFile close = models().getExistingFile(
                Helpers.id(MyNethersDelight.MODID, "block/" + basePath));
        ModelFile.ExistingModelFile open = models().getExistingFile(
                Helpers.id(MyNethersDelight.MODID, "block/" + basePath + "_open"));
        itemModels().withExistingParent("item/" + basePath, Helpers.id(MyNethersDelight.MODID, basePath));
        getVariantBuilder(block).forAllStates(state -> {
            Direction dir = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            boolean isOpen = state.getValue(BlockStateProperties.OPEN);
            int y = yFromHorizontal(dir);
            ModelFile model = isOpen ? open : close;
            return ConfiguredModel.builder()
                    .modelFile(model)
                    .rotationY(y)
                    .build();
        });




    }

    private ModelFile orientableModel(String name, ResourceLocation front, ResourceLocation side, ResourceLocation top) {
        return models()
                .withExistingParent("block/" + name, mcLoc("block/orientable"))
                .texture("front", front)
                .texture("side", side)
                .texture("top",  top);
    }

    private static int yFromHorizontal(Direction dir) {
        return switch (dir) {
            case NORTH -> 0;
            case EAST  -> 90;
            case SOUTH -> 180;
            case WEST  -> 270;
            default -> 0;
        };
    }

}
