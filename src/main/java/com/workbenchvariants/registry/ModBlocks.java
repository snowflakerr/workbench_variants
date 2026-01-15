package com.workbenchvariants.registry;

import com.workbenchvariants.WorkbenchVariants;
import com.workbenchvariants.content.VariantFurnaceBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, WorkbenchVariants.MODID);

    public static final RegistryObject<Block> BLACKSTONE_FURNACE =
            BLOCKS.register("blackstone_furnace",
                    () -> new VariantFurnaceBlock(BlockBehaviour.Properties.copy(Blocks.FURNACE)));

    public static final RegistryObject<Block> DEEPSLATE_FURNACE =
            BLOCKS.register("deepslate_furnace",
                    () -> new VariantFurnaceBlock(BlockBehaviour.Properties.copy(Blocks.FURNACE)));
}
