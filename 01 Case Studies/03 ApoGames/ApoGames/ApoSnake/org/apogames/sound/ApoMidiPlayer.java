package org.apogames.sound;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Transmitter;

public class ApoMidiPlayer {

	// Midi meta event
	public static final int END_OF_TRACK_MESSAGE = 47;

	private Sequencer sequencer;
	private Synthesizer synthesizer;

	private boolean paused;

	/**
	 * Creates a new MidiPlayer object.
	 */
	public ApoMidiPlayer() {
		try {
			MidiDevice.Info[] info = MidiSystem.getMidiDeviceInfo();
			for (int i = 0; i < info.length; i++) {
				if (info[i].getName().indexOf("Sequence") != -1) {
					this.sequencer = (Sequencer) MidiSystem.getMidiDevice(info[i]);
				} else if (info[i].getName().indexOf("Synthe") != -1) {
					this.synthesizer = (Synthesizer) MidiSystem.getMidiDevice(info[i]);
				}
			}
			if (this.sequencer != null) {
				this.sequencer.open();
			}
			if (this.synthesizer != null) {
				this.synthesizer.open();
			}
			if ((this.sequencer != null) && (this.synthesizer != null)) {
				Receiver synthReceiver = synthesizer.getReceiver();
				Transmitter seqTransmitter = sequencer.getTransmitter();
				seqTransmitter.setReceiver(synthReceiver);
			}
		} catch (MidiUnavailableException ex) {
			this.sequencer = null;
			this.synthesizer = null;
		}
	}

	/**
	 * Loads a sequence from the file system. Returns null if an error occurs.
	 */
	public Sequence getSequence(String filename) {
		try {
			return getSequence(new FileInputStream(filename));
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * Loads a sequence from an input stream. Returns null if an error occurs.
	 */
	public Sequence getSequence(InputStream is) {
		try {
			if (!is.markSupported()) {
				is = new BufferedInputStream(is);
			}
			Sequence s = MidiSystem.getSequence(is);
			is.close();
			return s;
		} catch (InvalidMidiDataException ex) {
			ex.printStackTrace();
			return null;
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * Plays a sequence, optionally looping. This method returns immediately.
	 * The sequence is not played if it is invalid.
	 */
	public void play(Sequence sequence, boolean loop) {
		if ((this.sequencer != null) && (sequence != null) && (this.sequencer.isOpen())) {
			try {
				this.sequencer.setSequence(sequence);
				this.sequencer.start();
			} catch (InvalidMidiDataException ex) {
				System.out.println(ex.getMessage());
			}
		}
	}

	/**
	 * Stops the sequencer and resets its position to 0.
	 */
	public void stop() {
		if ((this.sequencer != null) && (this.sequencer.isOpen())) {
			this.sequencer.stop();
			this.sequencer.setMicrosecondPosition(0);
		}
	}

	/**
	 * Closes the sequencer.
	 */
	public void close() {
		if ((this.sequencer != null) && (this.sequencer.isOpen())) {
			this.sequencer.close();
		}
	}

	/**
	 * Gets the sequencer.
	 */
	public Sequencer getSequencer() {
		return this.sequencer;
	}

	/**
	 * Sets the paused state. Music may not immediately pause.
	 */
	public void setPaused(boolean paused) {
		if ((this.paused != paused) && (this.sequencer != null) && (this.sequencer.isOpen())) {
			this.paused = paused;
			if (paused) {
				this.sequencer.stop();
			} else {
				this.sequencer.start();
			}
		}
	}

	/**
	 * Returns the paused state.
	 */
	public boolean isPaused() {
		return this.paused;
	}

	/**
	 * set Volume off the midi
	 * Must be between 0 and 100
	 * @param volume : between 0 and 100
	 */
	public void setVolume(int volume) {
		try {
			MidiChannel[] channels = this.synthesizer.getChannels();
			double gain = (float) volume / 100f;
			for (int i = 0; i < channels.length; i++) {
				channels[i].controlChange(7, (int) (gain * 127.0));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}