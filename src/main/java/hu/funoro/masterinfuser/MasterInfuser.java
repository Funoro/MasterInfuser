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
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(MasterInfuser.MOD_ID)
public class MasterInfuser {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "masterinfuser";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public MasterInfuser(IEventBus bus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        bus.addListener(this::commonSetup);

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

        NeoForge.EVENT_BUS.register(this);


        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    public static Identifier resource(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        NeoForge.EVENT_BUS.register(RecipeIngredientCache.INSTANCE);
        LOGGER.info("Master Infuser mod loaded.");
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
//    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
//    public static class ClientModEvents {
//        @SubscribeEvent
//        public static void onClientSetup(FMLClientSetupEvent event) {
//            // Some client setup code
//            LOGGER.info("HELLO FROM CLIENT SETUP");
//            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
//        }
//    }
}
