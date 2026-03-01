package com.vomiter.beneathdelight.mixin.farming;

import com.eerussianguy.beneath.common.blockentities.SoulFarmlandBlockEntity;
import com.soytutta.mynethersdelight.common.block.ResurgentSoilFarmlandBlock;
import net.dries007.tfc.common.blockentities.CropBlockEntity;
import net.dries007.tfc.common.blockentities.TickCounterBlockEntity;
import net.dries007.tfc.common.blocks.soil.HoeOverlayBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.IPlantable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(value = ResurgentSoilFarmlandBlock.class, remap = false)
public class ResurgentFarmlandMixin extends Block implements HoeOverlayBlock, EntityBlock {
    ResurgentFarmlandMixin(Properties p_49795_) {
        super(p_49795_);
    }

    @Inject(method = "performBonemealIfPossible", at = @At("HEAD"), cancellable = true)
    private void preventBoneMeal(Block block, BlockPos position, BlockState state, ServerLevel level, int distance, CallbackInfo ci){
        var blockentity = level.getBlockEntity(position);
        if(blockentity instanceof CropBlockEntity) ci.cancel();
        if(blockentity instanceof TickCounterBlockEntity) ci.cancel();
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new SoulFarmlandBlockEntity(pos, state);
    }

    @Override
    public void addHoeOverlayInfo(Level level, @NotNull BlockPos blockPos, @NotNull BlockState blockState, @NotNull List<Component> list, boolean b) {
        BlockEntity var7 = level.getBlockEntity(blockPos);
        if (var7 instanceof SoulFarmlandBlockEntity farmland) {
            farmland.addTooltipInfo(list);
        }
    }

    @Inject(method = "canSustainPlant", at = @At("HEAD"), cancellable = true)
    private void doNotSustainWart(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantable, CallbackInfoReturnable<Boolean> cir){
        if(plantable instanceof NetherWartBlock) cir.setReturnValue(false);
    }

}
