package cz.sam.cubix.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FileLocation {
	
	private static Map<String, String> locations = new HashMap<String, String>();
	private static String data_folder;
	
	public static void setDataFolder(String folder) {
		data_folder = folder;
	}
	
	public static String getDataFolder() {
		return data_folder;
	}
	
	public static void addLocation(String name, String path) {
		if(data_folder == null) {
			try {
				throw new Exception("FileLocationException: Data folder not set !");
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		
		if(locations.containsKey(name)) {
			try {
				throw new Exception("FileLocationException: This location is already exist !");
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		
		locations.put(name, data_folder + "/" + path + "/");
	}
	
	public static String getLocation(String name) {
		if(locations.containsKey(name)) {
			return locations.get(name);
		} else {
			try {
				throw new Exception("FileLocationException: This location not exist !");
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		return null;
	}
	
	public static File getFileLocation(String name) {
		if(locations.containsKey(name)) {
			return new File(locations.get(name));
		} else {
			try {
				throw new Exception("FileLocationException: This location not exist !");
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		return null;
	}
	
	public static String getResourceLocation(String name, String file) {
		if(locations.containsKey(name)) {
			return locations.get(name) + file;
		} else {
			try {
				throw new Exception("FileLocationException: This location not exist !");
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		return null;
	}
	
	public static File getFileResourceLocation(String name, String file) {
		if(locations.containsKey(name)) {
			return new File(locations.get(name) + file);
		} else {
			try {
				throw new Exception("FileLocationException: This location not exist !");
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		return null;
	}
	
}
