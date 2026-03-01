package com.vomiter.beneathdelight.mixin.stove;

import com.soytutta.mynethersdelight.common.block.NetherStoveBlock;
import com.soytutta.mynethersdelight.common.block.entity.NetherStoveBlockEntity;
import com.vomiter.survivorsdelight.HeatSourceBlockEntity;
import com.vomiter.survivorsdelight.adapter.stove.IStoveBlockEntity;
import com.vomiter.survivorsdelight.compat.firmalife.StoveOvenCompat;
import net.dries007.tfc.common.recipes.HeatingRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vectorwing.farmersdelight.common.block.StoveBlock;

@Mixin(value = NetherStoveBlockEntity.class, remap = false)
public abstract class NetherStoveBlockEntity_FuelAndHeat implements HeatSourceBlockEntity, IStoveBlockEntity {
    @Unique private static final String SD_LEFT_BURN_TICK = "SDLeftBurnTick";
    @Unique private int leftBurnTick = Integer.MAX_VALUE;
    @Unique private final HeatingRecipe[] cachedHeatingRecipes = new HeatingRecipe[6];

    public float sdtfc$getTemperature(){
        if(!((BlockEntity)(Object)this).getBlockState().getValue(NetherStoveBlock.LIT)) return 0;
        return IStoveBlockEntity.sdtfc$getStaticTemperature();
    }

    @Inject(method = "cookingTick", at = @At("HEAD"))
    private static void injectedCookingTick(Level level, BlockPos pos, BlockState state, NetherStoveBlockEntity stove, CallbackInfo ci){
        var self = (IStoveBlockEntity)stove;
        if(self == null) return;
        if(state.getValue(StoveBlock.LIT)){
            if(ModList.get().isLoaded("firmalife")) StoveOvenCompat.ovenHeating(level, pos, state, self);
            if(level.getGameTime() % 100 == 0){
                level.sendBlockUpdated(pos, state, state, 3);
            }
        }
    }

    @Inject(method = "cookAndOutputItems", at = @At("HEAD"))
    private void cookTFCFood(CallbackInfo ci) {
        NetherStoveBlockEntity stove = (NetherStoveBlockEntity) (Object) this;
        IStoveBlockEntity iStove = (IStoveBlockEntity) stove;
        if(stove.getLevel() == null) return;
        int slots = stove.getInventory().getSlots();
        for(int i = 0; i < slots; i++){
            iStove.sdtfc$cookTFCFoodInSlot(iStove, i);
        }
    }

    @Unique
    public int sdtfc$getLeftBurnTick(){return leftBurnTick;}

    @Unique
    public void sdtfc$setLeftBurnTick(int v){leftBurnTick = Integer.MAX_VALUE;}

    @Override
    public HeatingRecipe[] sdtfc$getCachedRecipes() {
        return cachedHeatingRecipes;
    }

    @Inject(method = "load", at = @At("TAIL"), remap = true)
    private void sdtfc$loadLeftBurnTick(CompoundTag tag, CallbackInfo ci) {
        if (tag.contains(SD_LEFT_BURN_TICK, 3)) { // 3 = int
            this.leftBurnTick = tag.getInt(SD_LEFT_BURN_TICK);
        }
    }

    @Inject(method = "writeItems", at = @At("TAIL"))
    private void sd$writeLeftBurnTick(CompoundTag tag, CallbackInfoReturnable<CompoundTag> cir) {
        tag.putInt(SD_LEFT_BURN_TICK, this.leftBurnTick);
    }




}
