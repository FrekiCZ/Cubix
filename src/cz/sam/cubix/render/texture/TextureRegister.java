package cz.sam.cubix.render.texture;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import cz.sam.cubix.Cubix;
import cz.sam.cubix.util.FileLocation;

public class TextureRegister implements IconRegister {
	
	private Cubix cubix;
	
	public TextureRegister(Cubix cubix) {
		this.cubix = cubix;
	}
	
	@Override
	public String registerIcon(String s)  {
		this.cubix.textureManager.loadTexture(s, FileLocation.getResourceLocation("blocks", s));
		//Log.info("Cubix: Texture " + s + " registred.");
		return s;
	}
	
	public void registerAllTextures() {
		Collection<File> collection = FileUtils.listFiles(FileLocation.getFileLocation("textures"), new String[] { "png" }, true);
		Iterator<File> iterator = collection.iterator();
		while(iterator.hasNext()) {
			this.registerTexture(iterator.next());
		}
	}

	private void registerTexture(File file) {
		this.cubix.textureManager.loadSmoothTexture(FilenameUtils.removeExtension(file.getName()), file.getAbsolutePath());
	}
	
}
