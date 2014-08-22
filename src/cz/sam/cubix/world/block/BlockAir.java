package cz.sam.cubix.world.block;

import cz.sam.cubix.render.texture.IconRegister;
import cz.sam.cubix.world.material.Material;

public class BlockAir extends Block {

	public BlockAir(int blockID) {
		super(blockID, Material.air);
	}

	@Override
	public void registerIcons(IconRegister par1IconRegister) {
		
	}

}
