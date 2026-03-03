package com.vomiter.beneathdelight.mixin.farming;

import com.soytutta.mynethersdelight.common.block.ResurgentSoilBlock;
import com.vomiter.survivorsdelight.SDConfig;
import net.dries007.tfc.common.blockentities.TickCounterBlockEntity;
import net.dries007.tfc.common.blocks.wood.TFCSaplingBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.IPlantable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vectorwing.farmersdelight.common.Configuration;
import vectorwing.farmersdelight.common.utility.MathUtils;

@Mixin(value = ResurgentSoilBlock.class, remap = false)
public class ResurgentSoilMixin {

    @Inject(
            method = "placeBlock",
            at = @At("TAIL")
    )
    private void resetTimerWhenPropagate(Block block, ServerLevel level, BlockPos pos, CallbackInfo ci){
        if(level.getBlockEntity(pos) instanceof TickCounterBlockEntity tickCounter){
            tickCounter.resetCounter();
        }
    }

    @Inject(
            method = "performBonemealIfPossible",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/BonemealableBlock;performBonemeal(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/util/RandomSource;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V", remap = true),
            cancellable = true
    )
    private void tfc_tree_growth_boost(Block block, BlockPos pos, BlockState state, ServerLevel level, int distance, CallbackInfo ci){
        if(level.getBlockEntity(pos) instanceof TickCounterBlockEntity tickCounter){
            tickCounter.reduceCounter(-1L * SDConfig.COMMON.richSoilGrowthBoostTick.get());
            level.levelEvent(2005, pos, 0);
            level.sendBlockUpdated(pos, level.getBlockState(pos), level.getBlockState(pos), 3);
            ci.cancel();
        }
    }

    @Inject(
            method = "randomTick",
            at = @At(value = "TAIL"),
            cancellable = true,
            remap = true
    )
    private void random_tick_tail(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand, CallbackInfo ci){
        unbonemeable_growth_boost(state, level, pos, rand, ci);
    }

    @Unique
    private void unbonemeable_growth_boost(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand, CallbackInfo ci){
        if(level.getBlockState(pos.above()).getBlock() instanceof BonemealableBlock) return;
        if(level.getBlockEntity(pos.above()) instanceof TickCounterBlockEntity tickCounter){
            if ((double) MathUtils.RAND.nextFloat() <= Configuration.RICH_SOIL_BOOST_CHANCE.get()) {
                tickCounter.reduceCounter(-1L * SDConfig.COMMON.richSoilGrowthBoostTick.get());
                level.levelEvent(2005, pos.above(), 0);
                level.sendBlockUpdated(pos.above(), level.getBlockState(pos.above()), level.getBlockState(pos.above()), 3);
                ci.cancel();
            }
        }
    }

    @Inject(method = "canSustainPlant", at = @At("HEAD"), cancellable = true)
    private void doNotSustainWart(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantable, CallbackInfoReturnable<Boolean> cir){
        if(plantable instanceof NetherWartBlock) cir.setReturnValue(false);
    }

}
