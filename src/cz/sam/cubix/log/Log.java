package cz.sam.cubix.log;

public class Log {
	
	public static void info(Object message) {
		System.out.println("INFO: " + message);
	}
	
	public static void exception(String message, Throwable ex) {
		System.out.println("WARNING: " + message);
		ex.printStackTrace();
	}
	
	public static void error(String message) {
		System.out.println("ERROR: " + message);
		System.exit(1);
	}
	
}
