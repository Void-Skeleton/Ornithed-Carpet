package carpet.settings.validation;

import com.google.common.collect.Sets;
import net.minecraft.server.ChunkHolder;
import net.minecraft.server.ChunkMap;
import net.minecraft.server.world.ServerWorld;
import carpet.mixins.accessor.ChunkMap_accessor;
import carpet.mixins.accessor.ServerWorld_accessor;
import carpet.server.CarpetServer;

import java.util.Set;

public class ChunkMapCrashFixModifier extends Validators.SideEffectValidator<Boolean> {
	@Override
	public Boolean parseValue(Boolean newValue) {
		return newValue;
	}

	@Override
	public void performEffect(Boolean newValue) {
		ServerWorld[] worlds = CarpetServer.minecraftServer.worlds;
		for (ServerWorld world : worlds) {
			fixChunkMap(((ServerWorld_accessor) world).getChunkMap(), newValue);
		}
	}

	private static void fixChunkMap(ChunkMap chunkMap, boolean crashFix) {
		Set<ChunkHolder> oldDirty = ((ChunkMap_accessor) chunkMap).getDirty();
		Set<ChunkHolder> newDirty = crashFix ? Sets.newConcurrentHashSet() : Sets.newHashSet();
		newDirty.addAll(oldDirty);
		((ChunkMap_accessor) chunkMap).setDirty(newDirty);
	}
}
