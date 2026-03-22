package com.vomiter.beneathdelight.data;

import com.vomiter.beneathdelight.Helpers;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class BDTags {
    static TagKey<Item> createItem(ResourceLocation id){
        return TagKey.create(
                Registries.ITEM,
                id
        );
    }

    public static TagKey<Item> GHAST_DOUGHS = createItem(Helpers.id("ghast_doughs"));
}
