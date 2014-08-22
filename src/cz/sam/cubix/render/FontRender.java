package cz.sam.cubix.render;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import cz.sam.cubix.util.FileLocation;
import cz.sam.cubix.util.Glyph;

public class FontRender {
	
	private Map<String, Glyph> glyphSizes = new HashMap<String, Glyph>();
	private TextureManager textureManager;
	private Random rand = new Random();
	private int characterWidth = 14; // 14
	private int characterHeight = 13; //13
	private boolean scaledSize = false;
	private float fontSizeMultiplexer = 0;
	
	public FontRender(TextureManager textureManager) {
		this.textureManager = textureManager;
	}
	
	public void load() {
		this.loadGlyphs();
	}
	
	private void loadGlyphs() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(FileLocation.getFileResourceLocation("assets", "font-sizes.txt"))));
			String line = "";
			while((line = reader.readLine()) != null) {
				if(!line.isEmpty()) {
					if(line.charAt(0) == ';') {
						String[] split = line.split(":");
						Glyph glyph = new Glyph(Float.parseFloat(split[1]), Float.parseFloat(split[2]), split[0].toCharArray()[0]);
						this.glyphSizes.put(split[0], glyph);
					} else {
						String[] split = line.split(";");
						Glyph glyph = new Glyph(Float.parseFloat(split[1]), Float.parseFloat(split[2]), split[0].toCharArray()[0]);
						this.glyphSizes.put(split[0], glyph);
					}
					
				}
			}
			reader.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public void drawString(String string, float x, float y, boolean bold) {
		if(this.scaledSize) {
			//this.setFontSize(FontSize.BIG_SIZE);
		}
		if(bold) {
			this.drawNormalString(string, x + 0.1F, y + 0.5F);
		}
		this.drawNormalString(string, x, y);
		this.reset();
	}
	
	public void drawStringWithShadow(String string, float x, float y) {
		this.drawNormalString(string, x + 0.1F, y + 0.5F);
		this.drawNormalString(string, x, y);
		this.reset();
	}
	
	public void drawRandomString(float x, float y, boolean bold, int length) {
		String str = "";
		for(int i = 0; i < length; i++) {
			str += (char)(this.rand.nextInt(26) + 'a');
		}
		this.drawString(str, x, y, bold);
	}
	
	public void drawRandomRainbowString(float x, float y, boolean bold, int length) {
		GL11.glPushAttrib(GL11.GL_TEXTURE_BIT | GL11.GL_ENABLE_BIT | GL11.GL_COLOR_BUFFER_BIT);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		this.textureManager.bindTexture("font");
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        
		GL11.glPushMatrix();
		int phase = 0;
		int center = 128;
		int width = 127;
		float frequency = (float) (Math.PI * 2 / length);
		for(int i = 0; i < length; i++) {
			int red = (int) (Math.sin(frequency * i + 2 + phase) * width + center);
			int green = (int) (Math.sin(frequency * i + 0 + phase) * width + center);
			int blue = (int) (Math.sin(frequency * i + 4 + phase) * width + center);
			Color.setColor(red, green, blue);
			char c = (char)(this.rand.nextInt(26) + 'a');
			float charWidth = this.renderChar(c, x, y);
			x += charWidth + this.fontSizeMultiplexer;
		}
		GL11.glPopMatrix();
		GL11.glPopAttrib();
	}
	
	public void drawRainbowString(String string, float x, float y) {
		GL11.glPushAttrib(GL11.GL_TEXTURE_BIT | GL11.GL_ENABLE_BIT | GL11.GL_COLOR_BUFFER_BIT);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		this.textureManager.bindTexture("font");
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        
		int phase = 0;
		int center = 128;
		int width = 127;
		float frequency = (float) (Math.PI * 2 / string.length());
		
		GL11.glPushMatrix();
		for (int i = 0; i < string.length(); i++) {
			int red = (int) (Math.sin(frequency * i + 2 + phase) * width + center);
			int green = (int) (Math.sin(frequency * i + 0 + phase) * width + center);
			int blue = (int) (Math.sin(frequency * i + 4 + phase) * width + center);
			Color.setColor(red, green, blue);
			float charWidth = this.renderChar(string.charAt(i), x, y);
			x += charWidth + this.fontSizeMultiplexer;
		}
		GL11.glPopMatrix();
		GL11.glPopAttrib();
	}
	
	private void drawNormalString(String string, float x, float y) {
		GL11.glPushAttrib(GL11.GL_TEXTURE_BIT | GL11.GL_ENABLE_BIT | GL11.GL_COLOR_BUFFER_BIT);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		this.textureManager.bindTexture("font");
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        
		GL11.glPushMatrix();
		for (int i = 0; i < string.length(); i++) {
			float charWidth = this.renderChar(string.charAt(i), x, y);
			x += charWidth + this.fontSizeMultiplexer;
		}
		GL11.glPopMatrix();
		GL11.glPopAttrib();
	}
	
	/*
	 * protected void renderLivingLabel(EntityLivingBase par1EntityLivingBase, String par2Str, double par3, double par5, double par7, int par9)
    {
        double d3 = par1EntityLivingBase.getDistanceSqToEntity(this.renderManager.livingPlayer);

        if (d3 <= (double)(par9 * par9))
        {
            FontRenderer fontrenderer = this.getFontRendererFromRenderManager();
            float f = 1.6F;
            float f1 = 0.016666668F * f;
            GL11.glPushMatrix();
            GL11.glTranslatef((float)par3 + 0.0F, (float)par5 + par1EntityLivingBase.height + 0.5F, (float)par7);
            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
            GL11.glScalef(-f1, -f1, f1);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDepthMask(false);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            Tessellator tessellator = Tessellator.instance;
            byte b0 = 0;

            if (par2Str.equals("deadmau5"))
            {
                b0 = -10;
            }

            GL11.glDisable(GL11.GL_TEXTURE_2D);
            tessellator.startDrawingQuads();
            int j = fontrenderer.getStringWidth(par2Str) / 2;
            tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
            tessellator.addVertex((double)(-j - 1), (double)(-1 + b0), 0.0D);
            tessellator.addVertex((double)(-j - 1), (double)(8 + b0), 0.0D);
            tessellator.addVertex((double)(j + 1), (double)(8 + b0), 0.0D);
            tessellator.addVertex((double)(j + 1), (double)(-1 + b0), 0.0D);
            tessellator.draw();
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            fontrenderer.drawString(par2Str, -fontrenderer.getStringWidth(par2Str) / 2, b0, 553648127);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(true);
            fontrenderer.drawString(par2Str, -fontrenderer.getStringWidth(par2Str) / 2, b0, -1);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glPopMatrix();
        }*/
	
	/*private float renderCharIn3D(char par1Char, float x, float y) {
		Glyph glyph = null;
		if(this.glyphSizes.containsKey(String.valueOf(par1Char))) {
			glyph = this.glyphSizes.get(String.valueOf(par1Char));
		} else {
			glyph = new Glyph(this.characterWidth, this.characterHeight, par1Char);
		}
		int asciiCode = (int) par1Char;
		int gridSize = 16;
		final float cellSize = 1.0f / gridSize;
        float cellX = ((int) asciiCode % gridSize) * cellSize;
        float cellY = ((int) asciiCode / gridSize) * cellSize;
        
        float a = this.characterWidth - glyph.getWidth();
        float b = this.characterHeight - glyph.getHeight();
        
        GL11.glBegin(GL11.GL_QUADS); {
        	GL11.glTexCoord2f(cellX + cellSize, cellY);
			GL11.glVertex3f(x, y, 0);
			
			GL11.glTexCoord2f(cellX + cellSize, cellY + cellSize);
			GL11.glVertex3f(x, y + glyph.getHeight() + b, 0);
			
			GL11.glTexCoord2f(cellX, cellY + cellSize);
			GL11.glVertex3f(x + glyph.getWidth() + a, y + glyph.getHeight() + b, 0);
			
			GL11.glTexCoord2f(cellX, cellY);
			GL11.glVertex3f(x + glyph.getWidth() + a, y, 0);
        } GL11.glEnd();
        
        
		return glyph.getWidth();
	}*/
	
	private float renderChar(char par1Char, float x, float y) {
		Glyph glyph = null;
		if(this.glyphSizes.containsKey(String.valueOf(par1Char))) {
			glyph = this.glyphSizes.get(String.valueOf(par1Char));
		} else {
			glyph = new Glyph(this.characterWidth, this.characterHeight, par1Char);
		}
		int asciiCode = (int) par1Char;
		int gridSize = 16;
		final float cellSize = 1.0f / gridSize;
        float cellX = ((int) asciiCode % gridSize) * cellSize;
        float cellY = ((int) asciiCode / gridSize) * cellSize;
        
        float a = this.characterWidth - glyph.getWidth();
        float b = this.characterHeight - glyph.getHeight();
        
        GL11.glBegin(GL11.GL_QUADS); {
        	GL11.glTexCoord2f(cellX, cellY);
			GL11.glVertex3f(x, y, 0);
			
			GL11.glTexCoord2f(cellX, cellY + cellSize);
			GL11.glVertex3f(x, y + glyph.getHeight() + b, 0);
			
			GL11.glTexCoord2f(cellX + cellSize, cellY + cellSize);
			GL11.glVertex3f(x + glyph.getWidth() + a, y + glyph.getHeight() + b, 0);
			
			GL11.glTexCoord2f(cellX + cellSize, cellY);
			GL11.glVertex3f(x + glyph.getWidth() + a, y, 0);
        } GL11.glEnd();
		
		return glyph.getWidth();
	}
	
	public void setFontSize(FontSize fontSize) {
		if(fontSize == FontSize.BIG_SIZE) {
			this.characterWidth = 21;
			this.characterHeight = 20;
			this.fontSizeMultiplexer = 1.5F;
		} else if(fontSize == FontSize.NORMAL_SIZE) {
			this.reset();
		} if(fontSize == FontSize.SMALL_SIZE) {
			this.characterWidth = 10;
			this.characterHeight = 9;
			this.fontSizeMultiplexer = 0;
		}
	}
	
	public int getStringWidth(String string) {
		if(string == null) {
            return 0;
        } else {
        	int i = 0;
            boolean flag = false;
            
            for(int j = 0; j < string.length(); ++j) {
            	char c0 = string.charAt(j);
            	Glyph g = null;
            	if(this.glyphSizes.containsKey(String.valueOf(c0))) {
            		g = this.glyphSizes.get(String.valueOf(c0));
            	} else {
            		g = new Glyph(this.characterWidth, this.characterHeight, c0);
            	}
            	
            	int k = (int) g.getWidth();
            	
            	if(k < 0 && j < string.length() - 1) {
            		++j;
                    c0 = string.charAt(j);

                    if(c0 != 108 && c0 != 76) {
                        if(c0 == 114 || c0 == 82) {
                            flag = false;
                        }
                    } else {
                        flag = true;
                    }

                    k = 0;
            	}
            	
            	i += k;

                if(flag) {
                    ++i;
                }
            }
            
            return i;
        }
	}
	
	public void setScaleFactor(int factor) {
		if(factor == 1) {
			this.scaledSize = true;
		} else {
			this.scaledSize = false;
		}
	}
	
	private void reset() {
		this.characterWidth = 14;
		this.characterHeight = 13;
		this.fontSizeMultiplexer = 0;
	}
	
	public int getCharHeight() {
		return 11;
	}

	public void setCharacterWidth(int characterWidth) {
		this.characterWidth = characterWidth;
	}

	public void setCharacterHeight(int characterHeight) {
		this.characterHeight = characterHeight;
	}
	
}