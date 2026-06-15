package hu.funoro.masterinfuser.data.recipe;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import hu.funoro.masterinfuser.registry.ModRecipeSerializers;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.ShapedCraftingRecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Optional;

/// This recipe forcibly consumes all input items.
/// (For example, a bucket of lava will not return the empty bucket.)
public class ConsumingShapedRecipe extends NormalCraftingRecipe {
    public static final MapCodec<ConsumingShapedRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec((i) -> i.group(CommonInfo.MAP_CODEC.forGetter((o) -> o.commonInfo), CraftingBookInfo.MAP_CODEC.forGetter((o) -> o.bookInfo), ShapedRecipePattern.MAP_CODEC.forGetter((o) -> o.pattern), ItemStackTemplate.CODEC.fieldOf("result").forGetter((o) -> o.result)).apply(i, ConsumingShapedRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, ConsumingShapedRecipe> STREAM_CODEC;

    public final ShapedRecipePattern pattern;
    private final ItemStackTemplate result;

    public ConsumingShapedRecipe(Recipe.CommonInfo commonInfo, CraftingRecipe.CraftingBookInfo bookInfo, ShapedRecipePattern pattern, ItemStackTemplate result) {
        super(commonInfo, bookInfo);
        this.pattern = pattern;
        this.result = result;
    }

    @Override
    public @NonNull RecipeSerializer<? extends NormalCraftingRecipe> getSerializer() {
        return ModRecipeSerializers.CONSUMING_SHAPED.get();
    }

    @VisibleForTesting
    public List<Optional<Ingredient>> getIngredients() {
        return this.pattern.ingredients();
    }

    protected @NonNull PlacementInfo createPlacementInfo() {
        return PlacementInfo.createFromOptionals(this.pattern.ingredients());
    }

    @Override
    public boolean matches(@NonNull CraftingInput input, @NonNull Level level) {
        return this.pattern.matches(input);
    }

    @Override
    public @NonNull ItemStack assemble(@NonNull CraftingInput input) {
        return this.result.create();
    }

    public int getWidth() {
        return this.pattern.width();
    }

    public int getHeight() {
        return this.pattern.height();
    }

    @Override
    public @NonNull List<RecipeDisplay> display() {
        return List.of(new ShapedCraftingRecipeDisplay(this.pattern.width(), this.pattern.height(), this.pattern.ingredients().stream().map((e) -> e.map(Ingredient::display).orElse(SlotDisplay.Empty.INSTANCE)).toList(), new SlotDisplay.ItemStackSlotDisplay(this.result), new SlotDisplay.ItemSlotDisplay(Items.CRAFTING_TABLE)));
    }

    @Override
    public @NonNull NonNullList<ItemStack> getRemainingItems(CraftingInput input) {
        return NonNullList.withSize(input.size(), ItemStack.EMPTY);
    }

    static {
        STREAM_CODEC = StreamCodec.composite(CommonInfo.STREAM_CODEC, (o) -> o.commonInfo, CraftingBookInfo.STREAM_CODEC, (o) -> o.bookInfo, ShapedRecipePattern.STREAM_CODEC, (o) -> o.pattern, ItemStackTemplate.STREAM_CODEC, (o) -> o.result, ConsumingShapedRecipe::new);
    }
}
