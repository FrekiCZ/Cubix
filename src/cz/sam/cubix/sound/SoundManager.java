package cz.sam.cubix.sound;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import cz.sam.cubix.settings.GameSettings;
import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.SoundSystemException;
import paulscode.sound.codecs.CodecJOrbis;
import paulscode.sound.codecs.CodecWav;
import paulscode.sound.libraries.LibraryLWJGLOpenAL;

public class SoundManager {
	
	private SoundSystem soundSystem;
	private GameSettings gameOptions;
	private File soundFolder;
	private int latestSoundID;
	private Map<String, File> soundMap = new HashMap<String, File>();
	private Map<String, File> musicMap = new HashMap<String, File>();
	private List<String> playingSounds = new ArrayList<String>();
	private List<String> playingMusics = new ArrayList<String>();
	private boolean loaded;
	
	public SoundManager(GameSettings gameOptions, File soundFolder) {
		this.gameOptions = gameOptions;
		this.soundFolder = soundFolder;
		
		try {
	        SoundSystemConfig.addLibrary(LibraryLWJGLOpenAL.class);
	        SoundSystemConfig.setCodec("ogg", CodecJOrbis.class);
	        SoundSystemConfig.setCodec("wav", CodecWav.class);
	    } catch (SoundSystemException ex) {
	        ex.printStackTrace();
	        System.err.println("error linking with the LibraryJavaSound plug-in");
	    }
		
		this.soundSystem = new SoundSystem();
		this.loaded = true;
		
		this.loadSounds();
	}

	private void loadSounds() {
		if(this.soundFolder.isDirectory()) {
			Collection<File> collection = FileUtils.listFiles(this.soundFolder, new String[] { "ogg" }, true);
			Iterator<File> iterator = collection.iterator();
			while(iterator.hasNext()) {
				this.loadSoundFile(iterator.next());
			}
		}
	}

	private void loadSoundFile(File file) {
		String s = this.soundFolder.toURI().relativize(file.toURI()).getPath();
		int i = s.indexOf("/");
		if(i != -1) {
			String folderName = s.substring(0, i);
            s = s.substring(i + 1);
            if(folderName.equalsIgnoreCase("music")) {
            	//System.out.println("Loading music: " + file.getName());
            	this.musicMap.put(file.getName(), file);
            	
            } else if(folderName.equalsIgnoreCase("sound")) {
            	//System.out.println("Loading sounds: " + file.getName());
            	this.soundMap.put(file.getName(), file);
            }
		}
	}
	
	public void playMusic(String soundName, String fileName) {
		if(!this.loaded) return;
		if(!this.soundSystem.playing(soundName)) {
			if(this.musicMap.containsKey(fileName)) {
				File soundFile = this.musicMap.get(fileName);
				URL soundURL = null;
				try {
					soundURL = soundFile.toURI().toURL();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				this.soundSystem.backgroundMusic(soundName, soundURL, soundFile.getAbsolutePath(), true);
				this.soundSystem.setVolume(soundName, this.gameOptions.musicVolume);
                this.soundSystem.play(soundName);
                this.playingMusics.add(soundName);
			} else {
				try {
					throw new Exception("Music not exist ! (" + fileName + ")");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void setVolume(String soundName, float volume) {
		if(this.soundSystem.playing(soundName)) {
			this.soundSystem.setVolume(soundName, volume);
		}
	}
	
	public void setMusicVolume(float volume) {
		for(String soundName : this.playingMusics) {
			if(this.soundSystem.playing(soundName)) {
				this.soundSystem.setVolume(soundName, volume);
			}
		}
	}
	
	public void setSoundsVolume(float volume) {
		for(String soundName : this.playingSounds) {
			if(this.soundSystem.playing(soundName)) {
				this.soundSystem.setVolume(soundName, volume);
			}
		}
	}
	
	public void playSoundFX(String fileName, float volume, float pitch) {
		if(!this.loaded) return;
		if(this.soundMap.containsKey(fileName)) {
			File soundFile = this.soundMap.get(fileName);
			URL soundURL = null;
			try {
				soundURL = soundFile.toURI().toURL();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			
			this.latestSoundID = (this.latestSoundID + 1) % 256;
			String soundID = "sound_" + this.latestSoundID;
			
			this.soundSystem.newSource(false, soundID, soundURL, soundFile.getAbsolutePath(), false, 0.0F, 0.0F, 0.0F, 0, 0.0F);
			
			if(volume > 1.0F) {
				volume = 1.0F;
            }
			volume *= 0.25F;
			this.soundSystem.setPitch(soundID, pitch);
            this.soundSystem.setVolume(soundID, volume * this.gameOptions.soundVolume);
            this.soundSystem.play(soundID);
		} else {
			try {
				throw new Exception("Music not exist ! (" + fileName + ")");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void playStreaming(String par1Str, float par2, float par3, float par4) {
		
	}
	
	public void cleanup() {
		this.soundSystem.cleanup();
    }
	
}
