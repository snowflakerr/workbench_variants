package com.workbenchvariants.content;

import com.workbenchvariants.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.SmokerMenu;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class VariantSmokerBlockEntity extends AbstractFurnaceBlockEntity implements MenuProvider {

    public VariantSmokerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SMOKER_VARIANT.get(), pos, state, RecipeType.SMOKING);
    }

    @Override
    protected Component getDefaultName() {
        // Uses translation keys like "block.workbench_variants.spruce_blackstone_smoker"
        return Component.translatable(this.getBlockState().getBlock().getDescriptionId());
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory playerInventory) {
        // Vanilla smoker menu type (keeps compatibility)
        return new SmokerMenu(containerId, playerInventory, this, this.dataAccess);
    }

    @Override
    public boolean stillValid(Player player) { return super.stillValid(player); }

    // Expose serverTick so the block can reference it cleanly
    public static void serverTick(net.minecraft.world.level.Level level, BlockPos pos, BlockState state, VariantSmokerBlockEntity be) {
        AbstractFurnaceBlockEntity.serverTick(level, pos, state, be);
    }
}