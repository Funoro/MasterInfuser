package hu.funoro.masterinfuser.client;

import hu.funoro.masterinfuser.client.screen.AutoinfuserScreen;
import hu.funoro.masterinfuser.init.ModMenuTypes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

public final class ModMenuScreens {
    @SubscribeEvent
    public void onRegisterMenuScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.AUTOINFUSER.get(), AutoinfuserScreen::new);
    }
}
