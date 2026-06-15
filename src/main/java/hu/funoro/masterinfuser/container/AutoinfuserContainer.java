package hu.funoro.masterinfuser.container;

import com.blakebr0.cucumber.container.BaseContainerMenu;
import com.blakebr0.cucumber.inventory.CItemStacksHandler;
import com.blakebr0.cucumber.inventory.slot.CSlot;
import com.blakebr0.mysticalagriculture.api.machine.MachineUpgradeItemStackHandler;
import com.blakebr0.mysticalagriculture.item.MachineUpgradeItem;
import com.mojang.logging.LogUtils;
import hu.funoro.masterinfuser.data.recipe.ModRecipeTypes;
import hu.funoro.masterinfuser.tileentity.AutoinfuserTileEntity;
import hu.funoro.masterinfuser.init.ModMenuTypes;
import hu.funoro.masterinfuser.util.RecipeIngredientCache;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.item.ResourceHandlerSlot;
import org.slf4j.Logger;

// modified ReprocessorContainer by blakebr0

public class AutoinfuserContainer extends BaseContainerMenu {
    private final ContainerData data;

    public AutoinfuserContainer(int id, Inventory playerInventory, RegistryFriendlyByteBuf buffer) {
        this(id, playerInventory, AutoinfuserTileEntity.createInventoryHandler(), new MachineUpgradeItemStackHandler(), new SimpleContainerData(6), buffer.readBlockPos());
    }

    public AutoinfuserContainer(int id, Inventory playerInventory, CItemStacksHandler inventory, MachineUpgradeItemStackHandler upgradeInventory, ContainerData data, BlockPos pos) {
        super(ModMenuTypes.AUTOINFUSER.get(), id, pos);
        this.data = data;

        this.addSlot(new ResourceHandlerSlot(upgradeInventory, upgradeInventory::set, 0, 152, 9));

        this.addSlot(new CSlot(inventory, 0, 74, 52));
        this.addSlot(new CSlot(inventory, 1, 30, 56));
        this.addSlot(new CSlot(inventory, 2, 134, 52));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 112 + i * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 170));
        }

        this.addDataSlots(data);
    }

    private static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        var itemstack = ItemStack.EMPTY;
        var slot = this.slots.get(index);

        if (slot.hasItem()) {
            var itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
//            LOGGER.debug("Shift click {} in Autoinfuser.", itemstack1);

            if (index > 3) {
                if (itemstack1.getItem() instanceof MachineUpgradeItem) {
//                    LOGGER.debug("{} is machine upgrade.", itemstack1);
                    if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (RecipeIngredientCache.INSTANCE.isValidInput(itemstack1, ModRecipeTypes.AUTOINFUSER.get())) {
//                    LOGGER.debug("{} is valid input for recipe.", itemstack1);
                    if (!this.moveItemStackTo(itemstack1, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (itemstack1.getBurnTime(null, player.level().fuelValues()) > 0) {
//                    LOGGER.debug("{} is valid fuel.", itemstack1);
                    if (!this.moveItemStackTo(itemstack1, 2, 3, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 31) {
                    if (!this.moveItemStackTo(itemstack1, 31, 40, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 40 && !this.moveItemStackTo(itemstack1, 4, 30, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 4, 40, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
        }

        return itemstack;
    }

    public int getEnergyStored() {
        return this.data.get(0);
    }

    public int getEnergyCapacity() {
        return this.data.get(1);
    }

    public int getProgress() {
        return this.data.get(2);
    }

    public int getOperationTime() {
        return this.data.get(3);
    }

    public int getFuelLeft() {
        return this.data.get(4);
    }

    public int getFuelItemValue() {
        return this.data.get(5);
    }
}
