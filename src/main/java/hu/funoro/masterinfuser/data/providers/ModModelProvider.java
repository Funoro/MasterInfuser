package hu.funoro.masterinfuser.data.providers;

import com.blakebr0.mysticalagriculture.MysticalAgriculture;
import com.mojang.math.Quadrant;
import hu.funoro.masterinfuser.MasterInfuser;
import hu.funoro.masterinfuser.block.AutoinfuserBlock;
import hu.funoro.masterinfuser.block.ModBlocks;
import hu.funoro.masterinfuser.item.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.renderer.block.dispatch.VariantMutator;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;

public class ModModelProvider extends ModelProvider {
    public ModModelProvider(PackOutput output) {
        super(output, MasterInfuser.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
//        ITEMS
        itemModels.generateFlatItem(ModItems.AUTOINFUSER_CRYSTAL.get(), ModelTemplates.FLAT_ITEM);

        // AUTOINFUSER MODELS
        Material autoinfuserFrontOn = new Material(MasterInfuser.resource("block/autoinfuser_front_on"));
        Material autoinfuserFrontOff = new Material(MasterInfuser.resource("block/autoinfuser_front"));
        Material machineSide = new Material(MysticalAgriculture.resource("block/machine_side"));
        var autoinfuserBlock = ModBlocks.AUTOINFUSER.get();

        Identifier autoinfuserOnModel = blockModels.createSuffixedVariant(
                autoinfuserBlock,
                "_on",
                ModelTemplates.CUBE_ORIENTABLE,
                _ -> new TextureMapping()
                        .put(TextureSlot.FRONT, autoinfuserFrontOn)
                        .put(TextureSlot.SIDE, machineSide)
                        .put(TextureSlot.TOP, machineSide)
        );
        Identifier autoinfuserOffModel = blockModels.createSuffixedVariant(
                autoinfuserBlock,
                "",
                ModelTemplates.CUBE_ORIENTABLE,
                _ -> new TextureMapping()
                        .put(TextureSlot.FRONT, autoinfuserFrontOff)
                        .put(TextureSlot.SIDE, machineSide)
                        .put(TextureSlot.TOP, machineSide)
        );

        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(autoinfuserBlock)
                        .with(PropertyDispatch.initial(
                                AutoinfuserBlock.RUNNING,
                                AutoinfuserBlock.FACING
                        ).generate((running, facing) -> {

                            Identifier model = running
                                    ? autoinfuserOnModel
                                    : autoinfuserOffModel;

                            return BlockModelGenerators.plainVariant(model)
                                    .with(VariantMutator.Y_ROT.withValue(toYRot(facing)));
                        }))
        );
    }
    private static Quadrant toYRot(Direction facing) {
        return switch (facing) {
            case EAST  -> Quadrant.R90;
            case SOUTH -> Quadrant.R180;
            case WEST  -> Quadrant.R270;
            default    -> Quadrant.R0;
        };
    }
}
