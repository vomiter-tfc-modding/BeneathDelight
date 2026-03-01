package com.vomiter.beneathdelight.common.event;

import com.eerussianguy.beneath.common.items.BeneathItems;
import com.soytutta.mynethersdelight.common.registry.MNDItems;
import com.vomiter.survivorsdelight.util.SDUtils;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.entities.livestock.TFCAnimalProperties;
import net.dries007.tfc.common.items.Food;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import vectorwing.farmersdelight.common.registry.ModItems;

import java.util.concurrent.atomic.AtomicInteger;

public class HoglinDropConvert {
    static TagKey<DamageType> isPiercing = TagKey.create(Registries.DAMAGE_TYPE, SDUtils.RLUtils.build("tfc", "is_piercing"));
    static int convertHam(LivingDropsEvent event, int porkCount){
        boolean hasHam = event.getDrops().stream()
                .anyMatch(stack -> stack.getItem().is(ModItems.HAM.get())||stack.getItem().is(ModItems.SMOKED_HAM.get()));
        if(hasHam) return porkCount;
        boolean killedWithMeleePiercing = (event.getSource().getDirectEntity() instanceof Player killer) && killer.getMainHandItem().is(TFCTags.Items.DEALS_PIERCING_DAMAGE);
        boolean killedWithRangedPiercing = event.getSource().is(isPiercing);
        if(!killedWithRangedPiercing && !killedWithMeleePiercing) return porkCount;

        int hamAdded = 0;
        float basicChance = 0.1f;
        if(event.getEntity() instanceof TFCAnimalProperties animal){
            basicChance += animal.getFamiliarity();
        }
        for (int i = 0; i < porkCount / 2; i++) {
            if(hamAdded >= 2) continue;
            if(event.getEntity().getRandom().nextFloat() < basicChance){
                event.getEntity().level().addFreshEntity(new ItemEntity(
                        event.getEntity().level(),
                        event.getEntity().getX(),
                        event.getEntity().getY(),
                        event.getEntity().getZ(),
                        new ItemStack(ModItems.HAM.get())
                ));
                hamAdded += 1;
            }
            porkCount -= 2;
        }
        return porkCount;
    }

    public static void onDrop(LivingDropsEvent event){
        if(event.getEntity().level().isClientSide) return;
        if(!(event.getEntity() instanceof Hoglin)) return;
        boolean killedWithMeleePiercing = (event.getSource().getDirectEntity() instanceof Player killer) && killer.getMainHandItem().is(TFCTags.Items.DEALS_PIERCING_DAMAGE);
        boolean killedWithRangedPiercing = event.getSource().is(isPiercing);

        AtomicInteger porkCount = new AtomicInteger();
        AtomicInteger hideCount = new AtomicInteger();

        event.getDrops().forEach(ie -> {
            if(ie.getItem().is(SDUtils.getTFCFoodItem(Food.PORK))){
                porkCount.addAndGet(ie.getItem().getCount());
                ie.discard();
            }
            if(ie.getItem().is(BeneathItems.CURSED_HIDE.get()) && (killedWithMeleePiercing || killedWithRangedPiercing)){
                hideCount.addAndGet(ie.getItem().getCount());
                ie.discard();
            }
        });

        Level level = event.getEntity().level();
        int porkCountInt = convertHam(event, porkCount.get());

        level.addFreshEntity(new ItemEntity(
                level,
                event.getEntity().getX(),
                event.getEntity().getY(),
                event.getEntity().getZ(),
                new ItemStack(MNDItems.HOGLIN_LOIN.get(), porkCountInt)
        ));

        level.addFreshEntity(new ItemEntity(
                level,
                event.getEntity().getX(),
                event.getEntity().getY(),
                event.getEntity().getZ(),
                new ItemStack(MNDItems.HOGLIN_HIDE.get(), hideCount.get())
        ));

    }

}
