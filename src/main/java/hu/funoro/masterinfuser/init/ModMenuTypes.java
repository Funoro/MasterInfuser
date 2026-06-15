package hu.funoro.masterinfuser.init;

import hu.funoro.masterinfuser.MasterInfuser;
import hu.funoro.masterinfuser.container.AutoinfuserContainer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> REGISTRY;
    public static final DeferredHolder<MenuType<?>, MenuType<AutoinfuserContainer>> AUTOINFUSER;

    static {
        REGISTRY = DeferredRegister.create(Registries.MENU, MasterInfuser.MOD_ID);
        AUTOINFUSER = REGISTRY.register("autoinfuser", () -> IMenuTypeExtension.create(AutoinfuserContainer::new));
    }
}
