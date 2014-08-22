package cz.sam.cubix.gui;

import cz.sam.cubix.render.FontRender;
import cz.sam.cubix.render.Tessellator;

public class GuiElements {
	
	protected void drawQuad(float x, float y, float width, float height) {
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertex(x,         y,          0);
		tessellator.addVertex(x,         y + height, 0);
		tessellator.addVertex(x + width, y + height, 0);
		tessellator.addVertex(x + width, y,          0);
		tessellator.draw();
	}
	
	protected void drawTextureQuad(float x, float y, float width, float height, float textureX, float textureY, float textureWidth, float textureHeight) {
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(x,         y,          0, textureX,                textureY);
		tessellator.addVertexWithUV(x,         y + height, 0, textureX,                textureY + textureHeight);
		tessellator.addVertexWithUV(x + width, y + height, 0, textureX + textureWidth, textureY + textureHeight);
		tessellator.addVertexWithUV(x + width, y,          0, textureX + textureWidth, textureY);
		tessellator.draw();
	}
	
	protected void drawTextureQuad(float x, float y, float width, float height, float textureX, float textureY, float textureWidth, float textureHeight, boolean default_tex_color) {
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(x,         y,          0, textureX,                textureY);
		tessellator.addVertexWithUV(x,         y + height, 0, textureX,                textureY + textureHeight);
		tessellator.addVertexWithUV(x + width, y + height, 0, textureX + textureWidth, textureY + textureHeight);
		tessellator.addVertexWithUV(x + width, y,          0, textureX + textureWidth, textureY);
		tessellator.draw(default_tex_color);
	}
	
	protected void drawCenteredString(FontRender par1FontRenderer, String par2Str, float par3, float par4) {
        par1FontRenderer.drawString(par2Str, par3 - par1FontRenderer.getStringWidth(par2Str) / 2, par4, false);
    }
	
}
