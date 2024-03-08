package org.apogames.sound;

import java.util.Hashtable;

public class ApoSounds {

	private Hashtable<String, String> sounds;

	
	
	public ApoSounds() {
		this.sounds = new Hashtable<String, String>();
	}

	public void loadSound(String name, String path) {
		if (this.sounds.containsKey(name)) {
			return;
		}
		this.sounds.put(name, path);
	}

	public void playSound(String name) {
		this.playSound(name, false);
	}
	
	public void playSound(String name, boolean bLoop) {
		AudioPlayer player = new AudioPlayer(this.sounds.get(name), bLoop);
		player.start();
	}

}