package com.workbenchvariants.registry.benchgrouper;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class ModItemGroups {

    private ModItemGroups() {}

    /**
     * Creates BlockItems for a whole block-group map (id -> block RegistryObject).
     * Uses the same id for the item registry name.
     */
    public static Map<String, RegistryObject<Item>> registerBlockItemGroup(
            DeferredRegister<Item> items,
            Map<String, RegistryObject<Block>> blocksById,
            Supplier<Item.Properties> properties
    ) {
        Map<String, RegistryObject<Item>> map = new LinkedHashMap<>();
        blocksById.forEach((id, blockRO) -> {
            map.put(id, items.register(id, () -> new BlockItem(blockRO.get(), properties.get())));
        });
        return map;
    }
}