package com.vomiter.beneathdelight.mixin.stove;

import com.soytutta.mynethersdelight.common.block.entity.NetherStoveBlockEntity;
import com.vomiter.beneathdelight.adapter.INetherStoveBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.spongepowered.asm.mixin.Mixin;
import vectorwing.farmersdelight.common.block.entity.SyncedBlockEntity;

@Mixin(value = NetherStoveBlockEntity.class, remap = false)
public abstract class NetherStoveBlockEntity_AccessorImp extends SyncedBlockEntity implements INetherStoveBlockEntity {
    public NetherStoveBlockEntity_AccessorImp(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }

    public NetherStoveBlockEntity sdtfc$getBlockEntity(){
        return (NetherStoveBlockEntity) (Object)this;
    }
    ;
    public ItemStackHandler sdtfc$getInventory(){
        return ((NetherStoveBlockEntity)(Object)this).getInventory();
    };

    public int[] sdtfc$getCookingTimes(){
        if(this instanceof NetherStoveBlockEntity_Accessor acc){
            return acc.getCookingTimes();
        }
        return null;
    };
    public int[] sdtfc$getCookingTimesTotal(){
        if(this instanceof NetherStoveBlockEntity_Accessor acc){
            return acc.getCookingTimesTotal();
        }
        return null;
    };
    public ResourceLocation[] sdtfc$getLastRecipeIDs(){
        if(this instanceof NetherStoveBlockEntity_Accessor acc){
            return acc.getLastRecipeIDs();
        }
        return null;
    };
}
