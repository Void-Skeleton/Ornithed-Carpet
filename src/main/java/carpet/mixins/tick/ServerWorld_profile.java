package carpet.mixins.tick;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import carpet.utils.duck.ServerWorld_duck;
import carpet.tick.TickContext;
import carpet.tick.TickPhase;
import carpet.tick.TickPhaseProfiler;
import carpet.tick.TypedProfiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerWorld.class)
public abstract class ServerWorld_profile implements ServerWorld_duck {
	@Unique
	private final TypedProfiler<TickPhase> tickPhaseProfiler = new TickPhaseProfiler();
	@Unique
	private final TypedProfiler<Block> updateProfiler = new TypedProfiler<>();
	@Unique
	private final TypedProfiler<Block> tileTickProfiler = new TypedProfiler<>();
	@Unique
	private final TypedProfiler<Block> blockEntityProfiler = new TypedProfiler<>();
	@Unique
	private final TypedProfiler<Class<? extends Entity>> entityProfiler = new TypedProfiler<>();

	@Inject(method = "tick", at = @At(value = "INVOKE_STRING",
		target = "Lnet/minecraft/util/profiler/Profiler;push(Ljava/lang/String;)V",
		args = "ldc=mobSpawner"))
	public void swapToSpawning(CallbackInfo ci) {
		if (TickContext.profilingTickPhases)
			tickPhaseProfiler.swap(TickPhase.SPAWNING);
	}

	@Inject(method = "tick", at = @At(value = "INVOKE_STRING",
		target = "Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V",
		args = "ldc=chunkSource"))
	public void swapToChunkSource(CallbackInfo ci) {
		if (TickContext.profilingTickPhases)
			tickPhaseProfiler.swap(TickPhase.CHUNK_SOURCE);
	}

	@Inject(method = "tick", at = @At(value = "INVOKE_STRING",
		target = "Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V",
		args = "ldc=tickPending"))
	public void swapToTileTick(CallbackInfo ci) {
		if (TickContext.profilingTickPhases)
			tickPhaseProfiler.swap(TickPhase.TILE_TICK);
	}

	@Inject(method = "tick", at = @At(value = "INVOKE_STRING",
		target = "Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V",
		args = "ldc=tickBlocks"))
	public void swapToChunkTick(CallbackInfo ci) {
		if (TickContext.profilingTickPhases)
			tickPhaseProfiler.swap(TickPhase.CHUNK_TICK);
	}

	@Inject(method = "tick", at = @At(value = "INVOKE_STRING",
		target = "Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V",
		args = "ldc=village"))
	public void swapToVillages(CallbackInfo ci) {
		if (TickContext.profilingTickPhases)
			tickPhaseProfiler.swap(TickPhase.VILLAGES);
	}

	@Inject(method = "tick", at = @At(value = "INVOKE",
		target = "Lnet/minecraft/server/world/ServerWorld;doBlockEvents()V"))
	public void swapToBlockEvents(CallbackInfo ci) {
		if (TickContext.profilingTickPhases)
			tickPhaseProfiler.swap(TickPhase.BLOCK_EVENT);
	}

	@Override
	public TypedProfiler<TickPhase> getTickPhaseProfiler() {
		return tickPhaseProfiler;
	}
	@Override
	public TypedProfiler<Block> getUpdateProfiler() {
		return updateProfiler;
	}
	@Override
	public TypedProfiler<Block> getTileTickProfiler() {
		return tileTickProfiler;
	}
	@Override
	public TypedProfiler<Block> getBlockEntityProfiler() {
		return blockEntityProfiler;
	}
	@Override
	public TypedProfiler<Class<? extends Entity>> getEntityProfiler() {
		return entityProfiler;
	}
}
