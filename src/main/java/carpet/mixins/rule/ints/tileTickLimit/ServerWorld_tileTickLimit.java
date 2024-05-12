package carpet.mixins.rule.ints.tileTickLimit;

import net.minecraft.server.world.ServerWorld;
import carpet.settings.CarpetSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ServerWorld.class)
public class ServerWorld_tileTickLimit {
	@ModifyConstant(method = "doScheduledTicks", constant = @Constant(intValue = 65536), expect = 2)
	public int modifyTileTickLimit(int constant) {
		return CarpetSettings.tileTickLimit;
	}
}
