package cz.sam.cubix.util;

public class OSUtil {
	
	public static EnumOS getOSType() {
		String os = System.getProperty("os.name").toLowerCase();
		if(os.indexOf("win") >= 0) {
			return EnumOS.WINDOWS;
		} else if(os.indexOf("mac") >= 0) {
			return EnumOS.MAC;
		} else if((os.indexOf("nix") >= 0) || (os.indexOf("nux") >= 0) || (os.indexOf("aix") > 0)) {
			return EnumOS.LINUX;
		} else if(os.indexOf("sunos") >= 0) {
			return EnumOS.SOLARIS;
		} else {
			return EnumOS.NOT_SUPPORTED;
		}
	}
	
}
