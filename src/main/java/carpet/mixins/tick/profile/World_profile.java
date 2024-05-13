package carpet.mixins.tick.profile;

import carpet.tick.TickContext;
import carpet.tick.TypedProfiler;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(World.class)
public abstract class World_profile implements WorldView {
	@Shadow @Final
	public boolean isClient;

	@Redirect(method = "neighborChanged", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/state/BlockState;neighborChanged(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/Block;Lnet/minecraft/util/math/BlockPos;)V"))
	public void profileNeighborUpdates(BlockState instance, World world, BlockPos blockPos, Block neighborBlock, BlockPos neighborPos) {
		if (TickContext.profilingNeighborUpdates && !isClient) {
			TypedProfiler<Block> profiler = TickContext.SERVER_CONTEXT.updateProfiler;
			profiler.swap(instance.getBlock());
			instance.neighborChanged(world, blockPos, neighborBlock, neighborPos);
			profiler.swap(null);
		} else instance.neighborChanged(world, blockPos, neighborBlock, neighborPos);
	}

	@Redirect(method = "tickEntities", at = @At(value = "INVOKE",
		target = "Lnet/minecraft/entity/Entity;tick()V"))
	public void profileGlobalEntities(Entity instance) {
		if (TickContext.profilingEntities && !isClient) {
			TypedProfiler<Class<? extends Entity>> profiler = TickContext.SERVER_CONTEXT.entityProfiler;
			profiler.swap(instance.getClass());
			instance.tick();
			profiler.swap(null);
		} else instance.tick();
	}

	@Redirect(method = "tickEntities", at = @At(value = "INVOKE",
		target = "Lnet/minecraft/world/World;tickEntity(Lnet/minecraft/entity/Entity;)V"))
	public void profileEntities(World instance, Entity entity) {
		if (TickContext.profilingEntities && !isClient) {
			TypedProfiler<Class<? extends Entity>> profiler = TickContext.SERVER_CONTEXT.entityProfiler;
			profiler.swap(entity.getClass());
			instance.tickEntity(entity);
			profiler.swap(null);
		} else instance.tickEntity(entity);
	}

	@Redirect(method = "tickEntities", at = @At(value = "INVOKE",
		target = "Lnet/minecraft/util/Tickable;tick()V"))
	public void profileBlockEntities(Tickable instance) {
		if (TickContext.profilingBlockEntities && !isClient) {
			TypedProfiler<Class<? extends BlockEntity>> profiler = TickContext.SERVER_CONTEXT.blockEntityProfiler;
			BlockEntity blockEntity = (BlockEntity) instance;
			profiler.swap(blockEntity.getClass());
			instance.tick();
			profiler.swap(null);
		} else instance.tick();
	}
}
