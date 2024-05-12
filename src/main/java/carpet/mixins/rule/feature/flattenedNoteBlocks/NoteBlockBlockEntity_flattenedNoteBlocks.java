package carpet.mixins.rule.feature.flattenedNoteBlocks;

import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.NoteBlockBlockEntity;
import carpet.utils.duck.NoteBlockBlockEntity_duck;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(NoteBlockBlockEntity.class)
public abstract class NoteBlockBlockEntity_flattenedNoteBlocks extends BlockEntity implements NoteBlockBlockEntity_duck {
	@Unique
	private int instrument = -1;

	@Override
	public void setInstrument(int instrument) {
		int oldInstrument = this.getInstrument();
		this.instrument = instrument;
		if (instrument != oldInstrument) {
			world.updateObservers(pos, Blocks.NOTEBLOCK);
		}
	}

	@Override
	public int getInstrument() {
		if (instrument == -1) instrument = NoteBlockBlockEntity_duck.calculateInstrument(world.getBlockState(pos.down()));
		return instrument;
	}
}
