package com.workbenchvariants.content;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class VariantLecternBlock extends LecternBlock {

    public VariantLecternBlock(Properties properties) {
        super(properties);
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new VariantLecternBlockEntity(pos, state);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack held = player.getItemInHand(hand);

        if (!state.getValue(HAS_BOOK)) {
            boolean validLecternBook =
                    held.is(Items.WRITABLE_BOOK) ||
                            held.is(Items.WRITTEN_BOOK);

            if (!validLecternBook) {
                return InteractionResult.PASS;
            }

            if (!level.isClientSide && level.getBlockEntity(pos) instanceof VariantLecternBlockEntity be) {
                ItemStack placed = held.copyWithCount(1);
                be.setBook(placed);

                if (!player.getAbilities().instabuild) {
                    held.shrink(1);
                }
            }

            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        if (!level.isClientSide && level.getBlockEntity(pos) instanceof VariantLecternBlockEntity be && player instanceof ServerPlayer sp) {
            sp.openMenu(be);
            player.awardStat(Stats.INTERACT_WITH_LECTERN);
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}