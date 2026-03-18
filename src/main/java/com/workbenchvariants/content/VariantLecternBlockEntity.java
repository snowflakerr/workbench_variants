package com.workbenchvariants.content;

import com.workbenchvariants.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.Clearable;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class VariantLecternBlockEntity extends BlockEntity implements MenuProvider, Clearable {

    private ItemStack book = ItemStack.EMPTY;
    private int page = 0;

    private final Container bookAccess = new SimpleContainer(1) {
        @Override
        public ItemStack getItem(int slot) {
            return slot == 0 ? VariantLecternBlockEntity.this.book : ItemStack.EMPTY;
        }

        @Override
        public void setItem(int slot, ItemStack stack) {
            if (slot == 0) {
                VariantLecternBlockEntity.this.book = stack;
                VariantLecternBlockEntity.this.page = 0;
                VariantLecternBlockEntity.this.setChangedAndSync();
            }
        }

        @Override
        public ItemStack removeItem(int slot, int amount) {
            if (slot != 0 || VariantLecternBlockEntity.this.book.isEmpty()) {
                return ItemStack.EMPTY;
            }

            ItemStack out = VariantLecternBlockEntity.this.book.split(amount);
            if (VariantLecternBlockEntity.this.book.isEmpty()) {
                VariantLecternBlockEntity.this.book = ItemStack.EMPTY;
                VariantLecternBlockEntity.this.page = 0;
            }
            VariantLecternBlockEntity.this.setChangedAndSync();
            return out;
        }

        @Override
        public ItemStack removeItemNoUpdate(int slot) {
            if (slot != 0) return ItemStack.EMPTY;

            ItemStack out = VariantLecternBlockEntity.this.book;
            VariantLecternBlockEntity.this.book = ItemStack.EMPTY;
            VariantLecternBlockEntity.this.page = 0;
            VariantLecternBlockEntity.this.setChangedAndSync();
            return out;
        }

        @Override
        public boolean isEmpty() {
            return VariantLecternBlockEntity.this.book.isEmpty();
        }

        @Override
        public void clearContent() {
            VariantLecternBlockEntity.this.book = ItemStack.EMPTY;
            VariantLecternBlockEntity.this.page = 0;
            VariantLecternBlockEntity.this.setChangedAndSync();
        }
    };

    private final ContainerData dataAccess = new ContainerData() {
        @Override
        public int get(int index) {
            return index == 0 ? page : 0;
        }

        @Override
        public void set(int index, int value) {
            if (index == 0 && page != value) {
                page = value;
                setChangedAndSync();
                pulseRedstone();

                if (VariantLecternBlockEntity.this.level != null) {
                    VariantLecternBlockEntity.this.level.playSound(
                            null,
                            VariantLecternBlockEntity.this.worldPosition,
                            net.minecraft.sounds.SoundEvents.BOOK_PAGE_TURN,
                            net.minecraft.sounds.SoundSource.BLOCKS,
                            1.0F,
                            1.0F
                    );
                }
            }
        }

        @Override
        public int getCount() {
            return 1;
        }
    };

    public VariantLecternBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.LECTERN_VARIANT.get(), pos, state);
    }

    public boolean hasBook() {
        return !this.book.isEmpty();
    }

    public ItemStack getBook() {
        return this.book;
    }

    public void setBook(ItemStack stack) {
        this.book = stack.copyWithCount(1);
        this.page = 0;
        setChangedAndSync();
    }

    public ItemStack removeBook() {
        ItemStack out = this.book;
        this.book = ItemStack.EMPTY;
        this.page = 0;
        setChangedAndSync();
        return out;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        if (this.page != page) {
            this.page = page;
            setChangedAndSync();
            pulseRedstone();
        }
    }

    public int getPageCount() {
        if (this.book.isEmpty() || !this.book.hasTag()) {
            return 0;
        }

        CompoundTag tag = this.book.getTag();
        if (tag == null || !tag.contains("pages", 9)) {
            return 1;
        }

        ListTag pages = tag.getList("pages", 8);
        return Math.max(1, pages.size());
    }

    public int getRedstoneSignal() {
        if (!this.hasBook()) {
            return 0;
        }

        int pageCount = this.getPageCount();
        if (pageCount <= 1) {
            return 1;
        }

        return Mth.floor(((float) this.page / (float) (pageCount - 1)) * 14.0F) + 1;
    }

    private void pulseRedstone() {
        if (this.level == null) {
            return;
        }

        BlockState state = this.getBlockState();
        if (!state.hasProperty(LecternBlock.POWERED)) {
            return;
        }

        if (!state.getValue(LecternBlock.POWERED)) {
            BlockState poweredState = state.setValue(LecternBlock.POWERED, true);
            this.level.setBlock(this.worldPosition, poweredState, 3);
            this.level.updateNeighborsAt(this.worldPosition, poweredState.getBlock());
            this.level.updateNeighborsAt(this.worldPosition.below(), poweredState.getBlock());
            this.level.scheduleTick(this.worldPosition, poweredState.getBlock(), 2);
        } else {
            this.level.updateNeighborsAt(this.worldPosition, state.getBlock());
            this.level.updateNeighborsAt(this.worldPosition.below(), state.getBlock());
        }
    }

    private void setChangedAndSync() {
        this.setChanged();

        if (this.level != null) {
            BlockState state = this.getBlockState();

            if (state.hasProperty(LecternBlock.HAS_BOOK)) {
                BlockState newState = state.setValue(LecternBlock.HAS_BOOK, this.hasBook());
                this.level.setBlock(this.worldPosition, newState, 3);
                this.level.sendBlockUpdated(this.worldPosition, state, newState, 3);
            } else {
                this.level.sendBlockUpdated(this.worldPosition, state, state, 3);
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);

        if (!this.book.isEmpty()) {
            tag.put("Book", this.book.save(new CompoundTag()));
        }
        tag.putInt("Page", this.page);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);

        this.book = tag.contains("Book") ? ItemStack.of(tag.getCompound("Book")) : ItemStack.EMPTY;
        this.page = tag.getInt("Page");
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void clearContent() {
        this.book = ItemStack.EMPTY;
        this.page = 0;
        setChangedAndSync();
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable(this.getBlockState().getBlock().getDescriptionId());
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new VariantLecternMenu(containerId, this.bookAccess, this.dataAccess, player);
    }
}