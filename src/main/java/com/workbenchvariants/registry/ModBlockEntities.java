package com.workbenchvariants.registry;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import com.workbenchvariants.WorkbenchVariants;

import com.workbenchvariants.content.VariantFurnaceBlockEntity;
import com.workbenchvariants.content.VariantSmokerBlockEntity;

import com.workbenchvariants.registry.benchgrouper.ModBlockGroups;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, WorkbenchVariants.MODID);

    // ================================= FURNACE VARIANTS =================================
    public static final RegistryObject<BlockEntityType<VariantFurnaceBlockEntity>> FURNACE_VARIANT =
            BLOCK_ENTITIES.register("furnace_variant",
                    () -> BlockEntityType.Builder.of(
                            VariantFurnaceBlockEntity::new,
                            ModBlockGroups.toBlockArray(ModBlocks.FURNACES)
                    ).build(null));
    // ====================================================================================

    // ================================= SMOKER VARIANTS ==================================
    public static final RegistryObject<BlockEntityType<VariantSmokerBlockEntity>> SMOKER_VARIANT =
            BLOCK_ENTITIES.register("smoker_variant",
                    () -> BlockEntityType.Builder.of(
                            VariantSmokerBlockEntity::new,
                            ModBlockGroups.toBlockArray(ModBlocks.SMOKERS)
                    ).build(null));
    // ====================================================================================
}
