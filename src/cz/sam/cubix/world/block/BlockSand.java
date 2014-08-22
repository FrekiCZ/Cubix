package cz.sam.cubix.world.block;

import cz.sam.cubix.render.texture.IconRegister;
import cz.sam.cubix.world.material.Material;

public class BlockSand extends Block {

	public BlockSand(int blockID) {
		super(blockID, Material.sand);
	}

	@Override
	public void registerIcons(IconRegister par1IconRegister) {
		String[] textures = new String[1];
		textures[0] = par1IconRegister.registerIcon("sand.png");
		this.setTextures(textures);
	}

}
