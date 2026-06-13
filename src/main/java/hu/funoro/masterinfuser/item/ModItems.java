package hu.funoro.masterinfuser.item;

import hu.funoro.masterinfuser.MasterInfuser;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MasterInfuser.MOD_ID);

    public static final DeferredItem<Item> AUTOINFUSER_CRYSTAL = ITEMS.registerSimpleItem("autoinfuser_crystal");

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
