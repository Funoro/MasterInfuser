package hu.funoro.masterinfuser.tileentity;

import hu.funoro.masterinfuser.MasterInfuser;
import hu.funoro.masterinfuser.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModTileEntities {
    public static final DeferredRegister<BlockEntityType<?>> REGISTRY;
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AutoinfuserTileEntity>> AUTOINFUSER;

    private static <T extends BlockEntity> DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<T> tile, Supplier<Block[]> blocks) {
        return REGISTRY.register(name, () -> new BlockEntityType<>(tile, blocks.get()));
    }

    static {
        REGISTRY = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MasterInfuser.MOD_ID);
        AUTOINFUSER = register("autoinfuser", AutoinfuserTileEntity::new, () -> new Block[]{ModBlocks.AUTOINFUSER.get()});
    }
}
