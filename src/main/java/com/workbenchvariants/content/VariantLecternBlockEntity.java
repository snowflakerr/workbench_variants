package com.workbenchvariants.content;

import com.workbenchvariants.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Clearable;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.LecternMenu;
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
            if (index == 0) {
                page = value;
                setChangedAndSync();
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
        this.page = page;
        setChangedAndSync();
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
        return new LecternMenu(containerId, this.bookAccess, this.dataAccess);
    }
}