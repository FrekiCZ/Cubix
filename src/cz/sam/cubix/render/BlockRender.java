package cz.sam.cubix.render;

import org.lwjgl.opengl.GL11;

import cz.sam.cubix.render.texture.TextureSide;
import cz.sam.cubix.world.block.Block;
import cz.sam.cubix.world.material.Material;

public class BlockRender {
	
	public void renderBlock(TextureManager textureManager, int x, int y, int z, int blockID, int[] sides, float size) {
		Block block = Block.blocksList[blockID];
		if(block == null) {
			return;
		}
		
		if(block.getMaterial() != Material.air) {
			String[] textures = block.getTextures();
			float par1 = size;
			float par2 = par1 / 2;
			
			if(block.getMaterial() == Material.grass) {
				GL11.glColor3f(75F / 255F, 205F / 255F, 55F / 255F);
			}
			
			if(block.getTextures().length > 1) {
				
				if(sides[TextureSide.TOP_TEXTURE] == 1) {
					textureManager.bindTexture(textures[TextureSide.TOP_TEXTURE]);
					this.renderQuad(new float[] { x - par2, y, z - par2 }, new float[] { x - par2, y, z + par2 }, new float[] { x + par2, y, z + par2 }, new float[] { x + par2, y, z - par2 });
				}
				
				if(block.getMaterial() == Material.grass) {
					GL11.glColor3f(255F / 255F, 255F / 255F, 255F / 255F);
				}
				
				if(sides[TextureSide.BOTTOM_TEXTURE] == 1) {
					textureManager.bindTexture(textures[TextureSide.BOTTOM_TEXTURE]);
					this.renderQuad(new float[] { x - par2, y - par1, z - par2 }, new float[] { x + par2, y - par1, z - par2 }, new float[] { x + par2, y - par1, z + par2 }, new float[] { x - par2, y - par1, z + par2  });
				}
				
				if(sides[TextureSide.FRONT_TEXTURE] == 1) {
					textureManager.bindTexture(textures[TextureSide.FRONT_TEXTURE]);
					this.renderQuad(new float[] { x - par2, y, z - par2 }, new float[] { x - par2, y - par1, z - par2 }, new float[] { x - par2, y - par1, z + par2 }, new float[] { x - par2, y, z + par2 });
				}
				
				if(sides[TextureSide.BACK_TEXTURE] == 1) {
					textureManager.bindTexture(textures[TextureSide.BACK_TEXTURE]);
					this.renderQuad(new float[] { x + par2, y, z + par2 }, new float[] { x + par2, y - par1, z + par2 }, new float[] { x + par2, y - par1, z - par2 }, new float[] { x + par2, y, z - par2 });
				}
				
				if(sides[TextureSide.LEFT_TEXTURE] == 1) {
					textureManager.bindTexture(textures[TextureSide.LEFT_TEXTURE]);
					this.renderQuad(new float[] { x + par2, y, z - par2 }, new float[] { x + par2, y - par1, z - par2 },  new float[] { x - par2, y - par1, z - par2 },  new float[] { x - par2, y, z - par2 });
				}
				
				if(sides[TextureSide.RIGHT_TEXTURE] == 1) {
					textureManager.bindTexture(textures[TextureSide.RIGHT_TEXTURE]);
					this.renderQuad(new float[] { x - par2, y, z + par2 }, new float[] { x - par2, y - par1, z + par2 },  new float[] { x + par2, y - par1, z + par2 },  new float[] { x + par2, y, z + par2 });
				}
				
				
			} else {
				textureManager.bindTexture(textures[0]);
				if(sides[TextureSide.TOP_TEXTURE] == 1) {
					this.renderQuad(new float[] { x - par2, y, z - par2 }, new float[] { x - par2, y, z + par2 }, new float[] { x + par2, y, z + par2 }, new float[] { x + par2, y, z - par2 });
				}
				
				if(block.getMaterial() == Material.grass) {
					GL11.glColor3f(255F / 255F, 255F / 255F, 255F / 255F);
				}
				
				if(sides[TextureSide.BOTTOM_TEXTURE] == 1) {
					this.renderQuad(new float[] { x - par2, y - par1, z - par2 }, new float[] { x + par2, y - par1, z - par2 }, new float[] { x + par2, y - par1, z + par2 }, new float[] { x - par2, y - par1, z + par2  });
				}
				
				if(sides[TextureSide.FRONT_TEXTURE] == 1) {
					this.renderQuad(new float[] { x - par2, y, z - par2 }, new float[] { x - par2, y - par1, z - par2 }, new float[] { x - par2, y - par1, z + par2 }, new float[] { x - par2, y, z + par2 });
				}
				
				if(sides[TextureSide.BACK_TEXTURE] == 1) {
					this.renderQuad(new float[] { x + par2, y, z + par2 }, new float[] { x + par2, y - par1, z + par2 }, new float[] { x + par2, y - par1, z - par2 }, new float[] { x + par2, y, z - par2 });
				}
				
				if(sides[TextureSide.LEFT_TEXTURE] == 1) {
					this.renderQuad(new float[] { x + par2, y, z - par2 }, new float[] { x + par2, y - par1, z - par2 },  new float[] { x - par2, y - par1, z - par2 },  new float[] { x - par2, y, z - par2 });
				}
				
				if(sides[TextureSide.RIGHT_TEXTURE] == 1) {
					this.renderQuad(new float[] { x - par2, y, z + par2 }, new float[] { x - par2, y - par1, z + par2 },  new float[] { x + par2, y - par1, z + par2 },  new float[] { x + par2, y, z + par2 });
				}
			}
		}
	}
	
	public void renderBlock(float x, float y, float z, float size) {
		float par1 = size;
		float par2 = par1 / 2;
		GL11.glColor3f(255F / 255F, 255F / 255F, 255F / 255F);
		this.renderQuad(new float[] { x - par2, y, z - par2 }, new float[] { x - par2, y, z + par2 }, new float[] { x + par2, y, z + par2 }, new float[] { x + par2, y, z - par2 });
		this.renderQuad(new float[] { x - par2, y - par1, z - par2 }, new float[] { x + par2, y - par1, z - par2 }, new float[] { x + par2, y - par1, z + par2 }, new float[] { x - par2, y - par1, z + par2  });
		this.renderQuad(new float[] { x - par2, y, z - par2 }, new float[] { x - par2, y - par1, z - par2 }, new float[] { x - par2, y - par1, z + par2 }, new float[] { x - par2, y, z + par2 });
		this.renderQuad(new float[] { x + par2, y, z + par2 }, new float[] { x + par2, y - par1, z + par2 }, new float[] { x + par2, y - par1, z - par2 }, new float[] { x + par2, y, z - par2 });
		this.renderQuad(new float[] { x + par2, y, z - par2 }, new float[] { x + par2, y - par1, z - par2 },  new float[] { x - par2, y - par1, z - par2 },  new float[] { x - par2, y, z - par2 });
		this.renderQuad(new float[] { x - par2, y, z + par2 }, new float[] { x - par2, y - par1, z + par2 },  new float[] { x + par2, y - par1, z + par2 },  new float[] { x + par2, y, z + par2 });
	}
	
	private void renderQuad(float[] par1, float[] par2, float[] par3, float[] par4) {
		GL11.glBegin(GL11.GL_QUADS); {
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex3f(par1[0], par1[1], par1[2]);
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex3f(par2[0], par2[1], par2[2]);
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex3f(par3[0], par3[1], par3[2]);
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex3f(par4[0], par4[1], par4[2]);
		} GL11.glEnd();
	}
	
}
