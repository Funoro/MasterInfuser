package hu.funoro.masterinfuser.creativemodetab;

import hu.funoro.masterinfuser.MasterInfuser;
import hu.funoro.masterinfuser.block.ModBlocks;
import hu.funoro.masterinfuser.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MasterInfuser.MOD_ID);

    public static final Supplier<CreativeModeTab> MOD_TAB = CREATIVE_MODE_TABS.register(
            "master_infuser_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.AUTOINFUSER_CRYSTAL.get()))
                    .title(Component.translatable("creativetab.masterinfuser.master_infuser_tab"))
                    .withTabsBefore(com.blakebr0.mysticalagriculture.init.ModCreativeModeTabs.CREATIVE_TAB.getId())
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.AUTOINFUSER_CRYSTAL);
                        output.accept(ModBlocks.AUTOINFUSER);
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
