package org.carpet.mixins.rule.creative.fillUpdates;

import net.minecraft.block.StructureBlock;
import net.minecraft.block.entity.StructureBlockEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.source.CommandSource;
import org.carpet.settings.CarpetSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.carpet.settings.CarpetSettings.*;

@Mixin(StructureBlock.class)
public abstract class StructureBlock_fillUpdates {
	@Unique
	private static boolean[] recordedYeets = new boolean[5];

	@Inject(method = "activate", at = @At("HEAD"))
	public void saveYeets(StructureBlockEntity structureBlockEntity, CallbackInfo ci) {
		if (!CarpetSettings.fillUpdates) {
			recordedYeets[0] = yeetComparatorUpdates;
			recordedYeets[1] = yeetNeighborUpdates;
			recordedYeets[2] = yeetObserverUpdates;
			recordedYeets[3] = yeetInitialUpdates;
			recordedYeets[4] = yeetRemovalUpdates;
		}
		yeetComparatorUpdates = yeetNeighborUpdates = yeetObserverUpdates =
			yeetInitialUpdates = yeetRemovalUpdates = true;
	}

	@Inject(method = "activate", at = @At("TAIL"))
	public void loadYeets(StructureBlockEntity structureBlockEntity, CallbackInfo ci) {
		if (!CarpetSettings.fillUpdates) {
			yeetComparatorUpdates = recordedYeets[0];
			yeetNeighborUpdates = recordedYeets[1];
			yeetObserverUpdates = recordedYeets[2];
			yeetInitialUpdates = recordedYeets[3];
			yeetRemovalUpdates = recordedYeets[4];
		}
	}
}
