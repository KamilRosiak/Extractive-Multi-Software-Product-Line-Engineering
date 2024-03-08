package org.apogames;

public class ApoNewThread implements Runnable {
	private ApoThreadInterface game;

	private boolean bRunning;
	private boolean bThink;
	private boolean bRender;

	private long waitThink;
	private long waitRender;

	private float frameAverage = 0;
	private long lastFrame = System.currentTimeMillis();
	private float yield = 10000f;
	private float damping = 0.1f;

	private long lastSecond = this.lastFrame;

	private int fps;
	private int draws;
	
	/**
	 * Konstruktor
	 * @param game = InterfaceObejkt, um mit der Component zu kommunizieren
	 * @param wait = wieviel Millisekunden soll gewartet werden bis wieder nachgedacht und gezeichnet werden soll
	 */
	public ApoNewThread(ApoThreadInterface game, long wait) {
		this( game, wait, wait );
	}
	
	/**
	 * Konstruktor
	 * @param game = InterfaceObejkt, um mit der Component zu kommunizieren
	 * @param waitThink = wieviel Millisekunden soll gewartet werden bis wieder nachgedacht werden soll
	 * @param waitRender = wieviel Millisekunden soll gewartet werden bis wieder gezeichnet werden soll
	 */
	public ApoNewThread(ApoThreadInterface game, long waitThink, long waitRender ) {
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
	public ApoNewThread(ApoThreadInterface game, long waitThink, long waitRender, boolean bThink, boolean bRender) {
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
	}

	/**
	 * startet den Thread
	 */
	public void start() {
		if (!this.isBRunning()) {
			this.setBRunning(true);
            this.frameAverage = this.waitRender;
            this.lastFrame = System.currentTimeMillis();
            this.yield = 10000f;
            this.damping = 0.1f;
            this.lastSecond = lastFrame;
			
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
			/*try {
				Thread.sleep(10);
			} catch (InterruptedException e) {}*/	
		}
	}

	/**
	 * berechnet wie lange seit dem letzten Aufruf vergangen ist 
	 * @return immer TRUE
	 */
	private boolean computeDelta() {
        long timeNow = System.currentTimeMillis();
        this.frameAverage = (this.frameAverage * 10 + (timeNow - this.lastFrame)) / 11;
        this.lastFrame = timeNow;

        this.yield += this.yield*((this.waitRender/1000000/this.frameAverage)-1)*this.damping+0.05f;

        //System.out.println(this.yield);
        
        for(int i = 0; i < this.yield; i++) {
            Thread.yield();
        }
        
        if (timeNow - this.lastSecond >= 1000) {
            this.fps = this.draws;
            this.draws = 0;
            this.lastSecond = timeNow;
        }
		
		return true;
	}
	
	/**
	 * setzt alle Werte auf den Ausgangswert
	 * @return immer TRUE
	 */
	public boolean setRestart() {
		this.frameAverage = this.waitRender;
        this.lastFrame = System.currentTimeMillis();
        this.yield = 10000f;
        this.damping = 0.1f;
        this.lastSecond = lastFrame;
		return true;
	}
	
	/**
	 * wenn genügend Zeit vergangen ist, ruft er die ThinkMethode auf :)
	 * @return immer TRUE
	 */
	private boolean think() {
		if ((this.game.isBThink()) && (this.isBThink())) {
			this.game.think( (int)this.getWait()/1000000 );
		}		
		return true;
	}
	
	/**
	 * wenn genügend Zeit vergangen ist, ruft er die RepaintMethode auf :)
	 * @return immer TRUE
	 */
	private boolean render() {
		if ((this.game.isBRepaint()) && (this.isBRender())) {
			this.game.render();
		}
		this.draws++;
		
		return true;
	}

}
