package hu.funoro.masterinfuser.data.providers;

import com.blakebr0.mysticalagradditions.MysticalAgradditions;
import com.blakebr0.mysticalagriculture.MysticalAgriculture;
import com.blakebr0.mysticalagriculture.item.EssenceItem;
import hu.funoro.masterinfuser.MasterInfuser;
import hu.funoro.masterinfuser.block.ModBlocks;
import hu.funoro.masterinfuser.data.recipe.AutoinfuserRecipeBuilder;
import hu.funoro.masterinfuser.data.recipe.ConsumingShapedRecipeBuilder;
import hu.funoro.masterinfuser.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
            super(packOutput, registries);
        }

        @Override
        protected @NonNull RecipeProvider createRecipeProvider(HolderLookup.@NonNull Provider provider, @NonNull RecipeOutput recipeOutput) {
            return new ModRecipeProvider(provider, recipeOutput);
        }

        @Override
        public @NonNull String getName() {
            return "Master Infuser Recipes";
        }
    }

    @Override
    protected void buildRecipes() {
        Item masterInfusionCrystal = BuiltInRegistries.ITEM.getValue(Identifier.fromNamespaceAndPath("mysticalagriculture", "master_infusion_crystal"));
        ConsumingShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, ModItems.AUTOINFUSER_CRYSTAL.get())
                .pattern(" D ")
                .pattern("GCG")
                .pattern(" R ")
                .define('D', Items.DIAMOND)
                .define('G', Items.GOLD_INGOT)
                .define('C', masterInfusionCrystal)
                .define('R', Items.REDSTONE)
                .unlockedBy(getHasName(masterInfusionCrystal), has(masterInfusionCrystal))
                .group(MasterInfuser.MOD_ID)
                .save(output);

        Item souliumIngot = BuiltInRegistries.ITEM.getValue(Identifier.fromNamespaceAndPath("mysticalagriculture", "soulium_ingot"));
        Item machineFrame = BuiltInRegistries.ITEM.getValue(Identifier.fromNamespaceAndPath("mysticalagriculture", "machine_frame"));
        shaped(RecipeCategory.MISC, ModBlocks.AUTOINFUSER.get())
                .pattern("ISI")
                .pattern("CFC")
                .pattern("ISI")
                .define('I', Items.IRON_INGOT)
                .define('S', souliumIngot)
                .define('C', ModItems.AUTOINFUSER_CRYSTAL)
                .define('F', machineFrame)
                .unlockedBy(getHasName(ModItems.AUTOINFUSER_CRYSTAL), has(ModItems.AUTOINFUSER_CRYSTAL))
                .group(MasterInfuser.MOD_ID)
                .save(output);

        // Autoinfuser recipes
        EssenceItem inferium = (EssenceItem) BuiltInRegistries.ITEM.getValue(MysticalAgriculture.resource("inferium_essence"));
        EssenceItem prudentium = (EssenceItem) BuiltInRegistries.ITEM.getValue(MysticalAgriculture.resource("prudentium_essence"));
        EssenceItem tertium = (EssenceItem) BuiltInRegistries.ITEM.getValue(MysticalAgriculture.resource("tertium_essence"));
        EssenceItem imperium = (EssenceItem) BuiltInRegistries.ITEM.getValue(MysticalAgriculture.resource("imperium_essence"));
        EssenceItem supremium = (EssenceItem) BuiltInRegistries.ITEM.getValue(MysticalAgriculture.resource("supremium_essence"));
        AutoinfuserRecipeBuilder.tierUp(MasterInfuser.resource("prudentium_autoinfuse"), inferium, prudentium)
                .unlockedBy(getHasName(inferium), has(inferium))
                .save(output);
        AutoinfuserRecipeBuilder.tierUp(MasterInfuser.resource("tertium_autoinfuse"), prudentium, tertium)
                .unlockedBy(getHasName(prudentium), has(prudentium))
                .save(output);
        AutoinfuserRecipeBuilder.tierUp(MasterInfuser.resource("imperium_autoinfuse"), tertium, imperium)
                .unlockedBy(getHasName(tertium), has(tertium))
                .save(output);
        AutoinfuserRecipeBuilder.tierUp(MasterInfuser.resource("supremium_autoinfuse"), imperium, supremium)
                .unlockedBy(getHasName(imperium), has(imperium))
                .save(output);

        // Mystical Agradditions
        com.blakebr0.mysticalagradditions.item.EssenceItem insanium = (com.blakebr0.mysticalagradditions.item.EssenceItem) BuiltInRegistries.ITEM.getValue(MysticalAgradditions.resource("insanium_essence"));
        AutoinfuserRecipeBuilder.tierUp(MasterInfuser.resource("insanium_autoinfuse"), supremium, insanium)
                .unlockedBy(getHasName(supremium), has(supremium))
                .save(output);
    }
}
