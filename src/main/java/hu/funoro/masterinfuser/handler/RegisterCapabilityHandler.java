package hu.funoro.masterinfuser.handler;

import hu.funoro.masterinfuser.tileentity.AutoinfuserTileEntity;
import hu.funoro.masterinfuser.tileentity.ModTileEntities;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

public class RegisterCapabilityHandler {
    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.Item.BLOCK, ModTileEntities.AUTOINFUSER.get(), AutoinfuserTileEntity::getSidedInventory);

        event.registerBlockEntity(Capabilities.Energy.BLOCK, ModTileEntities.AUTOINFUSER.get(), (block, _) -> block.getEnergy());
    }
}
