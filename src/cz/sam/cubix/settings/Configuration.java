package cz.sam.cubix.settings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import cz.sam.cubix.util.FileUtil;

public class Configuration {
	
	private File configFile;
	private Map<String, String> configMap = new HashMap<String, String>();
	
	public Configuration(File configFile) {
		this.configFile = configFile;
		try {
			this.initConfig();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initConfig() throws Exception {
		if(this.configFile != null) {
    		if(!this.configFile.exists()) {
        		try {
    				this.configFile.createNewFile();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    	}
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.configFile)));
		
		String line = "";
		while((line = reader.readLine()) != null) {
			if(!line.isEmpty() && line.contains("=") && !line.startsWith("#")) {
				String[] ex = line.split("=");
				if(!ex[0].isEmpty() && !ex[1].isEmpty()) {
					this.configMap.put(ex[0], ex[1]);
				}
			}
		}
		
		reader.close();
	}
	
	public void saveConfig() {
		String nl = System.getProperty("line.separator");
		String config = "";
		int config_count = this.configMap.size() - 1;
		int count = -1;
		for(Entry<String, String> e : this.configMap.entrySet()) {
			count++;
			config += e.getKey() + "=" + e.getValue();
			if(count != config_count) {
				config += nl;
			}
		}
		FileUtil.writeToFile(this.configFile.getAbsolutePath(), config);
	}
	
	public String getString(String name) {
		if(this.configMap.containsKey(name)) {
			return this.configMap.get(name);
		}
		return null;
	}
	
	public boolean getBoolean(String name) {
		if(this.configMap.containsKey(name)) {
			return Boolean.valueOf(this.configMap.get(name));
		}
		return false;
	}
	
	public int getInt(String name) {
		if(this.configMap.containsKey(name)) {
			return Integer.valueOf(this.configMap.get(name));
		}
		return 0;
	}
	
	public float getFloat(String name) {
		if(this.configMap.containsKey(name)) {
			return Float.valueOf(this.configMap.get(name));
		}
		return 0F;
	}
	
	public double getDouble(String name) {
		if(this.configMap.containsKey(name)) {
			return Double.valueOf(this.configMap.get(name));
		}
		return 0D;
	}
	
	public void setDefault(String name, Object value) {
		if(this.isObjectCompabilite(value)) {
			if(!this.configMap.containsKey(name)) {
				this.set(name, value);
			}
		}
	}
	
	public void set(String name, Object value) {
		if(this.isObjectCompabilite(value)) {
			if(this.configMap.containsKey(name)) {
				this.configMap.remove(name);
			}
			
			this.configMap.put(name, String.valueOf(value));
		} else {
			try {
				throw new Exception("Configuration: Value is not supported !");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private boolean isObjectCompabilite(Object value) {
		if(
				value instanceof String || 
				value instanceof Integer ||
				value instanceof Float ||
				value instanceof Double ||
				value instanceof Boolean
		) {
			return true;
		}
		return false;
	}
	
}
