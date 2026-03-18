package com.workbenchvariants.content;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
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

                level.playSound(
                        null,
                        pos,
                        SoundEvents.BOOK_PUT,
                        SoundSource.BLOCKS,
                        1.0F,
                        1.0F
                );

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

    @Override
    public boolean isSignalSource(BlockState state) {
        return true;
    }

    @Override
    public int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return state.getValue(POWERED) ? 15 : 0;
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof VariantLecternBlockEntity lecternBE && lecternBE.hasBook()) {
            return lecternBE.getRedstoneSignal();
        }
        return 0;
    }

    @Override
    public void onRemove(BlockState oldState, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!oldState.is(newState.getBlock())) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof VariantLecternBlockEntity lecternBE) {
                ItemStack storedBook = lecternBE.getBook();
                if (!storedBook.isEmpty() && !level.isClientSide) {
                    ItemEntity itemEntity = new ItemEntity(
                            level,
                            pos.getX() + 0.5D,
                            pos.getY() + 1.0D,
                            pos.getZ() + 0.5D,
                            storedBook.copy()
                    );
                    itemEntity.setDefaultPickUpDelay();
                    level.addFreshEntity(itemEntity);
                }
            }
        }

        super.onRemove(oldState, level, pos, newState, isMoving);
    }
}