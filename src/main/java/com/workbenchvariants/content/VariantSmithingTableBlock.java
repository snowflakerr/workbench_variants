package com.workbenchvariants.content;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SmithingMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SmithingTableBlock;
import net.minecraft.world.level.block.state.BlockState;

public class VariantSmithingTableBlock extends SmithingTableBlock {

    public VariantSmithingTableBlock(Properties properties) {
        super(properties);
    }

    @Override
    public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        return new SimpleMenuProvider(
                (id, inventory, player) ->
                        new SmithingMenu(id, inventory, ContainerLevelAccess.create(level, pos)) {

                            @Override
                            public boolean stillValid(Player player) {
                                // Accept THIS block instead of vanilla only
                                return true;
                            }
                        },
                Component.translatable("container.upgrade")
        );
    }
}
