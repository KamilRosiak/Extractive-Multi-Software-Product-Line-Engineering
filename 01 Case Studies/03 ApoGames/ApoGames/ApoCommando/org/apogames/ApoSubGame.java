package org.apogames;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.apogames.input.ApoKeyboard;
import org.apogames.input.ApoMouse;

import apoCommando.ApoMarioConstants;

/**
 * abstrakte Klasse von der das eigentliche Spielpanel erbt und alles wichtige handelt
 * von den Keyboardeingaben, Mauseingaben und die "eigentliche Engine" bereitstellt
 * 
 * @author Dirk Aporius
 *
 */
public abstract class ApoSubGame extends Thread {

	/** das Screenobjekt für das Spiel */
	protected final ApoScreen screen;
	/** das Keyboardeingabeobjekt für das Spiel */
	protected ApoKeyboard keyboard;
	/** das Mausobjekt für das Spiel */
	protected ApoMouse mouse;
	/** Integer Variable welche angibt, wie die nächste SubGame ID ist */
	private int nextID = 0;
	/** boolean Variable welche angibt, ob ein Spiel gerade läuft oder nicht */
	private boolean isRunning = false;
	/** long Vaitbale die angibt, welche Zeit beim letzten Aufruf war in ms */
	private long last;
	/** Zählt die Anzahl der Neuzeichnungen pro Sekunde */
	private int frames;
	/** boolean Variable die angibt, ob das Spiel immer nachdenken soll */
	private boolean bShouldThink;
	/** boolean Variable die angibt, ob das Spiel immer neu gezeichnet werden soll */
	private boolean bShouldRepaint;
	/** long Variable die angibt, wie lange das Spiel beim letzten Zeichnen benötigt hat in ns */
	protected long renderTime;
	/** long Variable die angibt, wie lange das Spiel beim letzten Nachdenken benötigt hat in ns */
	private long thinkTime;

	private BufferedImage offscreenImage = new BufferedImage(ApoMarioConstants.GAME_WIDTH, ApoMarioConstants.GAME_HEIGHT, BufferedImage.TYPE_INT_RGB);
    private Graphics offscreenGraphics = this.offscreenImage.getGraphics();

	/**
	 * Konstruktor mit dem eigentlichen Screen
	 * @param screen : Screenobjekt
	 */
	public ApoSubGame(final ApoScreen screen) {
		super();
		this.screen = screen;
		this.screen.setSubGame(this);
	}
	
	public void init() {
		this.keyboard = new ApoKeyboard();
		this.screen.addKeyboard(this.keyboard);
		this.mouse = new ApoMouse();
		this.screen.addMouse(this.mouse);
		this.bShouldRepaint = true;
		this.bShouldThink = true;
		
		this.renderTime = 0;
		this.thinkTime = 0;
		
		this.load();
	}

	/**
	 * gibt das Screenobjekt zurück
	 * @return gibt das Screenobjekt zurück
	 */
	public final ApoScreen getScreen() {
		return this.screen;
	}

	@Override
	public void run() {
		long unprocessedTimeThink = 0;
		long unprocessedTimeRender = 0;
		long msPerTickThink = (long)(1000.0 / (double)(ApoMarioConstants.FPS_THINK));
		long msPerTickRender = (long)(1000.0 / (double)(ApoMarioConstants.FPS_RENDER));
		this.frames = 0;
		long lastFpsTime = System.currentTimeMillis() - 1000;
		this.last = System.currentTimeMillis();
		this.isRunning = true;

		// eigentliche Spiellogikschleife
		while (this.isRunning) {
			try {
				long now = System.currentTimeMillis();
				long passedTime = now - this.last;
				// falls Zeit vergangen ist seit dem letzten Aufruf
				if (passedTime > 0) {
					unprocessedTimeThink += passedTime;
					unprocessedTimeRender += passedTime;
					// wenn nachgedacht werden soll dann denke nach
					while (unprocessedTimeThink >= msPerTickThink) {
						if (unprocessedTimeThink > 10 * msPerTickThink) {
							unprocessedTimeThink = 0;
						} else {
							unprocessedTimeThink -= msPerTickThink;
						}
						if (!this.bShouldThink) {
							unprocessedTimeThink = 0;
						} else {
							this.think(msPerTickThink);
						}
					}
					// wenn gezeichnet werden soll, dann zeichne neu
					if (unprocessedTimeRender >= msPerTickRender) {
						unprocessedTimeRender -= msPerTickRender;
						if (this.bShouldRepaint) {
							this.render();
						}
					}
					this.last = now;
				}
				// falls eine Sekunde vergangen ist, dann aktualisiere die FPSanzahl
				if (System.currentTimeMillis() - lastFpsTime > 1000) {
					if (!this.bShouldRepaint) {
						this.render();
					}
					lastFpsTime += 1000;
					this.screen.setFps(this.frames);
					this.frames = 0;
				}
	
				// leg den Thread für 2 Millisekunden schlafen
				try {
					Thread.sleep(2);
				} catch (InterruptedException e) {
				}
			} catch (Exception ex) {
				
			}
		}
	}

	/**
	 * gibt die Klasse mit den Keyboardeingaben zurück
	 * @return gibt die Klasse mit den Keyboardeingaben zurück
	 */
	public final ApoKeyboard getKeyboard() {
		return this.keyboard;
	}
	
	/**
	 * gibt zurück, ob das Spiel immer neu geupdatet werden soll oder nicht
	 * @return TRUE Spiel soll immer neu geupdatet werden, ansonsten FALSE
	 */
	public final boolean shouldThink() {
		return this.bShouldThink;
	}

	/**
	 * setzt den Wert, ob ein Spiel immer geupdatet werden soll oder nicht auf den übergebenen
	 * @param shouldThink : TRUE Spiel soll immer neu geupdatet werden, ansonsten FALSE
	 */
	public void setShouldThink(boolean shouldThink) {
		this.bShouldThink = shouldThink;
	}

	/**
	 * gibt zurück, ob das Spiel immer neu gezeichnet werden soll oder nicht
	 * @return TRUE Spiel soll immer neu gezeichnet werden, FALSE Spiel soll nur neu gezeichnet werden, wenn man über einen Button fährt
	 */
	public final boolean shouldRepaint() {
		return this.bShouldRepaint;
	}

	/**
	 * setzt den Wert, ob neu gezeichnet werden soll auf den übergebenen
	 * @param shouldRepaint : TRUE Spiel soll immer neu gezeichnet werden, FALSE Spiel soll nur neu gezeichnet werden, wenn man über einen Button fährt
	 */
	public void setShouldRepaint(boolean shouldRepaint) {
		this.bShouldRepaint = shouldRepaint;
	}
	
	/**
	 * wird aufgerufen wenn nachgedacht werden soll
	 * und gibt zurück, ob überhaupt nachgedacht werden soll oder nicht
	 * @param delta : Zeit die seit dem letzten Aufruf vergangen ist in Millisekunden
	 * @return TRUE, Spiel wurde geupdatet ansonsten FALSE
	 */
	private final boolean think(long delta) {
		if (this.bShouldThink) {
			long time = System.nanoTime();
			this.update(delta);
			this.thinkTime = System.nanoTime() - time;
			return true;
		}
		return false;
	}

	/**
	 * wird aufgerufen, wenn das Spiel gezeichnet werden soll<br />
	 * Es unterscheidet dabei, ob es sich um eine normale Swingkomponente handelt, die einfach repaintet wird<br />
	 * oder um eine BufferStrategy, die anders gemalt wird<br />
	 */
	public void render() {
		if (this.screen.getBufferStrategy() != null) {
			long time = System.nanoTime();
			this.render(this.screen.getGraphics2D());
			this.screen.update();
			this.addFrame();
			this.renderTime = System.nanoTime() - time;
		} else {
			long time = System.nanoTime();
			this.render((Graphics2D)(this.offscreenGraphics));
            this.addFrame();
            
            if (this.getScreen().getComponent() != null) {
            	Graphics g = this.getScreen().getComponent().getGraphics();
            	if (this.offscreenImage != null) {
            		g.drawImage(this.offscreenImage, 0, 0, null);
            	}
            	g.dispose();
            }
			this.renderTime = System.nanoTime() - time;
		}
	}

	/**
	 * addiert 1 auf die Frames
	 */
	public void addFrame() {
		this.frames += 1;
	}
	
	/**
	 * gibt zurück, wie lange das letzte Zeichnen gebraucht hat
	 * @return gibt zurück, wie lange das letzte Zeichnen gebraucht hat
	 */
	public final long getRenderTime() {
		return this.renderTime;
	}

	/**
	 * gibt zurück, wie lange das letzte "Nachdenken" gebraucht hat
	 * @return gibt zurück, wie lange das letzte "Nachdenken" gebraucht hat
	 */
	public final long getThinkTime() {
		return this.thinkTime;
	}

	/**
	 * Beendet das aktuelle Subgame und setzt die subgame ID auf den neuen Wert
	 * @param nextID : neue SubGame ID
	 */
	public void end(int nextID) {
		this.nextID = nextID;
		this.isRunning = false;
	}

	/**
	 * gibt die nächste ID wieder, wenn zwischen verschiedenen SubGames gewechselt werden soll
	 * @return gibt die nächste ID wieder, wenn zwischen verschiedenen SubGames gewechselt werden soll
	 */
	public final int getNextID() {
		return this.nextID;
	}

	/** Wird beim Initialisieren aufgerufen und läd alles wichtige */
	protected abstract void load();

	/**
	 * Methode wird alle delta Millisekunden aufgerufen und veranlasst das Spiel nachzudenken<br />
	 * die eigentliche Spiellogik steckt hier drin<br />
	 * @param delta : Zeit in Millisekunden, die seit dem letzten Aufruf vergangen ist
	 */
	protected abstract void update(long delta);

	/**
	 * Methode die aufgerufen wird, u, das das eigentliche Spiel auf den Bildschirm zu rendern
	 * @param g : Graphics2D-Objekt
	 */
	protected abstract void render(Graphics2D g);
}
