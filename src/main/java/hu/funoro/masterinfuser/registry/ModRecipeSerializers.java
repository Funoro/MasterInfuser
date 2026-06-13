package hu.funoro.masterinfuser.registry;

import hu.funoro.masterinfuser.MasterInfuser;
import hu.funoro.masterinfuser.data.recipe.AutoinfuserRecipe;
import hu.funoro.masterinfuser.data.recipe.ConsumingShapedRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, MasterInfuser.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<ConsumingShapedRecipe>> CONSUMING_SHAPED =
            SERIALIZERS.register("consuming_shaped",
                    () -> new RecipeSerializer<>(ConsumingShapedRecipe.MAP_CODEC, ConsumingShapedRecipe.STREAM_CODEC)
            );
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<AutoinfuserRecipe>> AUTOINFUSER =
            SERIALIZERS.register("autoinfuser",
                    () -> new RecipeSerializer<>(AutoinfuserRecipe.MAP_CODEC, AutoinfuserRecipe.STREAM_CODEC)
            );

    public static void register(IEventBus bus) {
        SERIALIZERS.register(bus);
    }
}
