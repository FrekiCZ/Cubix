package cz.sam.cubix.gui;

import cz.sam.cubix.Cubix;

public class GuiString extends GuiComponent {
	
	private String string;
	
	public GuiString(float x, float y, String string) {
		this.x = x;
		this.y = y;
		this.string = string;
		this.calculateWidth();
	}
	
	public GuiString(String string) {
		this(0, 0, string);
	}
	
	private void calculateWidth() {
		this.width = Cubix.getCubix().fontRender.getStringWidth(this.string);
		this.height = Cubix.getCubix().fontRender.getCharHeight();
	}

	@Override
	public void drawComponent(Cubix cubix, int x, int y) {
		cubix.fontRender.drawString(this.string, this.x, this.y + this.height / 2, false);
	}

}
