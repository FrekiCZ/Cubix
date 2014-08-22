package cz.sam.cubix.gui;

import cz.sam.cubix.Cubix;
import cz.sam.cubix.render.Color;
import cz.sam.cubix.render.FontRender;

public class GuiButton extends GuiComponent {
	
	public static int WIDTH = 150;
	
	private int id;
	private String string;
	private boolean enabled;
	private boolean focused;
	private ButtonListener listener;
	
	public GuiButton(int id, float x, float y, float width, float height, String string, ButtonListener listener) {
		this.listener = listener;
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.string = string;
		this.enabled = true;
	}
	
	public GuiButton(int id, float x, float y, String string, ButtonListener listener) {
		this(id, x, y, WIDTH, 18, string, listener);
	}
	
	public GuiButton(int id, float x, float y, String string) {
		this(id, x, y, WIDTH, 18, string, null);
	}
	
	public GuiButton(int id, String string, ButtonListener listener) {
		this(id, 0, 0, WIDTH, 18, string, listener);
	}
	
	@Override
	public void drawComponent(Cubix cubix, int x, int y) {
		FontRender font = cubix.fontRender;
		this.focused = x >= this.x && y >= this.y && x < this.x + this.width && y < this.y + this.height;
		
		if(this.enabled) {
			if(this.focused) {
				cubix.textureManager.bindTexture("gui_button_hover");
			} else {
				cubix.textureManager.bindTexture("gui_button");
			}
		} else {
			cubix.textureManager.bindTexture("gui_button");
		}
		
		this.drawTextureQuad(this.x, this.y, this.width, this.height, 0, 0, 1, 1);
		
		if(this.enabled) {
			Color.setColor(255, 255, 255);
		} else {
			Color.setColor(255, 255, 255, 0.5f);
		}
		
		this.drawCenteredString(font, this.string, this.x + this.width / 2, this.y + (this.height - font.getCharHeight()) / 2);
	}
	
	public void handleComponent(Cubix cubix, int x, int y) {
		if(this.enabled && x >= this.x && y >= this.y && x < this.x + this.width && y < this.y + this.height) {
			if(this.listener != null) {
				cubix.soundManager.playSoundFX("click.ogg", 1F, 1F);
				this.listener.buttonClicked(this);
			}
		}
	}
	
	public void addClickListener(ButtonListener listener) {
		this.listener = listener;
	}
	
	public static float toCenter(float x) {
		return x - WIDTH / 2;
	}

	public int getId() {
		return id;
	}

	public String getString() {
		return string;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public boolean isFocused() {
		return focused;
	}

	public GuiButton setEnabled(boolean enabled) {
		this.enabled = enabled;
		return this;
	}
	
}
