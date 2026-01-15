package com.workbenchvariants.content;

import com.workbenchvariants.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.FurnaceMenu;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class VariantFurnaceBlockEntity extends AbstractFurnaceBlockEntity implements MenuProvider {

    public VariantFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FURNACE_VARIANT.get(), pos, state, RecipeType.SMELTING);
    }

    @Override
    protected Component getDefaultName() {
        // Uses translation keys like "block.workbench_variants.blackstone_furnace"
        return Component.translatable(this.getBlockState().getBlock().getDescriptionId());
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory playerInventory) {
        // Vanilla furnace menu type (keeps compatibility)
        return new FurnaceMenu(containerId, playerInventory, this, this.dataAccess);
    }

    @Override
    public boolean stillValid(Player player) {
        return super.stillValid(player);
    }

    // Expose serverTick so the block can reference it cleanly
    public static void serverTick(net.minecraft.world.level.Level level, BlockPos pos, BlockState state, VariantFurnaceBlockEntity be) {
        AbstractFurnaceBlockEntity.serverTick(level, pos, state, be);
    }
}
