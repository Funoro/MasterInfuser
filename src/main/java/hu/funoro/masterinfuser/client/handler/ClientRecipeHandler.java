package hu.funoro.masterinfuser.client.handler;

import hu.funoro.masterinfuser.data.recipe.IAutoinfuserRecipe;
import hu.funoro.masterinfuser.data.recipe.ModRecipeTypes;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.RecipesReceivedEvent;

import java.util.ArrayList;
import java.util.List;


public class ClientRecipeHandler {
    public static final List<RecipeHolder<IAutoinfuserRecipe>> AUTOINFUSER_RECIPES = new ArrayList<>();

    @SubscribeEvent
    public void onRecipesReceived(RecipesReceivedEvent event) {
        var recipes = event.getRecipeMap();
        AUTOINFUSER_RECIPES.addAll(recipes.byType(ModRecipeTypes.AUTOINFUSER.get()));
    }

    @SubscribeEvent
    public void onClientPlayerLoggingOut(ClientPlayerNetworkEvent.LoggingOut event) {
        AUTOINFUSER_RECIPES.clear();
    }
}
