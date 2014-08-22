package cz.sam.cubix.gui;

import cz.sam.cubix.Cubix;
import cz.sam.cubix.render.Color;

public class GuiSlider extends GuiComponent {
	
	private boolean dragged = false;
	private boolean drawPercentageIsPossible = false;
	private boolean focused = false;
	private float draggingButtonWidth = 8F;
	private float draggingButtonHeight = 15F;
	private float sliderValue = 1F;
	
	public GuiSlider(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public GuiSlider(float x, float y) {
		this(x, y, 150, 10);
	}
	
	public GuiSlider() {
		this(0, 0, 150, 10);
	}
	
	@Override
	public void drawComponent(Cubix cubix, int x, int y) {
		this.checkValue();
		cubix.textureManager.bindTexture("gui_slider_bg");
		this.drawTextureQuad(this.x, this.y + this.draggingButtonHeight / 2 - this.height / 2, this.width, this.height, 0, 0, 1, 1);
		
		this.focused = x >= this.x + (int)(this.sliderValue * (float)(this.width - this.draggingButtonWidth)) && y >= this.y && x < this.x + (int)(this.sliderValue * (float)(this.width - this.draggingButtonWidth)) + this.draggingButtonWidth && y < this.y + this.draggingButtonHeight;
		
		if(!this.dragged) {
			if(this.focused) {
				Color.setColor(180, 180, 180, 1.0F);
			} else {
				Color.setColor(255, 255, 255, 0.8F);
			}
		} else {
			Color.setColor(255, 255, 255, 1.0F);
		}
		cubix.textureManager.bindTexture("gui_slider_thumb");
		this.drawTextureQuad(this.x + (int)(this.sliderValue * (float)(this.width - this.draggingButtonWidth)), this.y, this.draggingButtonWidth, this.draggingButtonHeight, 0, 0, 1, 1, false);
		
		if(this.drawPercentageIsPossible) {
			Color.setColor(255, 255, 255, 1F);
			int perValue = this.getPercetangeValue();
			String value = perValue + "%";
			if(perValue == 0) value = "OFF";
			this.drawCenteredString(cubix.fontRender, value, this.x + this.width / 2, this.y + this.height / 2F - cubix.fontRender.getCharHeight() / 2);
		}
	}
	
	@Override
	public void mouseClickMove(int x, int y, int mouse_button, long click_time) {
		if(this.dragged) {
			this.sliderValue = (float)(x - (this.x + this.draggingButtonWidth / 2 - 2F)) / (float)(this.width - this.draggingButtonWidth);
			this.checkValue();
		}
	}
	
	public void handleComponent(Cubix cubix, int x, int y) {
		boolean playSound = false;
		if(x >= this.x + (int)(this.sliderValue * (float)(this.width - this.draggingButtonWidth)) && y >= this.y && x < this.x + (int)(this.sliderValue * (float)(this.width - this.draggingButtonWidth)) + this.draggingButtonWidth && y < this.y + this.draggingButtonHeight) {
			this.dragged = true;
			playSound = true;
		} else if(!this.dragged && x >= this.x && y >= this.y && x < this.x + this.width && y < this.y + this.height) {
			this.sliderValue = (float)(x - (this.x + this.draggingButtonWidth / 2 - 2F)) / (float)(this.width - this.draggingButtonWidth);
			this.dragged = true;
			playSound = true;
		}
		
		if(playSound) {
			cubix.soundManager.playSoundFX("click.ogg", 1F, 1F);
		}
	}
	
	@Override
	public void mouseReleased(int par1, int par2) {
		this.dragged = false;
	}
	
	private void checkValue() {
		if(this.sliderValue < 0.0F) {
            this.sliderValue = 0.0F;
        }

        if(this.sliderValue > 1.0F) {
            this.sliderValue = 1.0F;
        }
	}
	
	public void setDrawPercentage(boolean flag) {
		this.drawPercentageIsPossible = flag;
	}
	
	public void setValue(float value) {
		this.sliderValue = value;
		this.checkValue();
	}
	
	public float getValue() {
		return this.sliderValue;
	}
	
	public int getPercetangeValue() {
		return (int) ((this.sliderValue / 1F) * 100);
	}

}
