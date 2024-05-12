package carpet.mixins.rule.population.asyncPacketSyncing;

import net.minecraft.server.ChunkHolder;
import net.minecraft.server.ChunkMap;
import carpet.mixins.accessor.ChunkHolder_accessor;
import carpet.settings.CarpetSettings;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Set;

@Mixin(ChunkMap.class)
public abstract class ChunkMap_asyncPacketSyncing {

	@Redirect(method = "tick", at = @At(value = "INVOKE", target = "Ljava/util/Set;clear()V"))
	public void redirectClearDirtyChunks(Set<ChunkHolder> dirty) {
		if (CarpetSettings.asyncPacketSyncing) {
			// Iterator remove
			// Let's hope that no chunk is added during this, shall we?
			dirty.removeIf(chunkHolder -> ((ChunkHolder_accessor) chunkHolder).getBlocksChanged() == 0);
		} else dirty.clear();
	}
}
