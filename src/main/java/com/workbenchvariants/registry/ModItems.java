package com.workbenchvariants.registry;

import com.workbenchvariants.WorkbenchVariants;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import com.workbenchvariants.registry.benchgrouper.ModItemGroups;
import java.util.Map;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, WorkbenchVariants.MODID);

    // ================================= FURNACE VARIANTS =================================
    public static final Map<String, RegistryObject<Item>> FURNACE_ITEMS =
            ModItemGroups.registerBlockItemGroup(
                    ITEMS,
                    ModBlocks.FURNACES,
                    Item.Properties::new
            );
    // ====================================================================================

    // ================================= SMOKER VARIANTS ==================================
    public static final Map<String, RegistryObject<Item>> SMOKER_ITEMS =
            ModItemGroups.registerBlockItemGroup(
                    ITEMS,
                    ModBlocks.SMOKERS,
                    Item.Properties::new
            );
    // ====================================================================================

    // =============================== CARTOGRAPHY VARIANTS ===============================
    public static final Map<String, RegistryObject<Item>> CARTOGRAPHY_ITEMS =
            ModItemGroups.registerBlockItemGroup(
                    ITEMS,
                    ModBlocks.CARTOGRAPHY_TABLES,
                    Item.Properties::new
            );
    // ====================================================================================

    // ================================ SMITHING VARIANTS =================================
    public static final Map<String, RegistryObject<Item>> SMITHING_ITEMS =
            ModItemGroups.registerBlockItemGroup(
                    ITEMS,
                    ModBlocks.SMITHING_TABLES,
                    Item.Properties::new
            );
    // ====================================================================================

    // ================================== LOOM VARIANTS ===================================
    public static final Map<String, RegistryObject<Item>> LOOM_ITEMS =
            ModItemGroups.registerBlockItemGroup(
                    ITEMS,
                    ModBlocks.LOOMS,
                    Item.Properties::new
            );
    // ====================================================================================

    // ================================= LECTERN VARIANTS =================================
    public static final Map<String, RegistryObject<Item>> LECTERN_ITEMS =
            ModItemGroups.registerBlockItemGroup(
                    ITEMS,
                    ModBlocks.LECTERNS,
                    Item.Properties::new
            );
    // ====================================================================================

    // =============================== COMPOSTER VARIANTS ================================
    public static final Map<String, RegistryObject<Item>> COMPOSTER_ITEMS =
            ModItemGroups.registerBlockItemGroup(
                    ITEMS,
                    ModBlocks.COMPOSTERS,
                    Item.Properties::new
            );
    // ====================================================================================
}
