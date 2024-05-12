package carpet.mixins.rule.creative.oreUpdateSuppressor;

import net.minecraft.block.Block;
import net.minecraft.block.OreBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.state.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import carpet.settings.CarpetSettings;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(OreBlock.class)
public abstract class OreBlock_oreUpdateSuppressor extends Block {
	public OreBlock_oreUpdateSuppressor(Material material, MaterialColor materialColor) {
		super(material, materialColor);
	}

	@Intrinsic
	@SuppressWarnings("deprecation") @Override
	public void neighborChanged(BlockState state, World world, BlockPos pos, Block neighborBlock, BlockPos neighborPos) {
		if (CarpetSettings.oreUpdateSuppressor && world.hasNeighborSignal(pos))
			throw new ClassCastException("Carpet-simulated update suppression");
	}
}
