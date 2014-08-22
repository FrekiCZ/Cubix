package cz.sam.cubix.world.block;

import cz.sam.cubix.render.texture.IconRegister;
import cz.sam.cubix.render.texture.TextureSide;
import cz.sam.cubix.world.material.Material;

public class BlockGrass extends Block {

	public BlockGrass(int blockID) {
		super(blockID, Material.grass);
	}
	
	public void registerIcons(IconRegister par1IconRegister) {
		String[] textures = new String[TextureSide.MULTITEXTURE];
		textures[TextureSide.TOP_TEXTURE] = par1IconRegister.registerIcon("grass_top.png");
		textures[TextureSide.BOTTOM_TEXTURE] = par1IconRegister.registerIcon("dirt.png");
		textures[TextureSide.FRONT_TEXTURE] = par1IconRegister.registerIcon("grass_side.png");
		textures[TextureSide.BACK_TEXTURE] = par1IconRegister.registerIcon("grass_side.png");
		textures[TextureSide.LEFT_TEXTURE] = par1IconRegister.registerIcon("grass_side.png");
		textures[TextureSide.RIGHT_TEXTURE] = par1IconRegister.registerIcon("grass_side.png");
		this.setTextures(textures);
	}

}
