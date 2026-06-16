package hu.funoro.masterinfuser.compat.jei;

import hu.funoro.masterinfuser.MasterInfuser;
import hu.funoro.masterinfuser.block.ModBlocks;
import hu.funoro.masterinfuser.client.handler.ClientRecipeHandler;
import hu.funoro.masterinfuser.client.screen.AutoinfuserScreen;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.*;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;

@JeiPlugin
public class JeiCompat implements IModPlugin {
    public static final Identifier UID = MasterInfuser.resource("jei_plugin");

    @Override
    public @NonNull Identifier getPluginUid() {
        return UID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        var guiHelper = registration.getJeiHelpers().getGuiHelper();

        registration.addRecipeCategories(
                new AutoinfuserCategory(guiHelper)
        );
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addCraftingStation(AutoinfuserCategory.RECIPE_TYPE, new ItemStack(ModBlocks.AUTOINFUSER.get()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(AutoinfuserCategory.RECIPE_TYPE, ClientRecipeHandler.AUTOINFUSER_RECIPES);
        MasterInfuser.LOGGER.debug("Registered {} recipes for JEI.", ClientRecipeHandler.AUTOINFUSER_RECIPES.size());
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(AutoinfuserScreen.class, 99, 52, 22, 15, AutoinfuserCategory.RECIPE_TYPE);
    }
}
