package org.apogames.sound;

import java.util.ArrayList;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;

public class ApoMidi {

	private ArrayList<Sequence> songs = new ArrayList<Sequence>();
	private Sequencer sequencer;
	
	public void addSong(String s) {
		try {
			if (this.sequencer == null) {
				this.sequencer = MidiSystem.getSequencer();
				this.sequencer.open();
			}
			this.songs.add(MidiSystem.getSequence(ApoMidi.class.getResourceAsStream(s)));
		} catch (Exception ex) {
			this.sequencer = null;
			this.songs.clear();
			System.out.println("Ex mit "+ex.getMessage());
		}
	}

	public void startMusic(int song) {
		this.stopMusic();
		if ((song < 0) || (song > this.songs.size()) || (this.songs.size() <= 0)) {
			return;
		}
		if (this.sequencer != null) {
			try {
				this.sequencer.open();
				this.sequencer.setSequence((Sequence) null);
				this.sequencer.setSequence(this.songs.get(song));
				this.sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
				this.sequencer.start();
			} catch (Exception e) {
			}
		}
	}

	public void stopMusic() {
		if (this.sequencer != null) {
			try {
				this.sequencer.stop();
				this.sequencer.close();
			} catch (Exception e) {
			}
		}
	}

	public void setVolume(int volume) {
		try {
			if (this.sequencer instanceof Synthesizer) {
				MidiChannel[] channels = ((Synthesizer)(this.sequencer)).getChannels();
				double gain = (float) volume / 100f;
				for (int i = 0; i < channels.length; i++) {
					channels[i].controlChange(7, (int) (gain * 127.0));
				}
				System.out.println("Check");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
