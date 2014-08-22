import java.io.File;
import java.net.URL;

import javax.swing.JOptionPane;

import cz.sam.cubix.Cubix;
import cz.sam.cubix.util.EnumOS;
import cz.sam.cubix.util.FileLocation;
import cz.sam.cubix.util.OSUtil;
import cz.sam.cubix.util.Session;


public class Main {

	public static void main(String[] args) {
		Main main = new Main();
		main.run(args);
	}
	
	public void run(String[] args) {
		int usernameIndex = -1;
		int gameDirIndex = -1;
		int uuidIndex = -1;
		
		for(int i = 0; i < args.length; i++)  {
			if(args[i].equals("--username")) {
				usernameIndex = i + 1;
			} else if(args[i].equals("--gameDir")) {
				gameDirIndex = i + 1;
			} else if(args[i].equals("--uuid")) {
				uuidIndex = i + 1;
			}
		}
		
		String username = null;
		String uuid = null;
		
		if(usernameIndex != -1) {
			username = args[usernameIndex];
		}
		
		if(uuidIndex != -1) {
			uuid = args[uuidIndex];
		}
		
		if(gameDirIndex != -1) {
			FileLocation.setDataFolder(args[gameDirIndex]);
		} else {
			FileLocation.setDataFolder("Cubix");
		}
		
		Session session = new Session(username, uuid);
		loadNatives();
		Cubix cubix = new Cubix(session, false);
		try {
			cubix.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void loadNatives() {
		EnumOS ostype = OSUtil.getOSType();
		String native_folder = "";
		if(ostype == EnumOS.WINDOWS) {
			native_folder = "windows";
		} else if(ostype == EnumOS.MAC) {
			native_folder = "macosx";
		} else if(ostype == EnumOS.LINUX) {
			native_folder = "linux";
		} else if(ostype == EnumOS.SOLARIS) {
			native_folder = "solaris";
		} else if(ostype == EnumOS.NOT_SUPPORTED) {
			JOptionPane.showMessageDialog(null, "Cubix - váš systém není podporován !");
			System.exit(1);
		}
		
		String jarDir = System.getProperty("user.dir");
		System.setProperty("org.lwjgl.librarypath", jarDir + File.separator + "Cubix" + File.separator + "natives" + File.separator + native_folder);
		System.out.println("INFO: Natives load.");
	}
	
	public URL getFile(String path) {
		return this.getClass().getResource(path);
	}

}
