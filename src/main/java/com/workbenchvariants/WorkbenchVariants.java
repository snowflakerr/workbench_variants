package com.workbenchvariants;

import com.workbenchvariants.registry.ModBlockEntities;
import com.workbenchvariants.registry.ModBlocks;
import com.workbenchvariants.registry.ModItems;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import com.workbenchvariants.config.ModConfig;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig.Type;


@Mod(WorkbenchVariants.MODID)
public class WorkbenchVariants {
    public static final String MODID = "workbench_variants";

    public WorkbenchVariants() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);

        ModLoadingContext.get().registerConfig(Type.COMMON, ModConfig.SPEC);
    }
}
