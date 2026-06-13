package hu.funoro.masterinfuser.data.recipe;

import com.blakebr0.mysticalagriculture.api.crafting.IInfusionRecipe;
import hu.funoro.masterinfuser.MasterInfuser;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipeTypes {
    public static final DeferredRegister<RecipeType<?>> REGISTRY;
    public static final DeferredHolder<RecipeType<?>, RecipeType<IAutoinfuserRecipe>> AUTOINFUSER;
    public static final DeferredHolder<RecipeType<?>, RecipeType<ConsumingShapedRecipe>> CONSUMING_SHAPED;

    static {
        REGISTRY = DeferredRegister.create(Registries.RECIPE_TYPE, MasterInfuser.MOD_ID);
        AUTOINFUSER = REGISTRY.register("autoinfuser", () -> RecipeType.simple(MasterInfuser.resource("autoinfuser")));
        CONSUMING_SHAPED = REGISTRY.register("consuming_shaped", () -> RecipeType.simple(MasterInfuser.resource("consuming_shaped")));
    }
}
