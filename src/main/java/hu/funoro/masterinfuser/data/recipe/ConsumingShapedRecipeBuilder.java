package hu.funoro.masterinfuser.data.recipe;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.level.ItemLike;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class ConsumingShapedRecipeBuilder implements RecipeBuilder {
    private final HolderGetter<Item> items;
    private final RecipeCategory category;
    private final ItemStackTemplate result;
    private final List<String> rows;
    private final Map<Character, Ingredient> key;
    private final RecipeUnlockAdvancementBuilder advancementBuilder;
    private @Nullable String group;
    private boolean showNotification;

    private ConsumingShapedRecipeBuilder(HolderGetter<Item> items, RecipeCategory category, ItemStackTemplate result) {
        this.rows = Lists.newArrayList();
        this.key = Maps.newLinkedHashMap();
        this.advancementBuilder = new RecipeUnlockAdvancementBuilder();
        this.showNotification = true;
        this.items = items;
        this.category = category;
        this.result = result;
    }
    private ConsumingShapedRecipeBuilder(HolderGetter<Item> items, RecipeCategory category, ItemLike result, int count) {
        this(items, category, new ItemStackTemplate(result.asItem(), count));
    }

    public static ConsumingShapedRecipeBuilder shaped(HolderGetter<Item> items, RecipeCategory category, ItemLike item) {
        return shaped(items, category, item, 1);
    }

    public static ConsumingShapedRecipeBuilder shaped(HolderGetter<Item> items, RecipeCategory category, ItemLike item, int count) {
        return new ConsumingShapedRecipeBuilder(items, category, item, count);
    }

    public static ConsumingShapedRecipeBuilder shaped(HolderGetter<Item> items, RecipeCategory category, ItemStackTemplate result) {
        return new ConsumingShapedRecipeBuilder(items, category, result);
    }

    public ConsumingShapedRecipeBuilder define(Character symbol, TagKey<Item> tag) {
        return this.define(symbol, Ingredient.of(this.items.getOrThrow(tag)));
    }

    public ConsumingShapedRecipeBuilder define(Character symbol, ItemLike item) {
        return this.define(symbol, Ingredient.of(item));
    }

    public ConsumingShapedRecipeBuilder define(Character symbol, Ingredient ingredient) {
        if (this.key.containsKey(symbol)) {
            throw new IllegalArgumentException("Symbol '" + symbol + "' is already defined!");
        } else if (symbol == ' ') {
            throw new IllegalArgumentException("Symbol ' ' (whitespace) is reserved and cannot be defined");
        } else {
            this.key.put(symbol, ingredient);
            return this;
        }
    }

    public ConsumingShapedRecipeBuilder pattern(String row) {
        if (!this.rows.isEmpty() && row.length() != this.rows.getFirst().length()) {
            throw new IllegalArgumentException("Pattern must be the same width on every line!");
        } else {
            this.rows.add(row);
            return this;
        }
    }

    public @NonNull ConsumingShapedRecipeBuilder unlockedBy(@NonNull String name, @NonNull Criterion<?> criterion) {
        this.advancementBuilder.unlockedBy(name, criterion);
        return this;
    }
    public @NonNull ConsumingShapedRecipeBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    public ConsumingShapedRecipeBuilder showNotification(boolean showNotification) {
        this.showNotification = showNotification;
        return this;
    }

    @Override
    public @NonNull ResourceKey<Recipe<?>> defaultId() {
        return RecipeBuilder.getDefaultRecipeId(this.result);
    }

    @Override
    public void save(RecipeOutput output, @NonNull ResourceKey<Recipe<?>> id) {
        ShapedRecipePattern pattern = ShapedRecipePattern.of(this.key, this.rows);
        ConsumingShapedRecipe recipe = new ConsumingShapedRecipe(RecipeBuilder.createCraftingCommonInfo(this.showNotification), RecipeBuilder.createCraftingBookInfo(this.category, this.group), pattern, this.result);
        output.accept(id, recipe, this.advancementBuilder.build(output, id, this.category));
    }
}
