package org.apogames;

/**
 * Klasse, die sich um den Timer kümmert und somit dass es flüssig alles in
 * einer bestimmten Reihenfolge abläuft
 * 
 * @author Dirk Aporius
 * 
 */
public class ApoThread implements Runnable {
	private final long FPSUpdater = 1000000000;
	
	private ApoTimerInterface game;

	private boolean bRunning;
	private boolean bThink;
	private boolean bRender;

	private long waitThink;
	private long waitRender;

	private long delta;
	private long last;
	
	private long fps;
	
	private long deltaThink;
	private long deltaRender;
	private long deltaSecond;
	
	private long fpsCounter;

	/**
	 * Konstruktor
	 * @param game = InterfaceObejkt, um mit der Component zu kommunizieren
	 * @param wait = wieviel Millisekunden soll gewartet werden bis wieder nachgedacht und gezeichnet werden soll
	 */
	public ApoThread(ApoTimerInterface game, long wait) {
		this( game, wait, wait );
	}
	
	/**
	 * Konstruktor
	 * @param game = InterfaceObejkt, um mit der Component zu kommunizieren
	 * @param waitThink = wieviel Millisekunden soll gewartet werden bis wieder nachgedacht werden soll
	 * @param waitRender = wieviel Millisekunden soll gewartet werden bis wieder gezeichnet werden soll
	 */
	public ApoThread(ApoTimerInterface game, long waitThink, long waitRender ) {
		this(game, waitThink, waitRender, true, true);
	}

	/**
	 * Konstruktor
	 * @param game = InterfaceObejkt, um mit der Component zu kommunizieren
	 * @param waitThink = wieviel Millisekunden soll gewartet werden bis wieder nachgedacht werden soll
	 * @param waitRender = wieviel Millisekunden soll gewartet werden bis wieder gezeichnet werden soll
	 * @param bThink = boolean Variable, die angibt, ob gedacht werden soll oder nicht
	 * @param bRender = boolean Variable, die angibt, ob gemalt werden soll oder nicht
	 */
	public ApoThread(ApoTimerInterface game, long waitThink, long waitRender, boolean bThink, boolean bRender) {
		super();

		this.game = game;
		this.waitThink = waitThink * 1000000;
		this.waitRender = waitRender * 1000000;

		this.bRunning = false;
		
		this.setBThink(bThink);
		this.setBRender(bRender);
	}
	
	/**
	 * gibt zurück, ob gedacht werden soll
	 * @return TRUE, es soll gedacht werden, sonst FALSE
	 */
	public boolean isBThink() {
		return this.bThink;
	}

	/**
	 * setzt den Wert, ob gedacht werden soll, auf den übergebenen
	 * @param bThink : TRUE, es soll gedacht werden, sonst FALSE
	 */
	public void setBThink(boolean bThink) {
		this.bThink = bThink;
	}

	/**
	 * gibt zurück, ob gemalt werden soll
	 * @return TRUE, es soll gemalt werden, sonst FALSE
	 */
	public boolean isBRender() {
		return bRender;
	}

	/**
	 * setzt den Wert, ob gedacht werden soll, auf den übergebenen
	 * @param bRender : TRUE, es soll gedacht werden, sonst FALSE
	 */
	public void setBRender(boolean bRender) {
		this.bRender = bRender;
	}
	
	/**
	 * gibt die Anzahl der Frames zurück
	 * @return gibt die Anzahl der Frames zurück
	 */
	public long getFps() {
		return this.fps;
	}

	/**
	 * gibt die Zeit zurück, die der Timer immer wartet um zu denken
	 * 
	 * @return gibt die Zeit zurück, die der Timer immer wartet
	 */
	public long getWait() {
		return this.waitThink;
	}

	/**
	 * setzt die Zeit, die der Timer immer wartet um zu denken, auf den übergebenen Wert
	 * 
	 * @param wait
	 */
	public void setWait(long wait) {
		this.waitThink = wait;
	}
	
	/**
	 * gibt die Zeit zurück, die der Timer immer wartet um neu zu zeichnen
	 * 
	 * @return gibt die Zeit zurück, die der Timer immer wartet
	 */
	public long getWaitRender() {
		return this.waitRender;
	}

	/**
	 * setzt die Zeit, die der Timer immer wartet um neu zu zeichnen, auf den übergebenen Wert
	 * 
	 * @param wait
	 */
	public void setWaitRender(long wait) {
		this.waitRender = wait;
		this.deltaRender = 0;
	}

	/**
	 * startet den Thread
	 */
	public void start() {
		if (!this.isBRunning()) {
			this.setBRunning(true);
			this.last = System.nanoTime();
			this.deltaThink = 0;
			this.deltaRender = 0;
			this.deltaSecond = 0;
			this.fpsCounter = 0;
			Thread t = new Thread(this);
			t.start();
		}
	}

	/**
	 * stoppt den Thread
	 */
	public void stop() {
		if (this.isBRunning()) {
			this.setBRunning(false);
		}
	}

	/**
	 * gibt zurück, ob es gerade gewollt ist den Timer zu nutzen
	 * 
	 * @return TRUE, falls Spiel grad läuft sonst FALSE
	 */
	public boolean isBRunning() {
		return this.bRunning;
	}

	/**
	 * setzt die boolean Variable, die dafür verantwortlich ist, ob die
	 * Run-Methode des Timer läuft, auf den übergebenen Wert
	 * 
	 * @param bRunning
	 */
	public void setBRunning(boolean bRunning) {
		this.bRunning = bRunning;
	}

	public void run() {
		while (this.isBRunning()) {
			this.computeDelta();
			this.think();
			this.render();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {}	
		}
	}

	/**
	 * berechnet wie lange seit dem letzten Aufruf vergangen ist 
	 * @return immer TRUE
	 */
	private boolean computeDelta() {
		long now = System.nanoTime();
		this.delta = now - this.last;
		this.last = now;

		this.deltaThink += this.delta;
		this.deltaRender += this.delta;
		this.deltaSecond += this.delta;
		
		while ( this.deltaSecond > this.FPSUpdater ) {
			this.deltaSecond -= this.FPSUpdater;
			this.fps = this.fpsCounter;
			this.fpsCounter = 0;
		}
		
		return true;
	}
	
	/**
	 * setzt alle Werte auf den Ausgangswert
	 * @return immer TRUE
	 */
	public boolean setRestart() {
		this.last = System.nanoTime();
		this.deltaThink = 0;
		this.deltaRender = 0;
		this.deltaSecond = 0;
		this.fpsCounter = 0;
		return true;
	}
	
	/**
	 * wenn genügend Zeit vergangen ist, ruft er die ThinkMethode auf :)
	 * @return immer TRUE
	 */
	private boolean think() {
		while ( this.deltaThink >= this.getWait() ) {
			this.deltaThink -= this.getWait();
			if ((this.game.isBThink()) && (this.isBThink())) {
				this.game.think( (int)this.getWait()/1000000 );
			}
		}
		
		return true;
	}
	
	/**
	 * wenn genügend Zeit vergangen ist, ruft er die RepaintMethode auf :)
	 * @return immer TRUE
	 */
	private boolean render() {
		int count = 0;
		while ((count < 1) && (this.deltaRender >= this.getWaitRender())) {
			this.deltaRender -= this.getWaitRender();
			count++;
			if ((this.game.isBRepaint()) && (this.isBRender())) {
				this.game.render();
			}
			this.fpsCounter++;
		}
		
		return true;
	}

}
