package org.apogames.sound;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;
import java.util.Hashtable;
import java.util.Vector;

public class ApoSounds {

	private Hashtable<String, AudioClip> sounds;

	private Vector<AudioClip> loopingClips;

	public ApoSounds() {
		this.sounds = new Hashtable<String, AudioClip>();
		this.loopingClips = new Vector<AudioClip>();
	}

	public void loadSound(String name, String path) {

		if (this.sounds.containsKey(name)) {
			return;
		}
		URL sound_url = getClass().getClassLoader().getResource(path);
		this.sounds.put(name, (AudioClip) Applet.newAudioClip(sound_url));
	}

	public void playSound(String name) {
		AudioClip audio = this.sounds.get(name);
		audio.play();
	}

	public void loopSound(String name) {
		AudioClip audio = this.sounds.get(name);
		this.loopingClips.add(audio);
		audio.loop();
	}

	public void stopLoopingSound() {
		for (AudioClip c : this.loopingClips) {
			c.stop();
		}
	}

}