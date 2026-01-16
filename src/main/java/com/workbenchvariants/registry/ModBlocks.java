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
import com.workbenchvariants.content.VariantCartographyTableBlock;

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
    public static final List<String> SMOKER_IDS = new ArrayList<>();
    static {
        String[] woodBase = {
                "spruce", "birch", "jungle", "acacia",
                "dark_oak", "mangrove", "cherry",
                "bamboo", "crimson", "warped"
        };
        String[] woodWithOak = Stream
                .concat(Stream.of("oak"), Stream.of(woodBase))
                .toArray(String[]::new);

        // Cobblestone smokers (no oak)
        for (String wood : woodBase) { SMOKER_IDS.add(wood + "_cobblestone_smoker"); }
        // Blackstone smokers
        for (String wood : woodWithOak) { SMOKER_IDS.add(wood + "_blackstone_smoker"); }
        // Deepslate smokers
        for (String wood : woodWithOak) { SMOKER_IDS.add(wood + "_deepslate_smoker"); }
    }

    public static final Map<String, RegistryObject<Block>> SMOKERS =
            ModBlockGroups.registerBlockGroup(
                    BLOCKS,
                    SMOKER_IDS,
                    () -> new VariantSmokerBlock(BlockBehaviour.Properties.copy(Blocks.SMOKER))
            );
    // ====================================================================================

    // =============================== CARTOGRAPHY VARIANTS ===============================
    public static final List<String> CARTOGRAPHY_IDS = new ArrayList<>();
    static {
        String[] woods = {
                "oak", "spruce", "birch", "jungle", "acacia",
                "mangrove", "cherry", "bamboo", "crimson", "warped"
        };

        for (String wood : woods) { CARTOGRAPHY_IDS.add(wood + "_cartography_table"); }
    }

    public static final Map<String, RegistryObject<Block>> CARTOGRAPHY_TABLES =
            ModBlockGroups.registerBlockGroup(
                    BLOCKS,
                    CARTOGRAPHY_IDS,
                    () -> new VariantCartographyTableBlock(BlockBehaviour.Properties.copy(Blocks.CARTOGRAPHY_TABLE))
            );
    // ========================================================================================
}