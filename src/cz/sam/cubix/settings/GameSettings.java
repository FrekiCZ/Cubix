package cz.sam.cubix.settings;

import java.io.File;

import org.lwjgl.input.Keyboard;

public class GameSettings {
	
	private Configuration configuration;
	
	public String gameDir;
	public boolean noclip;
	public float soundVolume = 1.0F;
	public float musicVolume = 1.0F;
	public boolean vsyncEnabled = false;
	public boolean drawDefaultCursor = true;
	
	public KeyBinding keyFoward = new KeyBinding("foward", Keyboard.KEY_W);
	public KeyBinding keyBack = new KeyBinding("back", Keyboard.KEY_S);
	public KeyBinding keyLeft = new KeyBinding("left", Keyboard.KEY_A);
	public KeyBinding keyRight = new KeyBinding("right", Keyboard.KEY_D);
    public KeyBinding keyJump = new KeyBinding("jump", Keyboard.KEY_SPACE);
    
    public void loadSettings() {
    	this.configuration = new Configuration(new File(this.gameDir, "Cubix/config.txt"));
    	this.initConfig();
    }
    
    public void saveSettings() {
    	this.configuration.set("soundVolume", this.soundVolume);
    	this.configuration.set("musicVolume", this.musicVolume);
    	this.configuration.set("vsync", this.vsyncEnabled);
    	this.configuration.set("defaultCursor", this.drawDefaultCursor);
    	this.configuration.saveConfig();
    }
    
    private void initConfig() {
    	this.setDefaults();
    	this.soundVolume = this.configuration.getFloat("soundVolume");
    	this.musicVolume = this.configuration.getFloat("musicVolume");
    	this.vsyncEnabled = this.configuration.getBoolean("vsync");
    	this.drawDefaultCursor = this.configuration.getBoolean("defaultCursor");
    }

	private void setDefaults() {
		this.configuration.setDefault("soundVolume", 1.0F);
    	this.configuration.setDefault("musicVolume", 1.0F);
    	this.configuration.setDefault("vsync", false);
    	this.configuration.setDefault("defaultCursor", true);
	}
    
}
