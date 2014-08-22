package cz.sam.cubix.gui;

import cz.sam.cubix.Cubix;
import cz.sam.cubix.render.Color;
import cz.sam.cubix.render.FontRender;

public class GuiProgressBar extends GuiComponent {
	
	private int value;
	private boolean drawString = true;
	
	public GuiProgressBar(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public GuiProgressBar(float x, float y, float width) {
		this(x, y, width, 20);
	}
	
	public GuiProgressBar(float x, float y) {
		this(x, y, 250, 20);
	}
	
	public GuiProgressBar() {
		this(0, 0);
	}
	
	@Override
	public void drawComponent(Cubix cubix, int x, int y) {
		float xShift = 10;
		float yShift = 10;
		
		int process = (int) (((float) this.value / 100F) * ((float)this.width - xShift));
		int textureProgress = (int) (((float) this.value / 100F) * ((float)this.width));
		
		cubix.textureManager.bindTexture("gui_progress_bar_bg");
		this.drawTextureQuad(this.x, this.y, this.width, this.height, 0, 0, 1, 1);
		
		float t = 1F / (this.width);
		float tex = (t * textureProgress);
		cubix.textureManager.bindTexture("gui_progress_bar_fill");
		this.drawTextureQuad(this.x + (xShift / 2), this.y + (yShift / 2), process, this.height - 10, 0, 0, tex, 1);
		
		if(this.drawString) {
			FontRender font = cubix.fontRender;
			if(this.value > 55) {
				Color.setColor(255, 255, 255);
			} else {
				Color.setColor(255, 255, 255);
			}
			this.drawCenteredString(font, String.valueOf(this.value) + "%", this.x + this.width / 2, (this.y + this.height / 2) - font.getCharHeight() / 2);
		}
		
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public void setStringPainted(boolean flag) {
		this.drawString = flag;
	}
	
	public int getValue() {
		return this.value;
	}
	
}
