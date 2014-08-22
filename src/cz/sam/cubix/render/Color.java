package cz.sam.cubix.render;

import org.lwjgl.opengl.GL11;

public class Color {
	
	public static void setColor(int red, int green, int blue) {
		GL11.glColor4f(red / 255.0F, green / 255.0F, blue / 255.0F, 1);
	}
	
	public static void setColor(int red, int green, int blue, float alpha) {
		GL11.glColor4f(red / 255.0F, green / 255.0F, blue / 255.0F, alpha);
	}
	
}
