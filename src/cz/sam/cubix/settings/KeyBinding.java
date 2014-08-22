package cz.sam.cubix.settings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class KeyBinding {
	
	public static List<KeyBinding> keybindArray = new ArrayList<KeyBinding>();
	public static Map<Integer, KeyBinding> keyBindings = new HashMap<Integer, KeyBinding>();
	
	private String keyName;
    private int keyCode;
    
    private boolean pressed;
    private int pressTime;
    
    public KeyBinding(String keyName, int keyCode) {
    	this.keyName = keyName;
    	this.keyCode = keyCode;
    	keybindArray.add(this);
    	keyBindings.put(keyCode, this);
    }
    
    public boolean isPressed() {
        if(this.pressTime == 0) {
            return false;
        } else {
            --this.pressTime;
            return true;
        }
    }
    
    private void unpressKey() {
        this.pressTime = 0;
        this.pressed = false;
    }
    
    public static void onTick(int keyCode) {
        KeyBinding keybinding = keyBindings.get(keyCode);

        if(keybinding != null) {
            ++keybinding.pressTime;
        }
    }
    
    public static void setKeyBindState(int keyCode, boolean flag) {
        KeyBinding keybinding = keyBindings.get(keyCode);

        if(keybinding != null) {
            keybinding.pressed = flag;
        }
    }
    
    public static void unPressAllKeys() {
        Iterator<KeyBinding> iterator = keybindArray.iterator();

        while(iterator.hasNext()) {
            KeyBinding keybinding = (KeyBinding)iterator.next();
            keybinding.unpressKey();
        }
    }
    
    public static void resetKeyBindingArrayAndHash() {
    	keyBindings.clear();
        Iterator<KeyBinding> iterator = keybindArray.iterator();

        while(iterator.hasNext()) {
            KeyBinding keybinding = (KeyBinding)iterator.next();
            keyBindings.put(keybinding.keyCode, keybinding);
        }
    }
    
    public boolean isKeyPressed() {
    	return this.pressed;
    }
    
	public String getKeyName() {
		return keyName;
	}

	public void setKeyCode(int keyCode) {
		this.keyCode = keyCode;
	}
	
}
