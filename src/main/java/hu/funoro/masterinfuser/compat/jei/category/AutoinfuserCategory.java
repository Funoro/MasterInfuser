package hu.funoro.masterinfuser.compat.jei.category;

import com.blakebr0.mysticalagriculture.MysticalAgriculture;
import hu.funoro.masterinfuser.MasterInfuser;
import hu.funoro.masterinfuser.block.ModBlocks;
import hu.funoro.masterinfuser.data.recipe.IAutoinfuserRecipe;
import hu.funoro.masterinfuser.data.recipe.ModRecipeTypes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeHolderType;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.NonNull;

public class AutoinfuserCategory implements IRecipeCategory<RecipeHolder<IAutoinfuserRecipe>> {
    private static final Identifier TEXTURE = MysticalAgriculture.resource("textures/jei/reprocessor.png");
    public static final IRecipeHolderType<IAutoinfuserRecipe> RECIPE_TYPE = IRecipeHolderType.create(ModRecipeTypes.AUTOINFUSER.get());

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableAnimated arrow;

    public AutoinfuserCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 82, 26);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.AUTOINFUSER.get()));

        var arrow = helper.createDrawable(TEXTURE, 85, 0, 24, 17);

        this.arrow = helper.createAnimatedDrawable(arrow, 100, IDrawableAnimated.StartDirection.LEFT, false);
        MasterInfuser.LOGGER.debug("Created AutoinfuserCategory for JEI.");
    }

    @Override
    public @NonNull IRecipeType<RecipeHolder<IAutoinfuserRecipe>> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public @NonNull Component getTitle() {
        return Component.translatable("jei.category.masterinfuser.autoinfuser");
    }

    @Override
    public int getWidth() {
        return this.background.getWidth();
    }

    @Override
    public int getHeight() {
        return this.background.getHeight();
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void draw(@NonNull RecipeHolder<IAutoinfuserRecipe> recipe, @NonNull IRecipeSlotsView slots, @NonNull GuiGraphicsExtractor gfx, double mouseX, double mouseY) {
        this.background.draw(gfx);
        this.arrow.draw(gfx, 24, 4);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<IAutoinfuserRecipe> recipeHolder, @NonNull IFocusGroup focuses) {
        var recipe = recipeHolder.value();
        var inputIngredient = recipe.getIngredient();
        ItemStack input = new ItemStack(inputIngredient.getValues().get(0), 4);
        var result = recipe.assemble(CraftingInput.EMPTY);

        builder.addSlot(RecipeIngredientRole.INPUT, 1, 5).add(input);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 61, 5).add(result);
    }
}
