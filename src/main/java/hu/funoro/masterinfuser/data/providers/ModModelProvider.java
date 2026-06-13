package hu.funoro.masterinfuser.data.providers;

import hu.funoro.masterinfuser.MasterInfuser;
import hu.funoro.masterinfuser.block.ModBlocks;
import hu.funoro.masterinfuser.item.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;

public class ModModelProvider extends ModelProvider {
    public ModModelProvider(PackOutput output) {
        super(output, MasterInfuser.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
//        ITEMS
        itemModels.generateFlatItem(ModItems.AUTOINFUSER_CRYSTAL.get(), ModelTemplates.FLAT_ITEM);

//        BLOCKS
        blockModels.createTrivialCube(ModBlocks.AUTOINFUSER.get());
    }
}
