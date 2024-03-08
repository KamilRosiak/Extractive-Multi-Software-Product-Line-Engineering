package org.apogames.sound;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import org.apogames.ApoConstants;

public class AudioPlayer implements Runnable {
	private static final int EXTERNAL_BUFFER_SIZE = 128000;

	private final boolean BInJAR = true;
	
	private String path;
	
	private boolean bStop;
	
	private boolean bLoop;
	
	private ApoSoundThread soundThread;
	
	public AudioPlayer(String path, boolean bLoop) {
		this.path = path;
		this.bLoop = bLoop;
		this.bStop = false;
	}

	public void start() {
		this.soundThread = new ApoSoundThread(this);
		this.soundThread.start();
		Thread t = new Thread(this);
		t.start();
	}
	
	public boolean isBStop() {
		return this.bStop;
	}

	public void setBStop(boolean stop) {
		this.bStop = stop;
	}

	public boolean isBLoop() {
		return this.bLoop;
	}

	public void setBLoop(boolean loop) {
		this.bLoop = loop;
	}

	public void run() {
		try {
			while(this.soundThread.isRunning()) {
				do {
					AudioInputStream audioInputStream = null;
					try {
						if (this.BInJAR) {
							audioInputStream = AudioSystem.getAudioInputStream(AudioFormat.Encoding.PCM_SIGNED, AudioSystem.getAudioInputStream(this.getClass().getResource(this.path)));
						} else {
							if (ApoConstants.B_APPLET) {
								audioInputStream = AudioSystem.getAudioInputStream(new URL("http://www.apo-games.de/apoPongBeat" + this.path));
							} else {
								audioInputStream = AudioSystem.getAudioInputStream(new File(System.getProperty("user.dir") + this.path.replace("/", File.separator)));	
							}
						}
					} catch (Exception e) {
						System.out.println("Fehler: "+e.getMessage()+" "+this.path+" "+e.getCause());
					}

					AudioFormat audioFormat = audioInputStream.getFormat();
				
					SourceDataLine line = null;
					DataLine.Info info = new DataLine.Info(SourceDataLine.class,audioFormat);
					try {
						line = (SourceDataLine) AudioSystem.getLine(info);
						line.open(audioFormat);
					} catch (LineUnavailableException e) {
					} catch (Exception e) {
					}

					line.start();

					int nBytesRead = 0;
					byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];
					while ((nBytesRead != -1) && (!this.isBStop())) {
						try {
							nBytesRead = audioInputStream.read(abData, 0, abData.length);
						} catch (IOException e) {
						}
						if (nBytesRead >= 0) {
							line.write(abData, 0, nBytesRead);
						}
					}
					line.drain();
					line.close();
				} while ((!this.isBStop()) && (this.isBLoop()));
				this.soundThread.stop();
			}
			try {
				/*synchronized(this.soundThread)*/ {
					this.soundThread.wait((long)10);
				}
			} catch(InterruptedException e) {
				(this.soundThread).stop();
			}
		} catch (Exception ex) {
			
		}
	}

}
