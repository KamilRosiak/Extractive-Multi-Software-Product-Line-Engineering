package org.apogames.speech;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import apoSoccer.ApoSoccerConstants;
import java.util.ArrayList;

public class ApoSpeech implements Runnable {

	private VoiceManager vm = null;
	private Voice voice = null;
	private Voice[] voices;
	private String speakText;
	private boolean bSpeak;
	
	/**
	 * Standard-Konstruktor
	 */
	public ApoSpeech() {
		try {
			this.init();
		} catch (Exception e) {
			//e.printStackTrace();
			//System.exit(1);
			return;
		}
	}

	/**
	 * Konstruktor zum sofortigen Vorbereiten der Texte
	 */
	public ApoSpeech(String[] texts) {
		try {
			this.init();
			this.prepareTexts(texts);
		} catch (Exception e) {
			//e.printStackTrace();
			//System.exit(1);
			return;
		}
	}

	/**
	 * Initialisiert den VoiceManager und das Stimmobjekt.
	 * Derzeit noch inklusive einiger Testroutinen, die später entfernt werden.
	 * @throws Exception
	 */
	private void init() {
		if (!ApoSoccerConstants.B_MUSIC) {
			return;
		}
		this.vm = VoiceManager.getInstance();
		voices = vm.getVoices();
        if(voices.length > 0) {
    		/*for(Voice v:voices) {
    			//System.out.println(v.getName()+": "+v.getDomain());
    		}*/
        } else {
        	//throw new Exception("No voices found!");
        	return;
        }
    	//selectVoice(listVoiceNames()[0]);
        //System.out.println(voice.getName());
        java.util.Arrays.toString(listVoiceNames());
        selectVoice("kevin16");
        this.bSpeak = false;
        voice.allocate();
        /**String[] ts = {"This is a long long long long long long long test.", "Just a test.", "Another test."};
        prepareTexts(ts);
        long start = System.currentTimeMillis();
        speak(ts[1]);
        speak(ts[0]);
        speak(ts[2]);
        System.out.println("Dauer: "+(System.currentTimeMillis()-start));*/
	}
	
	/**
	 * Setzt den Stimmsynthesizer so, dass eine gegebene Stimme ausgewählt wird.
	 * 
	 * @param v	Eine fertige Stimme, die der Domain 'general' angehören soll
	 * @return true bei Erfolg, false sonst
	 */
	public boolean selectVoice(Voice v) {
		if (v.getDomain().compareTo("general") != 0) {
			return false;
		}
		this.voice = v;
		this.voice.allocate();
		//System.out.println(v.getName()+" selected as active voice.");
		return true;
	}
	
	/**
	 * Setzt den Stimmsynthesizer so, dass eine bestimmte Stimme ausgewählt wird.
	 * Derzeit sind hier nur 'kevin' und 'kevin16' vorhanden.
	 * @param vName	Name der Stimme
	 * @return		true, wenn Auswahl der Stimme erfolgreich war, false sonst
	 */
	public boolean selectVoice(String vName) {
		//System.out.println("Trying to select '"+vName+"' as active voice...");
		for(Voice v:this.voices) {
			if(v.getName()==vName  && v.getDomain().compareTo("general") == 0) {
				this.voice = v;
				this.voice.allocate();
				//System.out.println(vName+" selected as active voice.");
				return true;
			}
		}
		//System.out.println("... failed!");
		return false;
	}

	/**
	 * Listet alle verfügbaren Stimmen der "general"-Domain auf. Andere Domänen können in 
	 * der Regel nur bestimmte Satzkonstrukte synthetisieren. 
	 * 
	 * @return Array der verfügbaren Stimmnamen.
	 */
	public String[] listVoiceNames() {
		ArrayList<String> out = new ArrayList<String>();
		for(int i=0;i<this.voices.length;i++) {
			if (this.voices[i].getDomain().compareTo("general")==0) {
				out.add(this.voices[i].getName());
			}
		}
		String[] o = new String[out.size()];
		for(int i=0;i<out.size();i++) {
			o[i] = out.get(i);
		}
		return o;
	}
	
	/**
	 * 
	 * @param text	String, der durch den Sprachsynthesizer ausgegeben werden soll.
	 * @return
	 */
	public boolean speak(String text) {
		if ((text == null) || (text.length() <= 0)) {
			return false;
		} else if (text.length() > ApoSoccerConstants.MAX_SPEECH_STRING_LENGTH) {
			//System.out.println("Text '"+text+"' is too long. Truncating after "+ApoSoccerConstants.MAX_SPEECH_STRING_LENGTH+" characters");
			text = text.substring(0, ApoSoccerConstants.MAX_SPEECH_STRING_LENGTH);
		}
		this.speakText = text;
		Thread t = new Thread(this);
		t.start();
		return true;
	}
	
	
	/**
	 * Diese Methode bereitet die Strings vor, indem sie einmal mit Lautstärke 0 gesprochen werden.
	 * Evtl. in späterer Version hier Audio-Dateien erstellen und im Speicher halten.
	 * Derzeitige Zeitersparnis ist vorhanden und bewegt sich im Bereich von 1-10% der Ausführungszeit.
	 *  
	 * @param texts	Array mit den Texten, die die KI sprechen darf.
	 */
	public void prepareTexts(String[] texts) {
		/**float volume = this.voice.getVolume();
		this.voice.setVolume(0);
		for(String t:texts) {
			if (t.length() > ApoSoccerConstants.MAX_SPEECH_STRING_LENGTH) {
				System.out.println("Text '"+t+"' is too long. Truncating after "+ApoSoccerConstants.MAX_SPEECH_STRING_LENGTH+" characters");
				t = t.substring(0, ApoSoccerConstants.MAX_SPEECH_STRING_LENGTH);
			}
			this.voice.speak(t);
		}
		this.voice.setVolume(volume);*/
	}
	
	public float getPitch() {
		return this.voice.getPitch();
	}
	
	public float getVolume() {
		return this.voice.getVolume();
	}
	
	public float getStretch() {
		return this.voice.getDurationStretch();
	}
	
	public void setPitch(float hertz) {
		this.voice.setPitch(hertz);
	}

	public void setVolume(float volume) {
		this.voice.setVolume(volume);
	}

	public void setStretch(float stretch) {
		this.voice.setDurationStretch(stretch);
	}

	public boolean isBSpeak() {
		return this.bSpeak;
	}
	
	public void run() {
		this.bSpeak = true;
		if (this.voice != null) {
			this.voice.speak(this.speakText);
		}
		this.bSpeak = false;
	}
}
