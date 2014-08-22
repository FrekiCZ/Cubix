package cz.sam.cubix.gui;

import org.lwjgl.opengl.GL11;

import cz.sam.cubix.Cubix;
import cz.sam.cubix.render.Color;
import cz.sam.cubix.render.FontRender;

public class GuiButtonOnOff extends GuiComponent {
	
	public static float WIDTH = 50;
	
	private boolean onOffSwitch;
	private boolean enabled = true;
	
	public GuiButtonOnOff(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public GuiButtonOnOff(float x, float y) {
		this(x, y, 50, 18);
	}
	
	public GuiButtonOnOff() {
		this(0, 0, 50, 18);
	}
	
	public void drawComponent(Cubix cubix, int x, int y) {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		FontRender font = cubix.fontRender;
		boolean focused = x >= this.x && y >= this.y && x < this.x + this.width && y < this.y + this.height;
		
		if(this.enabled) {
			if(focused) {
				cubix.textureManager.bindTexture("gui_button_hover");
			} else {
				cubix.textureManager.bindTexture("gui_button");
			}
		} else {
			cubix.textureManager.bindTexture("gui_button");
		}
		
		this.drawTextureQuad(this.x, this.y, this.width, this.height, 0, 0, 1, 1);
		
		Color.setColor(255, 255, 255);
		String string = "";
		
		if(this.onOffSwitch) {
			string = "ON";
		} else {
			string = "OFF";
		}
		
		this.drawCenteredString(font, string, this.x + this.width / 2, this.y + (this.height - 10) / 2);
		
	}
	
	@Override
	public void handleComponent(Cubix cubix, int x, int y) {
		if(this.enabled && x >= this.x && y >= this.y && x < this.x + this.width && y < this.y + this.height) {
			cubix.soundManager.playSoundFX("click.ogg", 1F, 1F);
			if(this.onOffSwitch) {
				this.onOffSwitch = false;
			} else {
				this.onOffSwitch = true;
			}
		}
	}
	
	public void setState(boolean state) {
		this.onOffSwitch = state;
	}
	
	public boolean getState() {
		return this.onOffSwitch;
	}
	
	public void setEnabled(boolean flag) {
		this.enabled = flag;
	}
	
}
