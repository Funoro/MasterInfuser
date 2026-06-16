package hu.funoro.masterinfuser.compat;

import hu.funoro.masterinfuser.MasterInfuser;
import hu.funoro.masterinfuser.block.AutoinfuserBlock;
import hu.funoro.masterinfuser.tileentity.AutoinfuserTileEntity;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;
import snownee.jade.api.*;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.JadeUI;

@WailaPlugin
public class JadeCompat implements IWailaPlugin {
    private static final Identifier AUTOINFUSER = MasterInfuser.resource("autoinfuser");

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(new IBlockComponentProvider() {
            @Override
            public void appendTooltip(@NonNull ITooltip tooltip, @NonNull BlockAccessor accessor, @NonNull IPluginConfig config) {
                if (accessor.getBlockEntity() instanceof AutoinfuserTileEntity infuser) {
                    var input = infuser.getInventory().getStacks().getFirst();
                    var fuel = infuser.getInventory().getStacks().get(1);
                    var output = infuser.getInventory().getStacks().get(2);
                    if (!input.isEmpty() || !fuel.isEmpty() || !output.isEmpty()) {
                        tooltip.add(JadeUI.item(input).alignSelfCenter());
                        tooltip.append(JadeUI.item(fuel).alignSelfCenter());
                        tooltip.append(JadeUI.progressArrow(infuser.getOperationTime() == 0 ? 0.0F : (float) infuser.getProgress() / (float) infuser.getOperationTime()).alignSelfCenter().settings(($) -> $.paddingHorizontal(3)));
                        tooltip.append(JadeUI.item(output).alignSelfCenter());
                    }
                }
            }

            @Override
            public @NonNull Identifier getUid() {
                return AUTOINFUSER;
            }
        },
                AutoinfuserBlock.class);
    }
}
