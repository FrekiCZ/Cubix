package cz.sam.cubix.world.block;

import cz.sam.cubix.render.texture.IconRegister;
import cz.sam.cubix.world.material.Material;

public class BlockWaterStill extends Block {

	public BlockWaterStill(int blockID) {
		super(blockID, Material.rock);
	}

	@Override
	public void registerIcons(IconRegister par1IconRegister) {
		String[] textures = new String[1];
		textures[0] = par1IconRegister.registerIcon("water.png");
		this.setTextures(textures);
	}

}
