package hu.funoro.masterinfuser.data.recipe;

import com.blakebr0.cucumber.item.BaseItem;
import com.blakebr0.mysticalagriculture.api.crop.ICropTierProvider;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class AutoinfuserRecipeBuilder implements RecipeBuilder {
    private final Identifier id;
    private final Ingredient input;
    private final ItemStackTemplate result;
    private final RecipeUnlockAdvancementBuilder advancementBuilder = new RecipeUnlockAdvancementBuilder();

    private AutoinfuserRecipeBuilder(Identifier id, Ingredient input, ItemStackTemplate result) {
        this.id = id;
        this.input = input;
        this.result = result;
    }

    public static <T extends BaseItem & ICropTierProvider> AutoinfuserRecipeBuilder tierUp(Identifier id, T inputEssence, T resultEssence) {
        return new AutoinfuserRecipeBuilder(id, Ingredient.of(inputEssence), new ItemStackTemplate(resultEssence));
    }

    @Override
    public @NonNull AutoinfuserRecipeBuilder unlockedBy(@NonNull String name, @NonNull Criterion<?> criterion) {
        this.advancementBuilder.unlockedBy(name, criterion);
        return this;
    }

    @Override
    @Deprecated
    public @NonNull AutoinfuserRecipeBuilder group(@Nullable String group) {
        return this;
    }

    @Override
    public @NonNull ResourceKey<Recipe<?>> defaultId() {
        return ResourceKey.create(Registries.RECIPE, this.id);
    }

    @Override
    public void save(RecipeOutput output, @NonNull ResourceKey<Recipe<?>> id) {
        AutoinfuserRecipe recipe = new AutoinfuserRecipe(this.input, this.result);
        output.accept(defaultId(), recipe, advancementBuilder.build(output, id, RecipeCategory.MISC));
    }
}
