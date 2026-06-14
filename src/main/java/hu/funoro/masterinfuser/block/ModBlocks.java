package hu.funoro.masterinfuser.block;

import hu.funoro.masterinfuser.MasterInfuser;
import hu.funoro.masterinfuser.item.ModItems;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

public class ModBlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MasterInfuser.MOD_ID);

    public static final DeferredBlock<Block> AUTOINFUSER = registerBlock("autoinfuser", AutoinfuserBlock::new);


    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<Identifier, T> block) {
        DeferredBlock<T> dblock = BLOCKS.register(name, block);
        registerBlockItem(name, dblock);
        return dblock;
    }
    /*
    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> function) {
        DeferredBlock<T> block = BLOCKS.registerBlock(name, function);
        registerBlockItem(name, block);
        return block;
    }
     */

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.registerItem(name, properties -> new BlockItem(block.get(), properties.useBlockDescriptionPrefix()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
