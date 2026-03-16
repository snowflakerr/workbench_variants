package com.workbenchvariants.content;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.LecternMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;

public class VariantLecternMenu extends LecternMenu {

    private final Player player;
    private final Container lecternContainer;

    public VariantLecternMenu(int containerId, Container lecternContainer, ContainerData lecternData, Player player) {
        super(containerId, lecternContainer, lecternData);
        this.player = player;
        this.lecternContainer = lecternContainer;
    }

    @Override
    public boolean clickMenuButton(Player player, int id) {
        boolean result = super.clickMenuButton(player, id);

        // vanilla lectern "take book" button removes the book from slot 0.
        // if the lectern is now empty, force-close the menu immediately.
        if (!player.level().isClientSide && this.lecternContainer.getItem(0).isEmpty()) {
            if (player instanceof ServerPlayer serverPlayer) {
                serverPlayer.closeContainer();
            }
        }

        return result;
    }
}