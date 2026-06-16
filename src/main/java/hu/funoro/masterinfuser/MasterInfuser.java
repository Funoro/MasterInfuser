package hu.funoro.masterinfuser;

import com.mojang.logging.LogUtils;
import hu.funoro.masterinfuser.block.ModBlocks;
import hu.funoro.masterinfuser.client.ModMenuScreens;
import hu.funoro.masterinfuser.client.handler.ClientRecipeHandler;
import hu.funoro.masterinfuser.creativemodetab.ModCreativeModeTabs;
import hu.funoro.masterinfuser.data.recipe.ModRecipeTypes;
import hu.funoro.masterinfuser.handler.RegisterCapabilityHandler;
import hu.funoro.masterinfuser.init.ModMenuTypes;
import hu.funoro.masterinfuser.item.ModItems;
import hu.funoro.masterinfuser.registry.ModRecipeSerializers;
import hu.funoro.masterinfuser.tileentity.ModTileEntities;
import hu.funoro.masterinfuser.util.RecipeIngredientCache;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

@Mod(MasterInfuser.MOD_ID)
@EventBusSubscriber(modid = MasterInfuser.MOD_ID)
public class MasterInfuser {
    public static final String MOD_ID = "masterinfuser";
    public static final Logger LOGGER = LogUtils.getLogger();

    public MasterInfuser(IEventBus bus, ModContainer modContainer) {
        ModCreativeModeTabs.register(bus);
        ModItems.register(bus);
        ModBlocks.register(bus);
        ModMenuTypes.REGISTRY.register(bus);
        ModTileEntities.REGISTRY.register(bus);
        ModRecipeTypes.REGISTRY.register(bus);
        NeoForge.EVENT_BUS.register(ModRecipeTypes.class);
        ModRecipeSerializers.register(bus);

        bus.register(RegisterCapabilityHandler.class);

        if (FMLEnvironment.getDist() == Dist.CLIENT) {
            // register client stuff
            bus.register(new ModMenuScreens());
            NeoForge.EVENT_BUS.register(new ClientRecipeHandler());
        }
    }

    public static Identifier resource(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    @SubscribeEvent
    public static void onCommonSetup(final FMLCommonSetupEvent event) {
        NeoForge.EVENT_BUS.register(RecipeIngredientCache.INSTANCE);
        LOGGER.info("Master Infuser mod loaded.");
    }
}
