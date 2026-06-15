package hu.funoro.masterinfuser.compat.jei;

import hu.funoro.masterinfuser.MasterInfuser;
import hu.funoro.masterinfuser.data.recipe.IAutoinfuserRecipe;
import mezz.jei.api.recipe.types.IRecipeType;

public class JeiRecipeTypes {
    public static final IRecipeType<IAutoinfuserRecipe> AUTOINFUSER = IRecipeType.create(MasterInfuser.resource("autoinfuser"), IAutoinfuserRecipe.class);
}
