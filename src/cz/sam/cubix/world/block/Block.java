package cz.sam.cubix.world.block;

import cz.sam.cubix.render.texture.IconRegister;
import cz.sam.cubix.world.material.Material;

public abstract class Block {
	
	public static final Block[] blocksList = new Block[4096];
	
	public static BlockAir air = new BlockAir(0);
	public static BlockStone stone = new BlockStone(1);
	public static BlockGrass grass = new BlockGrass(2);
	public static BlockBedrock bedrock = new BlockBedrock(3);
	public static BlockDirt dirt = new BlockDirt(4);
	public static BlockWaterStill waterStill = new BlockWaterStill(5);
	public static BlockSand sand = new BlockSand(6);
	public static BlockSandStone sandStone = new BlockSandStone(7);
	
	private final int blockID;
	private final Material blockMaterial;
	private String[] texture;
	
	public Block(int blockID, Material material) {
		if(blocksList[blockID] != null) {
			throw new IllegalArgumentException("Slot " + blockID + " is already occupied by " + blocksList[blockID] + " when adding " + this);
		} else {
			blocksList[blockID] = this;
			this.blockID = blockID;
			this.blockMaterial = material;
		}
	}
	
	public abstract void registerIcons(IconRegister par1IconRegister);
	
	public int getBlockID() {
		return this.blockID;
	}
	
	public Material getMaterial() {
		return this.blockMaterial;
	}
	
	public String[] getTextures() {
		return this.texture;
	}
	
	public void setTextures(String[] texture) {
		this.texture = texture;
	}
	
}
