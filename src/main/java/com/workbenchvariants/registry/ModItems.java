package com.workbenchvariants.registry;

import com.workbenchvariants.WorkbenchVariants;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, WorkbenchVariants.MODID);

    public static final RegistryObject<Item> BLACKSTONE_FURNACE_ITEM =
            ITEMS.register("blackstone_furnace",
                    () -> new BlockItem(ModBlocks.BLACKSTONE_FURNACE.get(), new Item.Properties()));

    public static final RegistryObject<Item> DEEPSLATE_FURNACE_ITEM =
            ITEMS.register("deepslate_furnace",
                    () -> new BlockItem(ModBlocks.DEEPSLATE_FURNACE.get(), new Item.Properties()));
}
