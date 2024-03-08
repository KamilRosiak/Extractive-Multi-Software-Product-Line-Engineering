package org.apogames;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import org.apogames.entity.ApoButton;

public abstract class ApoComponentBufferedStrategy extends Canvas implements
		ApoThreadInterface, KeyListener, MouseListener, MouseMotionListener,
		MouseWheelListener, FocusListener {

	private static final long serialVersionUID = 1L;
	
    private final boolean bStrategy;

	private boolean[] bDirections;

	private boolean bThink;

	private boolean bRepaint;

	private boolean bBack;

	private boolean bWin;

	private boolean bHelp;

	private boolean bLoose;

	private ApoThread thread;

	private boolean bMouse;

	private boolean bKey;

	private boolean bTimer;

	private int TICKS_THINK, TICKS_RENDER;

	private ApoButton[] buttons;
	
    public final int GAME_WIDTH;
    public final int GAME_HEIGHT;
	
    private BufferedImage offscreenImage;
    
    private Graphics offscreenGraphics;
    
    private BufferStrategy strategy;
	
	/**
	 * Konstruktor
	 * 
	 * @param bMouse = sollen Mausevents ausgewertet werden (TRUE ja, FALSE nein)
	 * @param bKey = sollen Keyevents ausgewertet werden (TRUE ja, FALSE nein)
	 * @param wait_time = alle wieviel Millisekunden soll nachgedacht und gezeichnet werden
	 */
	public ApoComponentBufferedStrategy(boolean bMouse, boolean bKey, int ticks, int gameWidth, int gameHeight, boolean bStrategy) {
		this(bMouse, bKey, ticks, ticks, true, gameWidth, gameHeight, bStrategy);
	}

	/**
	 * Konstruktor
	 * 
	 * @param bMouse = sollen Mausevents ausgewertet werden (TRUE ja, FALSE nein)
	 * @param bKey = sollen Keyevents ausgewertet werden (TRUE ja, FALSE nein)
	 * @param wait_time = alle wieviel Millisekunden soll nachgedacht und gezeichnet werden
	 */
	public ApoComponentBufferedStrategy(boolean bMouse, boolean bKey, int ticks_think, int ticks_render, int gameWidth, int gameHeight, boolean bStrategy) {
		this(bMouse, bKey, ticks_think, ticks_render, true, gameWidth, gameHeight, bStrategy);
	}
	
	/**
	 * Konstruktor
	 * 
	 * @param bMouse = sollen Mausevents ausgewertet werden (TRUE ja, FALSE nein)
	 * @param bKey = sollen Keyevents ausgewertet werden (TRUE ja, FALSE nein)
	 * @param wait_time = alle wieviel Millisekunden soll nachgedacht und gezeichnet werden
	 * @param bTimer = boolean Wert, der angibt, ob überhaupt ein Thread gestartet
	 * werden soll welcher zum Nachdenken und Neuzeichnen aufgerufen wird (TRUE ja ich will einen Thread, FALSE nein)
	 */
	public ApoComponentBufferedStrategy(boolean bMouse, boolean bKey, int ticks_think, int ticks_render, boolean bTimer, int gameWidth, int gameHeight, boolean bStrategy) {
		super();

		this.GAME_WIDTH = gameWidth;
		this.GAME_HEIGHT = gameHeight;
		
		this.bStrategy = bStrategy;
		
		int w = this.GAME_WIDTH;
		int h = this.GAME_HEIGHT;
		this.setSize(new Dimension(w, h));
		this.setPreferredSize(new Dimension(w, h));
		this.setMaximumSize(new Dimension(w, h));
		this.setMinimumSize(new Dimension(w, h));
        
		this.setFocusable(true);

		this.setBRepaint(true);
		this.setBThink(true);
		this.bMouse = bMouse;
		this.bKey = bKey;
		this.bTimer = bTimer;

		this.TICKS_THINK = ticks_think;
		this.TICKS_RENDER = ticks_render;

		this.bBack = false;
		this.bWin = false;
		this.bLoose = false;

		this.bDirections = new boolean[4];
		
		super.setIgnoreRepaint(true);
		if (!this.bStrategy) {
			this.offscreenImage = new BufferedImage(this.GAME_WIDTH, this.GAME_HEIGHT, BufferedImage.TYPE_INT_RGB);
			this.offscreenGraphics = this.offscreenImage.getGraphics();			
		}
	}

	public int getTicksRenderer() {
		return this.TICKS_RENDER;
	}

	public void setTicksRenderer(int ticks_render) {
		this.TICKS_RENDER = ticks_render;
		if (this.thread != null) {
			this.thread.setWaitRenderTicks(ticks_render);
		}
	}

	/**
	 * gibt die Buttons zurück
	 * 
	 * @return gibt die Buttons zurück
	 */
	public ApoButton[] getButtons() {
		return this.buttons;
	}

	/**
	 * setzt die Buttons auf die übergebenen
	 * 
	 * @param buttons =
	 *            neue Buttons
	 */
	public void setButtons(ApoButton[] buttons) {
		this.buttons = buttons;
	}

	/**
	 * abstrakte init Methode wird immer aufgerufen kurz bevor der thread
	 * gestartet wird
	 */
	public abstract void init();

	public void keyTyped(KeyEvent e) {
	}

	/**
	 * kümmert sich darum, wenn eine Taste gedrückt wird implementiert sind die
	 * Pfeiltasten und die Taste u
	 */
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();

		if (keyCode == KeyEvent.VK_UP) {
			this.bDirections[ApoConstants.UP] = true;
		}
		if (keyCode == KeyEvent.VK_DOWN) {
			this.bDirections[ApoConstants.DOWN] = true;
		}
		if (keyCode == KeyEvent.VK_LEFT) {
			this.bDirections[ApoConstants.LEFT] = true;
		}
		if (keyCode == KeyEvent.VK_RIGHT) {
			this.bDirections[ApoConstants.RIGHT] = true;
		}
		if (keyCode == KeyEvent.VK_U) {
			this.setBBack(true);
		}
	}

	/**
	 * kümmert sich darum, wenn eine Taste losgelassen wird implementiert sind
	 * die Pfeiltasten und die Taste u
	 */
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_UP)
			this.bDirections[ApoConstants.UP] = false;
		if (keyCode == KeyEvent.VK_DOWN)
			this.bDirections[ApoConstants.DOWN] = false;
		if (keyCode == KeyEvent.VK_LEFT)
			this.bDirections[ApoConstants.LEFT] = false;
		if (keyCode == KeyEvent.VK_RIGHT)
			this.bDirections[ApoConstants.RIGHT] = false;
		if (keyCode == KeyEvent.VK_U)
			this.setBBack(false);
		if (keyCode == KeyEvent.VK_H)
			this.setBHelp(!this.isBHelp());

	}

	/**
	 * abstrakte Methode, die sich um das Handling von Buttons kümmert
	 * 
	 * @param function
	 */
	public abstract void setButtonFunction(String function);

	/**
	 * abstrakte Think Methode, die angibt wieviel Millisekunden seit dem
	 * letzten Aufruf vergangen sind
	 */
	public abstract void think(int delta);

	/**
	 * abstrakte Methode, die aufgerufen wird, um neu zu zeichnen wird von
	 * repaint aufgerufen und malt die Komponent neu
	 */
	public abstract void render(Graphics2D g);

	/**
	 * wird vom Thread aufgerufen und kümmert sich um das neuzeichnen
	 */
	public void render() {
		if (this.bRepaint) {
			if (this.bStrategy) {
				// Render single frame
				do {
					// The following loop ensures that the contents of the drawing buffer
					// are consistent in case the underlying surface was recreated
					do {
						Graphics2D g = (Graphics2D)this.strategy.getDrawGraphics();
						g.clearRect(0, 0, getWidth(), getHeight());
						this.render(g);
						g.dispose();
						// Repeat the rendering if the drawing buffer contents 
						// were restored
					} while (this.strategy.contentsRestored());
					// Display the buffer
					this.strategy.show();
					// Repeat the rendering if the drawing buffer was lost
			     } while (this.strategy.contentsLost());
			} else {
				this.render((Graphics2D)this.offscreenGraphics);
			}
		}
	}

	/**
	 * setzt alle Werte auf den Ausgangswert
	 * @return immer TRUE
	 */
	public boolean setRestartThreadValues() {
		if (this.thread == null) {
			return true;
		}
		return this.thread.setRestart();
	}
	
	/**
	 * gibt die FPS an der gewünschten Stelle aus
	 * @param g : Graphics2D Objekt
	 * @param x : x-Position
	 * @param y : y-Position
	 */
	public void renderFPS(Graphics2D g, int x, int y) {
		g.drawString("FPS: " + this.getFPS(), x, y);
	}

	/**
	 * malt die Buttons
	 * @param g : das Graphics2D Object
	 */
	public void renderButtons(Graphics2D g) {
		if (this.getButtons() != null) {
			for (int i = 0; i < this.getButtons().length; i++) {
				this.getButtons()[i].render(g, 0, 0);
			}
		}
	}

	/**
	 * startet den Thread und fügt Mouse und KeyListeners hinzu wenn es
	 * gewünscht ist
	 */
	public void start() {
		if ((this.thread == null) && (this.bTimer)) {
			if (this.bStrategy) {
				super.createBufferStrategy(3);
				this.strategy = super.getBufferStrategy();
			}
			
			this.setBThink(true);

			this.thread = new ApoThread(this, this.TICKS_THINK, this.TICKS_RENDER);
			this.thread.start();
		}
		if (this.bMouse) {
			this.addMouseListener(this);
			this.addMouseMotionListener(this);
			this.addMouseWheelListener(this);
		}
		if (this.bKey) {
			this.addFocusListener(this);
			if (!ApoConstants.B_APPLET) {
				this.addKeyListener(this);
			} else {
				this.addKeyListener(this);
			}
		}
	}

	/**
	 * stoppt den Thread und entfernt die Mouse und KeyListeners falls welches
	 * hinzugefügt worden sind
	 */
	public void stop() {
		if ((this.thread != null) && (this.bTimer)) {
			this.setBThink(false);

			this.thread.stop();
			this.thread = null;
		}
		if (this.bMouse) {
			this.removeMouseListener(this);
			this.removeMouseMotionListener(this);
			this.removeMouseWheelListener(this);
		}
		if (this.bKey) {
			if (!ApoConstants.B_APPLET) {
				this.removeKeyListener(this);
			} else {
				this.removeKeyListener(this);
			}
		}
	}

	/**
	 * gibt die ungefähre Anzahl der Frame per Second zurück
	 * 
	 * @return gibt die ungefähre Anzahl der Frame per Second zurück
	 */
	public int getFPS() {
		if ((this.thread != null) && (this.bTimer)) {
			return (int) this.thread.getFps();
		}
		return -1;
	}

	/**
	 * gibt zurück, ob nachgedacht werden soll oder nicht
	 */
	public boolean isBThink() {
		return this.bThink;
	}

	/**
	 * setzt den boolean Wert, ob think aufgerufen wird oder nicht auf den
	 * übergebenen Wert
	 * 
	 * @param bThink =
	 *            neuer boolean Wert
	 */
	public void setBThink(boolean bThink) {
		this.bThink = bThink;
	}

	/**
	 * gibt zurück, ob immer gezeichnet werdne soll oder nicht
	 */
	public boolean isBRepaint() {
		return this.bRepaint;
	}

	/**
	 * setzt den boolean Wert, ob immer neu gezeichnet werden soll auf den
	 * übergebenen Wert
	 * 
	 * @param bRepaint
	 */
	public void setBRepaint(boolean bRepaint) {
		this.bRepaint = bRepaint;
	}

	/**
	 * gibt ein boolean Array zurück, in dem steht ob gerade die Pfeiltasten
	 * gedrückt sind (TRUE für gedrückt, sonst FALSE) herausbekommen, welche
	 * Taste im Array wofür steht, kann man mithilfe der ApoConstants (Bsp.:
	 * getBDirections()[ApoConstants.UP] liefert einen boolean Wert zurück, ob
	 * die Pfeiltaste hoch gedrückt wird oder nicht)
	 * 
	 * @return
	 */
	public boolean[] getBDirections() {
		return this.bDirections;
	}

	/**
	 * setzt das booleanArray, ob die Pfeiltasten gerade gedrückt sind auf das
	 * übergebene
	 * 
	 * @param bDirections
	 */
	public void setBDirections(boolean[] bDirections) {
		this.bDirections = bDirections;
	}

	/**
	 * gibt zurück, ob gerade ein Schritt zurück gemacht werden soll
	 * 
	 * @return gibt zurück, ob gerade ein Schritt zurück gemacht werden soll
	 */
	public boolean isBBack() {
		return this.bBack;
	}

	/**
	 * setzt den booleanWert, ob ein Schritt zurück gemacht werden soll auf den
	 * übergebenen
	 * 
	 * @param bBack
	 */
	public void setBBack(boolean bBack) {
		this.bBack = bBack;
	}

	/**
	 * gibt zurück, ob das Spiel verloren wurde oder nicht
	 * 
	 * @return gibt zurück, ob das Spiel verloren wurde oder nicht
	 */
	public boolean isBLoose() {
		return this.bLoose;
	}

	/**
	 * setzt den booleanWert, ob das Spiel verloren wurde oder nicht auf den
	 * übergebenen
	 * 
	 * @param bLoose
	 */
	public void setBLoose(boolean bLoose) {
		this.bLoose = bLoose;
	}

	/**
	 * gibt den booleanWert, ob das Spiel gewonnen wurde oder nicht zurück
	 * 
	 * @return gibt den booleanWert, ob das Spiel gewonnen wurde oder nicht
	 *         zurück
	 */
	public boolean isBWin() {
		return this.bWin;
	}

	/**
	 * setzt den booleanWert, ob das Spiel gewonnen wurde oder nicht, auf den
	 * übergebenen Wert
	 * 
	 * @param bWin
	 */
	public void setBWin(boolean bWin) {
		this.bWin = bWin;
	}

	/**
	 * gibt den booleanWert, ob die Hilfe eingeschaltet ist oder nicht zurück
	 * 
	 * @return gibt den booleanWert, ob die Hilfe eingeschaltet ist oder nicht
	 *         zurück
	 */
	public boolean isBHelp() {
		return this.bHelp;
	}

	/**
	 * setzt den booleanWert, ob die Hilfe eingeschaltet ist oder nicht, auf den
	 * übergebenen Wert
	 * 
	 * @param bHelp
	 */
	public void setBHelp(boolean bHelp) {
		this.bHelp = bHelp;
	}

	/**
	 * wenn die Maus über der Komponente ist, überprüft er ob es über den
	 * Buttons ist
	 */
	public void mouseMoved(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		if (this.buttons != null) {
			for (int i = 0; i < this.buttons.length; i++) {
				if (this.buttons[i].getMove(x, y)) {
					if ((!this.bTimer) || (!this.bRepaint)) {
						this.render();
					}
					break;
				}
			}
		}
	}

	/**
	 * wenn eine Maustaste gedrückt wurde über der Component, wird überprüft ob
	 * man sich über einem Button aufhält ;)
	 */
	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		if (this.buttons != null) {
			for (int i = 0; i < this.buttons.length; i++) {
				if (this.buttons[i].getPressed(x, y)) {
					if ((!this.bTimer) || (!this.bRepaint)) {
						this.render();
					}
					break;
				}
			}
		}
	}

	/**
	 * wenn eine Maustaste losgelassen wird wenn man über der Component ist,
	 * wird geschaut über welchem Button man ist und sagt ihm dann das die Maus
	 * losgelassen wurde
	 */
	public void mouseReleased(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		if (this.buttons != null) {
			for (int i = 0; i < this.buttons.length; i++) {
				if (this.buttons[i].getReleased(x, y)) {
					String function = this.buttons[i].getFunction();
					this.setButtonFunction(function);
				}
			}
		}
		if ((!this.bTimer) || (!this.bRepaint)) {
			this.render();
		}
	}

	public void mouseDragged(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
	}

	public void focusGained(FocusEvent e) {
	}

	public void focusLost(FocusEvent e) {
	}
	
	public void paint(Graphics g) {
	}

	public void update(Graphics g) {
	}
	
	public void renderGraphics() {
        Graphics g = getGraphics();
        g.drawImage(this.offscreenImage, 0, 0, GAME_WIDTH, GAME_HEIGHT, 0, 0, GAME_WIDTH, GAME_HEIGHT, null);
        g.dispose();
	}

}
