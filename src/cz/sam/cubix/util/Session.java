package cz.sam.cubix.util;

import java.util.Random;

public class Session {
	
	private String username;
	private String uuid;
	
	public Session(String username, String uuid) {
		if(username != null) {
			this.username = username;
		} else {
			this.username = "Player" + new Random().nextInt(1000);
		}
		
		if(uuid != null) {
			this.uuid = uuid;
		} else {
			this.uuid = "0";
		}
	}

	public String getUsername() {
		return username;
	}

	public String getUuid() {
		return uuid;
	}
	
}
