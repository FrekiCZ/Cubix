package cz.sam.cubix.util;

public class MathHelper {
	
	public static int ceiling_double_int(double d) {
        int i = (int)d;
        return d > (double)i ? i + 1 : i;
    }
	
	public static long floor_double_long(double par0) {
		long i = (long)par0;
        return par0 < (double)i ? i - 1L : i;
	}
	
	public static final float sqrt_float(float par0) {
        return (float)Math.sqrt((double)par0);
    }
	
}
