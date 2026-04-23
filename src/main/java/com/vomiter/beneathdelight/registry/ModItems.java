package com.vomiter.beneathdelight.registry;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    static DeferredRegister<Item> ITEMS
            = ModRegistries.createRegistry(ForgeRegistries.ITEMS);

    public static RegistryObject<Item> BOILED_CRIMSON_FUNGUS
            = ITEMS.register("boiled_crimson_fungus", () -> new Item(new Item.Properties()));
    public static RegistryObject<Item> BOILED_WARPED_FUNGUS
            = ITEMS.register("boiled_warped_fungus", () -> new Item(new Item.Properties()));
    public static RegistryObject<Item> BOILED_CRIMSON_FUNGUS_COLONY
            = ITEMS.register("boiled_crimson_fungus_colony", () -> new Item(new Item.Properties()));
    public static RegistryObject<Item> BOILED_WARPED_FUNGUS_COLONY
            = ITEMS.register("boiled_warped_fungus_colony", () -> new Item(new Item.Properties()));
    public static RegistryObject<Item> BOILED_CRIMSON_ROOTS
            = ITEMS.register("boiled_crimson_roots", () -> new Item(new Item.Properties()));
    public static RegistryObject<Item> BOILED_WARPED_ROOTS
            = ITEMS.register("boiled_warped_roots", () -> new Item(new Item.Properties()));

    public static RegistryObject<Item> SWEETENED_MAGMA_CREAM
            = ITEMS.register("sweetened_magma_cream", () -> new Item(new Item.Properties()));



}
