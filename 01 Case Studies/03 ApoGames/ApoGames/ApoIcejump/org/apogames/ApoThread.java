package org.apogames;

/**
 * Klasse, die sich um den Timer kümmert und somit dass es flüssig alles in
 * einer bestimmten Reihenfolge abläuft
 * 
 * @author Dirk Aporius
 * 
 */
public class ApoThread implements Runnable {	
	private ApoThreadInterface game;

	private boolean bRunning;
	private boolean bThink;
	private boolean bRender;

	private int waitThinkTicks;
	private int waitRenderTicks;
	
	private long waitThink;
	private long waitRender;

	private long last;
	
	private long fps;

	/**
	 * Konstruktor
	 * @param game = InterfaceObejkt, um mit der Component zu kommunizieren
	 * @param wait = wieviel Millisekunden soll gewartet werden bis wieder nachgedacht und gezeichnet werden soll
	 */
	public ApoThread(ApoThreadInterface game, int ticks) {
		this(game, ticks, ticks);
	}
	
	/**
	 * Konstruktor
	 * @param game = InterfaceObejkt, um mit der Component zu kommunizieren
	 * @param waitThink = wieviel Millisekunden soll gewartet werden bis wieder nachgedacht werden soll
	 * @param waitRender = wieviel Millisekunden soll gewartet werden bis wieder gezeichnet werden soll
	 */
	public ApoThread(ApoThreadInterface game, int waitThinkTicks, int waitRenderTicks) {
		this(game, waitThinkTicks, waitRenderTicks, true, true);
	}

	/**
	 * Konstruktor
	 * @param game = InterfaceObejkt, um mit der Component zu kommunizieren
	 * @param waitThink = wieviel Millisekunden soll gewartet werden bis wieder nachgedacht werden soll
	 * @param waitRender = wieviel Millisekunden soll gewartet werden bis wieder gezeichnet werden soll
	 * @param bThink = boolean Variable, die angibt, ob gedacht werden soll oder nicht
	 * @param bRender = boolean Variable, die angibt, ob gemalt werden soll oder nicht
	 */
	public ApoThread(ApoThreadInterface game, int waitThinkTicks, int waitRenderTicks, boolean bThink, boolean bRender) {
		super();

		this.game = game;
		this.waitRenderTicks = waitRenderTicks;
		this.waitThinkTicks = waitThinkTicks;
		this.waitThink = 1000 / this.waitThinkTicks;
		this.waitRender = 1000 / this.waitRenderTicks;

		this.bRunning = false;
		
		this.setBThink(bThink);
		this.setBRender(bRender);
	}
	
	public void setWaitThinkTicks(int waitThinkTicks) {
		this.waitThinkTicks = waitThinkTicks;
		this.waitThink = 1000 / this.waitThinkTicks;
	}

	public void setWaitRenderTicks(int waitRenderTicks) {
		this.waitRenderTicks = waitRenderTicks;
		this.waitRender = 1000 / this.waitRenderTicks;
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
			this.last = System.nanoTime();
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
        long unprocessedTimeThink = 0;
        long unprocessedTimeRender = 0;
        long nsPerTickThink = 1000000000 / this.waitThinkTicks;
        long nsPerTickRender = 1000000000 / this.waitRenderTicks;
        int frames = 0;
        long lastFpsTime = System.currentTimeMillis();
		
		while (this.isBRunning()) {
//			this.computeDelta();
//			this.think();
//			this.render();
			long now = System.nanoTime();
            long passedTime = now - this.last;
            if (passedTime > 0) {
                unprocessedTimeThink += passedTime;
                unprocessedTimeRender += passedTime;
                while (unprocessedTimeThink >= nsPerTickThink) {
                    unprocessedTimeThink -= nsPerTickThink;
                    if (this.game.isBThink()) {
                    	this.game.think((int)this.waitThink);
                    }
                }
                while (unprocessedTimeRender >= nsPerTickRender) {
                    unprocessedTimeRender -= nsPerTickRender;
//                    this.game.render();
//                    frames++;
                }
                this.last = now;
            }
            
            if (this.game.isBRepaint()) {
            	this.game.render();
            }
            frames++;
            
            if (System.currentTimeMillis()-lastFpsTime>1000) {
                lastFpsTime+=1000;
                this.fps = frames;
                //System.out.println(frames+" fps");
                frames = 0;
            }

            this.game.renderGraphics();
            
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {}	
		}
	}

	/**
	 * setzt alle Werte auf den Ausgangswert
	 * @return immer TRUE
	 */
	public boolean setRestart() {
		this.last = System.nanoTime();
		return true;
	}

}
