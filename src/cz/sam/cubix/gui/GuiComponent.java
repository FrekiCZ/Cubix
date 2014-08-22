package cz.sam.cubix.gui;

import cz.sam.cubix.Cubix;

public abstract class GuiComponent extends GuiElements {
	
	public static float default_height = 18F;
	
	protected float x;
	protected float y;
	protected float width;
	protected float height;
	
	public abstract void drawComponent(Cubix cubix, int x, int y);
	
	public void handleComponent(Cubix cubix, int x, int y) {
		
	}
	
	public void mouseClickMove(int x, int y, int mouse_button, long click_time) {
		
	}
	
	public void keyTyped(int key, char event_char) {
		
	}
	
	public void mouseReleased(int x, int y) {
		
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
	
	public void setWidth(float width) {
		this.width = width;
	}
	
	public void setHeight(float height) {
		this.height = height;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public void setLocation(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public float getWindowCenter(Gui gui) {
		return gui.getWidth() / 2 - this.width / 2;
	}
	
	public float getHeightWindowCenter(Gui gui) {
		return gui.getHeight() / 2 - this.getHeight() / 2;
	}
	
}
