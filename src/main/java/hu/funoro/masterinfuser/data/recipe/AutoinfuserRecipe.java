package hu.funoro.masterinfuser.data.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import hu.funoro.masterinfuser.registry.ModRecipeSerializers;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

public class AutoinfuserRecipe implements IAutoinfuserRecipe {
    public static final MapCodec<AutoinfuserRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec((builder) -> builder.group(
            Ingredient.CODEC.fieldOf("input").forGetter((recipe) -> recipe.input),
            ItemStackTemplate.CODEC.fieldOf("result").forGetter((recipe) -> recipe.result)
    ).apply(builder, AutoinfuserRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, AutoinfuserRecipe> STREAM_CODEC = StreamCodec.of(AutoinfuserRecipe::toNetwork, AutoinfuserRecipe::fromNetwork);

    private final Ingredient input;
    private final ItemStackTemplate result;

    public AutoinfuserRecipe(Ingredient input, ItemStackTemplate result) {
        this.input = input;
        this.result = result;
    }

    @Override
    public Ingredient getIngredient() {
        return this.input;
    }

    @Override
    public boolean matches(CraftingInput craftingInput, @NonNull Level level) {
        return this.input.test(craftingInput.getItem(0));
    }

    @Override
    public @NonNull ItemStack assemble(@NonNull CraftingInput craftingInput) {
        return this.result.create();
    }

    @Override
    public @NonNull RecipeSerializer<? extends Recipe<CraftingInput>> getSerializer() {
        return ModRecipeSerializers.AUTOINFUSER.get();
    }

    @Override
    public @NonNull RecipeType<IAutoinfuserRecipe> getType() {
        return ModRecipeTypes.AUTOINFUSER.get();
    }

    private static AutoinfuserRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
        Ingredient input = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
        ItemStackTemplate result = ItemStackTemplate.STREAM_CODEC.decode(buffer);
        return new AutoinfuserRecipe(input, result);
    }

    private static void toNetwork(RegistryFriendlyByteBuf buffer, AutoinfuserRecipe recipe) {
        Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.input);
        ItemStackTemplate.STREAM_CODEC.encode(buffer, recipe.result);
    }
}
