package com.workbenchvariants.registry.benchgrouper;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public final class ModBlockGroups {

    private ModBlockGroups() {}

    /**
     * Registers a group of blocks (same factory) from a list of ids.
     * Returns a LinkedHashMap so insertion order stays stable (nice for creative tabs / datagen).
     */
    public static Map<String, RegistryObject<Block>> registerBlockGroup(
            DeferredRegister<Block> blocks,
            List<String> ids,
            Supplier<? extends Block> factory
    ) {
        Map<String, RegistryObject<Block>> map = new LinkedHashMap<>();
        for (String id : ids) {
            map.put(id, blocks.register(id, factory));
        }
        return map;
    }

    /**
     * If you need the id inside the factory (different properties per id), use this.
     */
    public static Map<String, RegistryObject<Block>> registerBlockGroup(
            DeferredRegister<Block> blocks,
            List<String> ids,
            java.util.function.Function<String, ? extends Block> factoryById
    ) {
        Map<String, RegistryObject<Block>> map = new LinkedHashMap<>();
        for (String id : ids) {
            map.put(id, blocks.register(id, () -> factoryById.apply(id)));
        }
        return map;
    }

    /** Converts a group map into a Block[] for BlockEntityType.Builder.of(...). */
    public static Block[] toBlockArray(Map<String, RegistryObject<Block>> blocksById) {
        return blocksById.values().stream().map(RegistryObject::get).toArray(Block[]::new);
    }
}