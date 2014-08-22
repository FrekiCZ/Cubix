package cz.sam.cubix.render;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import cz.sam.cubix.Cubix;

public class EntityRender {
	
	public Cubix cubix;
	
	public EntityRender(Cubix cubix) {
		this.cubix = cubix;
	}

	public void updateEntityRenderer(int delta) {
		ScaledResolution scaledResolution = new ScaledResolution(this.cubix.SCREEN_WIDTH, this.cubix.SCREEN_HEIGHT);
		int mX = Mouse.getX() * scaledResolution.getScaledWidth() / this.cubix.SCREEN_WIDTH;
		int mY = scaledResolution.getScaledHeight() - Mouse.getY() * scaledResolution.getScaledHeight() / this.cubix.SCREEN_HEIGHT - 1;
		
		
		
		GL11.glViewport(0, 0, this.cubix.SCREEN_WIDTH, this.cubix.SCREEN_HEIGHT);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        this.setupOverlayRendering();
        
        if(this.cubix.guiScreen != null) {
        	this.cubix.guiScreen.handleInput();
        	if(this.cubix.guiScreen != null) {
        		this.cubix.guiScreen.preDrawScreen(mX, mY, delta);
        	}
        }
        
        if(!this.cubix.gameSettings.drawDefaultCursor) {
        	this.drawMouseCursor(mX, mY);
        }
	}
	
	private void drawMouseCursor(int mX, int mY) {
		int x = mX;
		int y = mY;
		
		this.cubix.textureManager.bindTexture("gui_cursor");
		int width = 16;
		int height = 16;
		
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(x, y, 0, 0, 0);
		tessellator.addVertexWithUV(x, y + height, 0, 0, 1);
		tessellator.addVertexWithUV(x + width, y + height, 0, 1, 1);
		tessellator.addVertexWithUV(x + width, y, 0, 1, 0);
		tessellator.draw();
	}

	public void setupOverlayRendering() {
		ScaledResolution scaledResolution = new ScaledResolution(this.cubix.SCREEN_WIDTH, this.cubix.SCREEN_HEIGHT);
		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0D, scaledResolution.getScaledWidth_double(), scaledResolution.getScaledHeight_double(), 0.0D, 1000.0D, 5000.0D);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
	}

}
