package cz.sam.cubix;

import java.awt.Canvas;

import org.lwjgl.Sys;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

import cz.sam.cubix.gui.Gui;
import cz.sam.cubix.gui.screen.GuiMainMenu;
import cz.sam.cubix.log.Log;
import cz.sam.cubix.render.*;
import cz.sam.cubix.render.texture.TextureRegister;
import cz.sam.cubix.settings.GameSettings;
import cz.sam.cubix.settings.KeyBinding;
import cz.sam.cubix.sound.SoundManager;
import cz.sam.cubix.util.*;
import cz.sam.cubix.world.World;
import cz.sam.cubix.world.block.Block;

public class Cubix {
	
	private static Cubix intance;
	
	private boolean isApplet;
	public int SCREEN_WIDTH = 1000;
	public int SCREEN_HEIGHT = 600;
	
	private Canvas parent_canvas;
	
	public Session session;
	public GameSettings gameSettings;
	public TextureManager textureManager;
	public TextureRegister textureLoader;
	public FontRender fontRender;
	public EntityRender entityRender;
	public Gui guiScreen;
	public World world;
	private Timer timer = new Timer(20);
	public FPSHelper fpsHelper = new FPSHelper();
	public SoundManager soundManager;
	
	public boolean isRunning = true;
	public boolean isMouseFocused;
	
	public Cubix(Session session, boolean isApplet) {
		Cubix.intance = this;
		this.isApplet = isApplet;
		this.session = session;
		Log.info("Starting game...");
		Log.info("Player username: " + this.session.getUsername());
		this.gameSettings = new GameSettings();
		this.textureManager = new TextureManager();
		this.textureLoader = new TextureRegister(this);
		this.fontRender = new FontRender(this.textureManager);
		FileLocation.addLocation("assets", "assets");
		FileLocation.addLocation("textures", "assets/textures");
		FileLocation.addLocation("font", "assets/textures/font");
		FileLocation.addLocation("icon", "assets/textures/icon");
		FileLocation.addLocation("splash", "assets/textures/splash");
		FileLocation.addLocation("gui", "assets/textures/gui");
		FileLocation.addLocation("world", "assets/textures/world");
		FileLocation.addLocation("blocks", "assets/textures/world/blocks");
		FileLocation.addLocation("audio", "assets/audio");
		FileLocation.addLocation("shaders", "assets/shaders");
		this.gameSettings.loadSettings();
	}
	
	public void start() throws Exception {
		Log.info("Creting screen.");
		this.createScreen();
		Log.info("Loading game...");
		this.loadScreen();
		this.loadResources();
		this.runLoop();
	}
	
	private void createScreen() throws Exception {
		if(this.isApplet) {
			Display.setParent(this.parent_canvas);
		}
		Display.setDisplayMode(new DisplayMode(this.SCREEN_WIDTH ,this.SCREEN_HEIGHT));
		Display.setResizable(true);
		Display.setTitle("Cubix");
		Display.setIcon(GLUtil.loadIcon());
		PixelFormat pixelformat = new PixelFormat();
        pixelformat = pixelformat.withDepthBits(24);
		Display.create(pixelformat);
		if(!this.gameSettings.drawDefaultCursor) {
			Mouse.setNativeCursor(new Cursor(32, 32, 0, 30, 1, GLUtil.getMouseCursor(), null));
		}
	}

	private void loadScreen() throws Exception {
		this.textureManager.loadTexture("cubix_splash", FileLocation.getResourceLocation("splash", "cubix_splash.png"));
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0.0D, Display.getWidth(), Display.getHeight(), 0.0D, 1000.0D, 3000.0D);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
		GL11.glViewport(0, 0, this.SCREEN_WIDTH, this.SCREEN_HEIGHT);
		GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
		GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_FOG);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
        //this.textureManager.bindTexture("cubix_splash");
        //SplashLoadScreen splash2 = new SplashLoadScreen(this, 0);
        //splash2.drawSplashScreen();
        
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_FOG);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
        Display.update();
	}

	private void loadResources() throws Exception {
		this.fontRender.load();
		this.textureLoader.registerAllTextures();
		
		for(int i = 0;i < Block.blocksList.length; i++) {
			Block block = Block.blocksList[i];
			if(block != null) {
				block.registerIcons(this.textureLoader);
			}
		}
		this.soundManager = new SoundManager(this.gameSettings, FileLocation.getFileLocation("audio"));
	}

	private void runLoop() throws Exception {
		this.entityRender = new EntityRender(this);
		this.displayGui(new GuiMainMenu());
		GL11.glViewport(0, 0, this.SCREEN_WIDTH, this.SCREEN_HEIGHT);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        this.soundManager.playMusic("bgMusic", "mainMenu.ogg");
        this.fpsHelper.getDelta();
		this.fpsHelper.init();
		while(this.isRunning) {
			this.fpsHelper.updateFPS();
			if(Display.isCloseRequested()) {
				this.shutdown();
			}
			
			GL11.glClearColor(0f, 0f, 0f, 1.0f);
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); 
			GL11.glLoadIdentity();
			
			this.timer.updateTimer();
			
			for(int i = 0; i < this.timer.elapsedTicks; ++i) {
				this.tick();
			}
			
			this.entityRender.updateEntityRenderer(this.fpsHelper.getDelta());
			
			this.onResize();
			
			Display.sync(60);
			Display.update();
		}
		this.gameSettings.saveSettings();
		this.soundManager.cleanup();
		Log.info("Exit.");
		Display.destroy();
		System.exit(0);
	}
	
	public void tick() {
		if(this.guiScreen != null) {
			this.guiScreen.updateScreen();
		}
		
		while(Keyboard.next()) {
			KeyBinding.setKeyBindState(Keyboard.getEventKey(), Keyboard.getEventKeyState());
			if(Keyboard.getEventKeyState()) {
				KeyBinding.onTick(Keyboard.getEventKey());
			}
		}
	}
	
	private void onResize() {
		if(Display.wasResized()) {
			this.SCREEN_WIDTH = Display.getWidth();
			this.SCREEN_HEIGHT = Display.getHeight();
			
			if(this.guiScreen != null) {
				ScaledResolution scaledResolution = new ScaledResolution(this.SCREEN_WIDTH, this.SCREEN_HEIGHT);
				this.fontRender.setScaleFactor(scaledResolution.getScaleFactor());
				this.guiScreen.updateResolution(this, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight());
			}
			
			GL11.glViewport(0, 0, this.SCREEN_WIDTH, this.SCREEN_HEIGHT);
            GL11.glMatrixMode(GL11.GL_PROJECTION);
            GL11.glLoadIdentity();
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GL11.glLoadIdentity();
		}
	}

	public void displayGui(Gui gui) {
		if(this.guiScreen != null) {
			this.guiScreen.onGuiClosing();
		}
		this.guiScreen = (Gui) gui;
		if(this.guiScreen != null) {
			this.setIngameFocus(false);
			ScaledResolution scaledResolution = new ScaledResolution(this.SCREEN_WIDTH, this.SCREEN_HEIGHT);
			((Gui) gui).updateResolution(this, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight());
			((Gui) gui).guiOpened();
		}
	}
	
	public void setIngameFocus(boolean flag) {
		if(flag) {
			if(Display.isActive()) {
				if(!this.isMouseFocused) {
					this.isMouseFocused = true;
					Mouse.setGrabbed(true);
					this.displayGui((Gui) null);
				}
			}
		} else {
			if(this.isMouseFocused) {
				this.isMouseFocused = false;
				Mouse.setCursorPosition(Display.getWidth() / 2, Display.getHeight() / 2);
		        Mouse.setGrabbed(false);
			}
		}
	}
	
	public void shutdown() {
		this.isRunning = false;
	}
	
	public static long getSystemTime() {
        return Sys.getTime() * 1000L / Sys.getTimerResolution();
    }
	
	public void setSize(int width, int height) {
		this.SCREEN_WIDTH = width;
		this.SCREEN_HEIGHT = height;
	}
	
	public void setParent(Canvas canvas) {
		this.parent_canvas = canvas;
	}
	
	public static Cubix getCubix() {
		return Cubix.intance;
	}
	
}
