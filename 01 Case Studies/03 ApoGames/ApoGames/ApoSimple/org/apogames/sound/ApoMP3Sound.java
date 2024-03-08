package org.apogames.sound;

//Ralph-Uwe RYll 2010 Version 1.0
import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.util.TimeZone;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class ApoMP3Sound implements Runnable
{
	private Thread runner = new Thread(this); //AbspielThread
	private static int BufferSize = 1024; // Anzahl der Daten, die aufeinmal an die Soundkarte geschickt werden.
    private static byte[]  buffer = new byte[BufferSize];
    private int gainPercent = 90;  //gibt die Lautstärke in Prozent an.  (0% = -80dB und 100% = 6dB) 
	private Boolean stop = false;
	private Boolean loopPlay = false;
	private URL song = null;
	private static long timeZoneKorrektur = TimeZone.getDefault().getOffset(0);
	private Time time = new Time(-timeZoneKorrektur);
	private Time songTime = new Time(-timeZoneKorrektur);
	private int sampleSizeInBits = 0;
	private long songLaenge = 0;
	private boolean reset = false;
	private Boolean isPlaying = false;
	private long resetKorrektur = 0;
	private boolean pause = false;
	private boolean mute = false;
	private int lautstaerke = gainPercent;

	/**
	 * damit mp3-Dateien abgespielt werden können, muss das mp3plugin von Sun im Classpath stehen oder
	 * (zB. unter Eclipse) als Bibliothek eingetragen sein.  
	 * Das gleiche gilt für flac-Dateien.
	 * 
	 * Die Lautstärke "gainLevel" ist logarhytmisch von -80dB bis ca. 6dB. Daher ist die 
	 * prozentuale Lautstärkeregelung nicht ganz korrekt. 
	 */
	public ApoMP3Sound() {
		
	}
	
	public ApoMP3Sound(String song) {
		this.setSong(song);
	}
	
	/**
	 * gibt die aktuelle Zeit an
	 */
	public Time getCurrentTime() {
		return time;
	}



	/**
	 * gibt zurück, ob das Musikstück wiederholt wird
	 */
	public boolean isLoopPlay(){
		return loopPlay;
	}
	
	/**
	 * startet eine Endlosschleife
	 *
	 */
	public void setLoopPlay(Boolean loop){
		loopPlay = loop;
	}
	
	/**
	 * stoppt die Wiedergabe
	 *
	 */
	public void stop()
	{
		stop=true;
	}
	
	/**
	 * starten der Wiedergabe
	 *
	 */
	public void play()
	{
			stop=false;
			if (!runner.isAlive())
			{
				runner = new Thread(this);
				runner.start();
			}
	}
	
	/**
	 * gibt die aktuelle Lautstärke zurück
	 */
	public int getVolume(){
		return gainPercent;
	}
	
	/**
	 * Wert zwischen 0% und 100%
	 * @param volumen
	 */
	public void setVolumen(int volumen){
		if ((volumen <= 100) || (volumen >= 0)){
			gainPercent = volumen;
		}
		
	}
	
	
	/**
	 * der WiedergabeThread
	 */
	public void run() 
	{
		do{
		try {
			resetKorrektur = 0;
			AudioInputStream in = AudioSystem.getAudioInputStream(AudioFormat.Encoding.PCM_SIGNED, AudioSystem.getAudioInputStream(song));
			AudioFormat audioFormat = in.getFormat();
			SourceDataLine line = (SourceDataLine) AudioSystem.getLine(new DataLine.Info(SourceDataLine.class, audioFormat));
		    line.open(audioFormat);
		    FloatControl gainControl = (FloatControl)line.getControl(FloatControl.Type.MASTER_GAIN);
		    line.start();
		    //songLaenge = song.length();
		    sampleSizeInBits = audioFormat.getSampleSizeInBits();	
		    
		    if (in.getFrameLength() == -1){
		    	songTime.setTime(songLaenge/sampleSizeInBits - timeZoneKorrektur);
		    }
		    else{
		    	songTime.setTime((in.getFrameLength()/(long)audioFormat.getFrameRate())*1000 - timeZoneKorrektur);
		    }
		    
		    in.mark(in.available());
		    while ((true)&&(!stop)){
		    	isPlaying = true;
		    	int gainLevel= (int) ((int)gainControl.getMinimum()+((gainControl.getMaximum()-gainControl.getMinimum())/100*gainPercent));
		    	gainControl.setValue(gainLevel);
		    	if (!pause){
			    	int n = in.read(buffer, 0, buffer.length);
			    	if ((n < 0)||(stop)) {
			        	break;
			        }
			    	if (reset) {
			    		resetKorrektur =  line.getMicrosecondPosition()/1000;
			    		in.reset();
			    		reset = false;
			    	}
			    	time.setTime(line.getMicrosecondPosition()/1000 - timeZoneKorrektur - resetKorrektur);
			    	line.write(buffer, 0, n);
		    	}
		      }
		      line.drain();
		      line.close();
		      in.close();
			} catch (UnsupportedAudioFileException e) {
			    System.out.println("nicht unterstütztes Format");
			} catch (IOException e) {
				System.out.println("Datei nicht gefunden" + e );
			} catch (LineUnavailableException e) {
				System.out.println("Soundkartenfehler");
			}
		}while (loopPlay && !stop);
		isPlaying = false;
	}
	
	/**
	 * Name und Pfad der Sounddatei
	 * @param song
	 */
	public void setSong(String song) {
		this.song = this.getClass().getResource(song);
		
	}

	/** 
	 * Name und Pfad der Sounddatei
	 * @return
	 */
	public URL getSong() {
		return song;
	}

	/**
	 * die Gesamtlänge des Musikstücks
	 * @return
	 */
	public Time getSongTime() {
		return songTime;
	}

	/**
	 * setzt den aktuellen Titel zurück
	 * @param reset
	 */

	public void reset(boolean reset) {
		this.reset = reset;
	}
	/**
	 * gibt zurück, ob gerade ein Titel abgespielt wird
	 * @return
	 */
	public Boolean isPlaying() {
		return isPlaying;
	}
	

	/**
	 * Titel pausieren
	 * @param pause
	 */
	public void setPause(boolean pause) {
		this.pause = pause;
	}


	/**
	 * gibt zurück, ob der Titel pausiert
	 * @return
	 */
	public boolean isPause() {
		return pause;
	}


	/**
	 * schaltet die Wiedergabe stumm
	 * @param mute
	 */
	public void setMute(boolean mute) {
		if ((mute) &&(!this.mute)){
			lautstaerke = this.getVolume();
			this.setVolumen(0);
		}else{
			this.setVolumen(lautstaerke);
		}
		this.mute = mute;
	}


	/**
	 * gibt zurück, ob die Wiedergabe stumm geschaltet ist
	 * @return
	 */
	public boolean isMute() {
		return mute;
	}

	


}