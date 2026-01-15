package com.workbenchvariants.registry;

import com.workbenchvariants.WorkbenchVariants;
import com.workbenchvariants.content.VariantFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, WorkbenchVariants.MODID);

    public static final RegistryObject<BlockEntityType<VariantFurnaceBlockEntity>> FURNACE_VARIANT =
            BLOCK_ENTITIES.register("furnace_variant",
                    () -> BlockEntityType.Builder.of(
                            VariantFurnaceBlockEntity::new,
                            ModBlocks.BLACKSTONE_FURNACE.get(),
                            ModBlocks.DEEPSLATE_FURNACE.get()
                    ).build(null));
}
