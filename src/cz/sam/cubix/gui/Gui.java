package cz.sam.cubix.gui;

import java.awt.Toolkit;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import cz.sam.cubix.Cubix;
import cz.sam.cubix.render.Color;
import cz.sam.cubix.render.FontRender;

public abstract class Gui extends GuiElements {
	
	private List<GuiComponent> components = new ArrayList<GuiComponent>();
	protected Cubix cubix;
	private int width;
	private int height;
	private int eventButton;
    private long lastMouseEvent;
    private int memoryUpdate;
    private int lastUsedMemory;
    private int lastFreeMemory;
    private int lastMaxMemory;
	
	public Gui() { }
	
	public abstract void guiOpened();
	public abstract void initGui();
	public abstract void drawScreen(int x, int y, float delta);
	
	public void mouseClicked(int x, int y, int mouse_button) {
		
	}
	
	public void mouseClickMove(int x, int y, int mouse_button, long click_time) {
		for(GuiComponent component : this.components) {
			component.mouseClickMove(x, y, mouse_button, click_time);
		}
	}
	
	public void keyTyped(int key, char event_char) {
		
	}
	
	public void mouseReleased(int x, int y) {
		for(GuiComponent component : this.components) {
			component.mouseReleased(x, y);
		}
	}
	
	public void updateScreen() {
		
	}
	
	public abstract void onGuiClosing();
	
	public void preDrawScreen(int x, int y, float delta) {
		this.drawScreen(x, y, delta);
		this.drawComponents(x, y);
	}
	
	public void updateResolution(Cubix cubix, int width, int height) {
		this.cubix = cubix;
		this.width = width;
		this.height = height;
		this.components.clear();
		this.initGui();
	}
	
	public void handleInput() {
		while(Mouse.next()) {
            this.mouseInput();
        }

        while(Keyboard.next()) {
            this.keyboardInput();
        }
	}
	
	private void mouseInput() {
		int x = Mouse.getEventX() * this.width / this.cubix.SCREEN_WIDTH;
		int y = this.height - Mouse.getEventY() * this.height / this.cubix.SCREEN_HEIGHT - 1;
		int b_event = Mouse.getEventButton();
		if(Mouse.getEventButtonState()) {
			this.eventButton = b_event;
			this.lastMouseEvent = Cubix.getSystemTime();
			this.mouseClicked(x, y, this.eventButton);
			this.handlComponentClick(x, y);
		} else if(b_event != -1) {
			this.mouseReleased(x, y);
		} else if(this.eventButton != -1 && this.lastMouseEvent > 0L) {
			long click_time = Cubix.getSystemTime() - this.lastMouseEvent;
            this.mouseClickMove(x, y, this.eventButton, click_time);
		}
	}

	private void keyboardInput() {
		if(Keyboard.getEventKeyState()) {
			int key = Keyboard.getEventKey();
            char event_char = Keyboard.getEventCharacter();
            this.keyTyped(key, event_char);
		}
	}

	public static String getClipboardString() {
        try {
            Transferable transferable = Toolkit.getDefaultToolkit().getSystemClipboard().getContents((Object)null);

            if(transferable != null && transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                return (String)transferable.getTransferData(DataFlavor.stringFlavor);
            }
        } catch (Exception exception) { }
        return "";
    }
    
    public static void setClipboardString(String par0Str) {
        try {
        	StringSelection stringselection = new StringSelection(par0Str);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringselection, (ClipboardOwner)null);
        } catch (Exception exception) { }
    }
	
	public void drawString(FontRender par1FontRenderer, String par2Str, float par3, float par4) {
        par1FontRenderer.drawString(par2Str, par3, par4, false);
    }
	
	public void drawCenteredString(String string, float par3, float par4) {
		FontRender font = this.getCubix().fontRender;
		font.drawString(string, par3 - font.getStringWidth(string) / 2, par4, false);
    }
	
	public void drawMemory(float x, float y) {
		int mb = 1024 * 1024;
		Runtime runtime = Runtime.getRuntime();
		if(this.memoryUpdate == 0) {
			this.lastUsedMemory = (int) ((runtime.totalMemory() - runtime.freeMemory()) / mb);
			this.lastMaxMemory = (int) (runtime.maxMemory() / mb);
			this.lastFreeMemory = (int) ((runtime.maxMemory() / mb) - ((runtime.totalMemory() - runtime.freeMemory()) / mb));
			this.memoryUpdate++;
		} else {
			this.memoryUpdate++;
			if(this.memoryUpdate > 40){
				this.memoryUpdate = 0;
			}
		}
		this.drawString(this.cubix.fontRender, "Max memory: " + this.lastMaxMemory + "MB", x, y);
		this.drawString(this.cubix.fontRender, "Used memory: " + this.lastUsedMemory + "MB", x, y + 10);
		this.drawString(this.cubix.fontRender, "Free memory: " + this.lastFreeMemory + "MB", x, y + 20);
	}
	
	public void drawDefaultBackground() {
		this.getCubix().textureManager.bindTexture("gui_background");
		float textureSize = this.width;
		for(float x = 0; x < ((float) this.width) / textureSize; x++) {
			for(float y = 0; y < ((float) this.height) / textureSize; y++) {
				float xPos = x * textureSize;
				float yPos = y * textureSize;
				this.drawTextureQuad(xPos, yPos, textureSize, textureSize, 0, 0, 1, 1);
			}
		}
	}
	
	public void drawCopyright() {
		Color.setColor(200, 200, 200);
		this.drawString(this.getCubix().fontRender, "Copyright © 2014", 5, this.getHeight() - 15);
	}
	
	public void addComponent(GuiComponent component) {
		this.components.add(component);
	}
	
	public void drawComponents(int x, int y) {
		for(GuiComponent component : this.components) {
			component.drawComponent(this.getCubix(), x, y);
		}
	}
	
	public void handlComponentClick(int x, int y) {
		for(GuiComponent component : this.components) {
			component.handleComponent(this.cubix, x, y);
		}
	}

	public Cubix getCubix() {
		return cubix;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setCubix(Cubix cubix) {
		this.cubix = cubix;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
}
