package com.vomiter.beneathdelight.common.event;

import com.vomiter.beneathdelight.common.command.ModCommand;
import com.vomiter.survivorsdelight.common.food.block.SDDecayingBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class EventHandler {
    public static void init(){
        final IEventBus bus = MinecraftForge.EVENT_BUS;
        bus.addListener(EventHandler::onRegisterCommands);
        bus.addListener(HoglinDropConvert::onDrop);
        //bus.addListener(EventHandler::onDebugRightClick);
        bus.addListener(DelayedStriderLoafTask::onServerTickEnd);
    }

    //DEBUG
    public static void onDebugRightClick(PlayerInteractEvent.RightClickBlock event){
        Player player = event.getEntity();
        BlockPos pos = event.getHitVec().getBlockPos();
        BlockState state = player.level().getBlockState(pos);
        String blockId = Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(state.getBlock())).toString();
        player.sendSystemMessage(Component.literal(blockId));
        if(player.level().getBlockEntity(pos) instanceof SDDecayingBlockEntity decay){
            player.sendSystemMessage(Component.literal("is rotten on" + (player.level().isClientSide? "CLIENT": "SERVER") + ":" + decay.isRotten()));
            player.sendSystemMessage(Component.literal("The stored stack is " + decay.getStack().getDescriptionId()));
        }
    }

    public static void onRegisterCommands(RegisterCommandsEvent event) {
        ModCommand.register(event.getDispatcher());
    }

}
