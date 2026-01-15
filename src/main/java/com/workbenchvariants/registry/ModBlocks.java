package com.workbenchvariants.registry;

import com.workbenchvariants.WorkbenchVariants;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import com.workbenchvariants.content.VariantFurnaceBlock;
import com.workbenchvariants.content.VariantSmokerBlock;

import com.workbenchvariants.registry.benchgrouper.ModBlockGroups;

import java.util.ArrayList;
import java.util.stream.Stream;
import java.util.List;
import java.util.Map;


public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, WorkbenchVariants.MODID);

    // ================================= FURNACE VARIANTS =================================
    public static final List<String> FURNACE_IDS = List.of(
            "blackstone_furnace",
            "deepslate_furnace"
    );
    public static final Map<String, RegistryObject<Block>> FURNACES =
            ModBlockGroups.registerBlockGroup(
                    BLOCKS,
                    FURNACE_IDS,
                    () -> new VariantFurnaceBlock(BlockBehaviour.Properties.copy(Blocks.FURNACE))
            );
    // ====================================================================================

    // ================================= SMOKER VARIANTS ==================================
    public static final List<String> SMOKER_IDS = buildSmokerIds();

    private static List<String> buildSmokerIds() {
        List<String> ids = new ArrayList<>();

        String[] woodBase = {
                "spruce", "birch", "jungle", "acacia",
                "dark_oak", "mangrove", "cherry",
                "bamboo", "crimson", "warped"
        };
        String[] woodWithOak = Stream
                .concat(Stream.of("oak"), Stream.of(woodBase))
                .toArray(String[]::new);

        // Cobblestone smokers (no oak)
        for (String wood : woodBase) { ids.add(wood + "_cobblestone_smoker"); }
        // Blackstone smokers
        for (String wood : woodWithOak) { ids.add(wood + "_blackstone_smoker"); }
        // Deepslate smokers
        for (String wood : woodWithOak) { ids.add(wood + "_deepslate_smoker"); }

        return ids;
    }

    public static final Map<String, RegistryObject<Block>> SMOKERS =
            ModBlockGroups.registerBlockGroup(
                    BLOCKS,
                    SMOKER_IDS,
                    () -> new VariantSmokerBlock(BlockBehaviour.Properties.copy(Blocks.SMOKER))
            );
    // ====================================================================================
}