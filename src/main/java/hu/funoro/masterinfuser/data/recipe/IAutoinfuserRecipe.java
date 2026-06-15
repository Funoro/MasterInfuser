package hu.funoro.masterinfuser.data.recipe;

import hu.funoro.masterinfuser.MasterInfuser;
import net.minecraft.world.item.crafting.*;
import org.jspecify.annotations.NonNull;

public interface IAutoinfuserRecipe extends Recipe<CraftingInput> {
    Ingredient getIngredient();

    @Override
    default @NonNull String group() {
        return MasterInfuser.MOD_ID + ":autoinfuser";
    }

    @Override
    default boolean showNotification() {
        return false;
    }

    @Override
    default @NonNull RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.CRAFTING_MISC;
    }

    @Override
    default @NonNull PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }
}
