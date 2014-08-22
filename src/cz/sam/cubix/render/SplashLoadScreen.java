package cz.sam.cubix.render;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import cz.sam.cubix.Cubix;

public class SplashLoadScreen {
	
	private Cubix cubix;
	private int time;
	
	public SplashLoadScreen(Cubix cubix, int duration) {
		this.time = duration;
		this.cubix = cubix;
	}
	
	public void drawSplashScreen() {
		for(float fadeIn = 0f; fadeIn <= 1.0f; fadeIn += 0.00030f) {
        	if(fadeIn > 0.9f) {
        		int c = 0;
        		while(true) {
        			c++;
        			if(c > this.time * 20) break;
        			this.drawWhiteScreen();
                    GL11.glEnable(GL11.GL_TEXTURE_2D);
                	GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                    this.drawSplash();
        		}
        	} else {
        		this.drawWhiteScreen();
                GL11.glEnable(GL11.GL_TEXTURE_2D);
            	GL11.glColor4f(1.0f, 1.0f, 1.0f, fadeIn);
                this.drawSplash();
        	}
            Display.update();
        }
        
        /*for(float fadeIn = 1f; fadeIn > 0; fadeIn -= 0.00030f) {
        	this.drawWhiteScreen();
            GL11.glEnable(GL11.GL_TEXTURE_2D);
        	GL11.glColor4f(1.0f, 1.0f, 1.0f, fadeIn);
            this.drawSplash();
            Display.update();
        }*/
	}
	
	public void drawSplash() {
        float splash2_width = 800;
        float splash2_height = 500;
        float splash2_f1 = splash2_width / 2f;
        float splash2_f2 = splash2_height / 2f;
        GL11.glBegin(GL11.GL_QUADS); {
        	GL11.glTexCoord2f(0, 0);
        	GL11.glVertex3f(this.cubix.SCREEN_WIDTH / 2 - splash2_f1, this.cubix.SCREEN_HEIGHT / 2 - splash2_f2, 0);
        	
        	GL11.glTexCoord2f(0, 1);
        	GL11.glVertex3f(this.cubix.SCREEN_WIDTH / 2 - splash2_f1, this.cubix.SCREEN_HEIGHT / 2 + splash2_f2, 0);
        	
        	GL11.glTexCoord2f(1, 1);
        	GL11.glVertex3f(this.cubix.SCREEN_WIDTH / 2 + splash2_f1, this.cubix.SCREEN_HEIGHT / 2 + splash2_f2, 0);
        	
        	GL11.glTexCoord2f(1, 0);
        	GL11.glVertex3f(this.cubix.SCREEN_WIDTH / 2 + splash2_f1, this.cubix.SCREEN_HEIGHT / 2 - splash2_f2, 0);
        } GL11.glEnd();
	}
	
	public void drawWhiteScreen() {
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glBegin(GL11.GL_QUADS); {
        	GL11.glVertex3f(0, 0, 0);
        	GL11.glVertex3f(0, this.cubix.SCREEN_HEIGHT, 0);
        	GL11.glVertex3f(this.cubix.SCREEN_WIDTH, this.cubix.SCREEN_HEIGHT, 0);
        	GL11.glVertex3f(this.cubix.SCREEN_WIDTH, 0, 0);
        } GL11.glEnd();
	}
	
}
