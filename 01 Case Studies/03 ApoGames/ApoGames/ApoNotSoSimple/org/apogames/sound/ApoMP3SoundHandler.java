package org.apogames.sound;

import java.util.Hashtable;

public class ApoMP3SoundHandler {

	private Hashtable<String, ApoMP3Sound> sounds;
	
	
	
	public ApoMP3SoundHandler() {
		this.sounds = new Hashtable<String, ApoMP3Sound>();
	}
	
	public void loadSound(String name, String path) {
		if (this.sounds.containsKey(name)) {
			return;
		}
		this.sounds.put(name, new ApoMP3Sound(path));
	}

	public void playSound(String name, boolean bLoop, int volumen) {
		if (!this.sounds.containsKey(name)) {
			return;
		}
		this.sounds.get(name).setLoopPlay(bLoop);
		if (volumen >= 0) {
			this.sounds.get(name).setVolumen(volumen);
		}
		this.sounds.get(name).play();

	}
	
	public void stopSound(String name) {
		if (!this.sounds.containsKey(name)) {
			return;
		}
		this.sounds.get(name).stop();
	}
	
	public void setLoop(String name, boolean bLoop) {
		if (!this.sounds.containsKey(name)) {
			return;
		}
		this.sounds.get(name).setLoopPlay(bLoop);
	}
	
	public void setVolumen(String name, int volumen) {
		if (!this.sounds.containsKey(name)) {
			return;
		}
		this.sounds.get(name).setVolumen(volumen);
	}
	
	public void stopAll() {
		for (String name : this.sounds.keySet()) {
			this.stopSound(name);
		}
	}
}
