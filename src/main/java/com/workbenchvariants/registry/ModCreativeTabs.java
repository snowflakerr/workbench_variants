package com.workbenchvariants.registry;

import com.workbenchvariants.WorkbenchVariants;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import java.util.Map;

@Mod.EventBusSubscriber(modid = WorkbenchVariants.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCreativeTabs {

    @SubscribeEvent
    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            insertGroupAfter(event, Blocks.FURNACE, ModBlocks.FURNACES);
            insertGroupAfter(event, Blocks.SMOKER, ModBlocks.SMOKERS);
            insertGroupAfter(event, Blocks.CARTOGRAPHY_TABLE, ModBlocks.CARTOGRAPHY_TABLES);
            insertGroupAfter(event, Blocks.SMITHING_TABLE, ModBlocks.SMITHING_TABLES);
            insertGroupAfter(event, Blocks.LOOM, ModBlocks.LOOMS);
            insertGroupAfter(event, Blocks.LECTERN, ModBlocks.LECTERNS);
            insertGroupAfter(event, Blocks.COMPOSTER, ModBlocks.COMPOSTERS);
        }

        if (event.getTabKey() == CreativeModeTabs.REDSTONE_BLOCKS) {
            insertGroupAfter(event, Blocks.LECTERN, ModBlocks.LECTERNS);
            insertGroupAfter(event, Blocks.COMPOSTER, ModBlocks.COMPOSTERS);
        }
    }

    private static void insertGroupAfter(
            BuildCreativeModeTabContentsEvent event,
            ItemLike vanillaAnchor,
            Map<String, ? extends RegistryObject<? extends ItemLike>> group
    ) {
        ItemStack previous = vanillaAnchor.asItem().getDefaultInstance();

        for (RegistryObject<? extends ItemLike> entry : group.values()) {
            ItemStack stack = entry.get().asItem().getDefaultInstance();
            event.getEntries().putAfter(
                    previous,
                    stack,
                    CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
            );
            previous = stack;
        }
    }
}