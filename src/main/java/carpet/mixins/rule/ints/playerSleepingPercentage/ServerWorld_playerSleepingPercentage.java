package carpet.mixins.rule.ints.playerSleepingPercentage;

import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.BlockableEventLoop;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.world.World;
import net.minecraft.world.WorldData;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.storage.WorldStorage;
import carpet.settings.CarpetSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(ServerWorld.class)
public abstract class ServerWorld_playerSleepingPercentage extends World implements BlockableEventLoop {
	protected ServerWorld_playerSleepingPercentage(WorldStorage storage, WorldData data, Dimension dimension, Profiler profiler, boolean isClient) {
		super(storage, data, dimension, profiler, isClient);
	}

	@Shadow
	private boolean allPlayersSleeping;

	// Compiler generates a warning for the use of the internal class Opcodes
	// I'll just use a raw constant instead
	@Redirect(method = "updateSleepingPlayers", at = @At(value = "FIELD",
		target = "Lnet/minecraft/server/world/ServerWorld;allPlayersSleeping:Z", opcode = 181 /* Opcodes.PUTFIELD */),
		slice = @Slice(from = @At(value = "INVOKE", target = "Ljava/util/List;isEmpty()Z")))
	public void customPlayerSleepingPercentage(ServerWorld world, boolean vanillaResult) {
		int percentage = CarpetSettings.playersSleepingPercentage;
		if (percentage == 100) {
			allPlayersSleeping = vanillaResult;
			return;
		}
		// Recalculate everything - it isn't much computation for a modern computer
		if (!this.players.isEmpty()) {
			int spectators = 0;
			int sleepers = 0;

			for(PlayerEntity playerEntity : this.players) {
				if (playerEntity.isSpectator()) {
					++spectators;
				} else if (playerEntity.isSleeping()) {
					++sleepers;
				}
			}
			allPlayersSleeping = sleepers > 0 &&
				100 * sleepers >= percentage * (this.players.size() - spectators);
		}
	}
}
