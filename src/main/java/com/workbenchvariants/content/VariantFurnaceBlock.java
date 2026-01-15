package com.workbenchvariants.content;

import com.workbenchvariants.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.core.Direction;


public class VariantFurnaceBlock extends AbstractFurnaceBlock {

    public VariantFurnaceBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new VariantFurnaceBlockEntity(pos, state);
    }

    // Required by AbstractFurnaceBlock in 1.20.1
    @Override
    protected void openContainer(Level level, BlockPos pos, Player player) {
        if (level.getBlockEntity(pos) instanceof VariantFurnaceBlockEntity be && player instanceof ServerPlayer sp) {
            sp.openMenu(be);
        }
    }

    // Vanilla behavior: right-click opens container, awards stat, etc.
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, net.minecraft.world.InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide) return InteractionResult.SUCCESS;
        this.openContainer(level, pos, player);
        return InteractionResult.CONSUME;
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide) return null;
        return createTickerHelper(type, ModBlockEntities.FURNACE_VARIANT.get(),
                (lvl, p, st, be) -> VariantFurnaceBlockEntity.serverTick(lvl, p, st, be));
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (!state.getValue(LIT)) return;

        double x = (double) pos.getX() + 0.5D;
        double y = (double) pos.getY();
        double z = (double) pos.getZ() + 0.5D;

        if (random.nextDouble() < 0.1D) {
            level.playLocalSound(x, y, z, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS,
                    1.0F, 1.0F, false);
        }

        Direction dir = state.getValue(FACING);
        Direction.Axis axis = dir.getAxis();

        double offset = 0.52D;
        double spread = random.nextDouble() * 0.6D - 0.3D;

        double px = axis == Direction.Axis.X ? (double) dir.getStepX() * offset : spread;
        double pz = axis == Direction.Axis.Z ? (double) dir.getStepZ() * offset : spread;

        double particleX = x + px;
        double particleY = y + random.nextDouble() * 6.0D / 16.0D;
        double particleZ = z + pz;

        level.addParticle(ParticleTypes.SMOKE, particleX, particleY, particleZ, 0.0D, 0.0D, 0.0D);
        level.addParticle(ParticleTypes.FLAME, particleX, particleY, particleZ, 0.0D, 0.0D, 0.0D);
    }
}
