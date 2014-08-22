package cz.sam.cubix.util;

import org.lwjgl.Sys;

public class FPSHelper {
	
	private long lastFrame;
	private int fps;
	private long lastFPS;
	private int realFPS;
	
	public long getTime() {
	    return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	public int getDelta() {
	    long time = getTime();
	    int delta = (int) (time - lastFrame);
	    lastFrame = time;
	 
	    return delta;
	}
	
	public void init() {
	    lastFPS = getTime();
	}
	
	public void updateFPS() {
	    if (getTime() - lastFPS > 1000) {
	    	realFPS = fps;
			fps = 0;
			lastFPS += 1000;
	    }
	    fps++;
	}
	
	public long getFPS() {
		return realFPS;
	}
	
}
