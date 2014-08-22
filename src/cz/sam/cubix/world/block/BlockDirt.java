package cz.sam.cubix.world.block;

import cz.sam.cubix.render.texture.IconRegister;
import cz.sam.cubix.world.material.Material;

public class BlockDirt extends Block {

	public BlockDirt(int blockID) {
		super(blockID, Material.dirt);
	}

	@Override
	public void registerIcons(IconRegister par1IconRegister) {
		String[] textures = new String[1];
		textures[0] = par1IconRegister.registerIcon("dirt.png");
		this.setTextures(textures);
	}

}
