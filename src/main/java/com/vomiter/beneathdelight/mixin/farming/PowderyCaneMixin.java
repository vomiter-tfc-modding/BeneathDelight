package com.vomiter.beneathdelight.mixin.farming;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.soytutta.mynethersdelight.common.block.PowderyCaneBlock;
import com.vomiter.beneathdelight.Helpers;
import net.dries007.tfc.common.TFCTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import vectorwing.farmersdelight.FarmersDelight;

@Mixin(PowderyCaneBlock.class)
public class PowderyCaneMixin {
    @WrapOperation(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/tags/TagKey;)Z", remap = true))
    private boolean sdtfc$useTFCKnife(ItemStack itemStack, TagKey<Item> tagKey, Operation<Boolean> original){
        if(tagKey.location().equals(Helpers.id("forge", "tools/knives"))){
            return original.call(itemStack, TFCTags.Items.KNIVES);
        }
        return original.call(itemStack, tagKey);
    }

}
