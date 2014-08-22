package cz.sam.cubix.world.block;

import cz.sam.cubix.render.texture.IconRegister;
import cz.sam.cubix.world.material.Material;

public class BlockSandStone extends Block {

	public BlockSandStone(int blockID) {
		super(blockID, Material.sandstone);
	}

	@Override
	public void registerIcons(IconRegister par1IconRegister) {
		String[] textures = new String[1];
		textures[0] = par1IconRegister.registerIcon("sandstone.png");
		this.setTextures(textures);
	}

}
