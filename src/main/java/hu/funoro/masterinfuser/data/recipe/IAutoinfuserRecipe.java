package hu.funoro.masterinfuser.data.recipe;

import hu.funoro.masterinfuser.MasterInfuser;
import net.minecraft.world.item.crafting.*;

public interface IAutoinfuserRecipe extends Recipe<CraftingInput> {
    Ingredient getIngredient();

    @Override
    default boolean isSpecial() {
        return true;
    }

    @Override
    default String group() {
        return MasterInfuser.MOD_ID + ":autoinfuser";
    }

    @Override
    default boolean showNotification() {
        return false;
    }

    @Override
    default RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.CRAFTING_MISC;
    }

    @Override
    default PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }
}
