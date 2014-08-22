package cz.sam.cubix.util;

public class ColorUtil {
	
	public static int getColor(int r, int g, int b) {
		int color = r << 16 | g << 8 | b;
		return color;
	}
	
	public static int getIntColorRGBA(int r, int g, int b, int alpha) {
		int color = r << 32 | g << 16 | b << 8 | alpha;
		return color;
	}
	
}
