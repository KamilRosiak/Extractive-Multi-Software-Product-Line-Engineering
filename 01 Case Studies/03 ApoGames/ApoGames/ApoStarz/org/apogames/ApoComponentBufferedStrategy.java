package org.apogames;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
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
import java.awt.image.VolatileImage;

import org.apogames.entity.ApoButton;

public abstract class ApoComponentBufferedStrategy extends Canvas implements
		ApoTimerInterface, KeyListener, MouseListener, MouseMotionListener,
		MouseWheelListener, FocusListener {

	private static final long serialVersionUID = 1L;

	private boolean[] bDirections;

	private boolean bThink;

	private boolean bRepaint;

	private boolean bBack;

	private boolean bWin;

	private boolean bHelp;

	private boolean bLoose;

	private ApoThread timer;

	private boolean bMouse;

	private boolean bKey;

	private boolean bTimer;

	private int WAIT_TIME;

	private ApoButton[] buttons;

	private VolatileImage backbuffer;

	private GraphicsEnvironment ge;

	private GraphicsConfiguration gc;

	private BufferStrategy strategy;

	/**
	 * Konstruktor
	 * 
	 * @param bMouse =
	 *            sollen Mausevents ausgewertet werden (TRUE ja, FALSE nein)
	 * @param bKey =
	 *            sollen Keyevents ausgewertet werden (TRUE ja, FALSE nein)
	 * @param wait_time =
	 *            alle wieviel Millisekunden soll nachgedacht und gezeichnet
	 *            werden
	 */
	public ApoComponentBufferedStrategy(boolean bMouse, boolean bKey, int wait_time) {
		this(bMouse, bKey, wait_time, true);
	}

	/**
	 * Konstruktor
	 * 
	 * @param bMouse =
	 *            sollen Mausevents ausgewertet werden (TRUE ja, FALSE nein)
	 * @param bKey =
	 *            sollen Keyevents ausgewertet werden (TRUE ja, FALSE nein)
	 * @param wait_time =
	 *            alle wieviel Millisekunden soll nachgedacht und gezeichnet
	 *            werden
	 * @param bTimer =
	 *            boolean Wert, der angibt, ob �berhaupt ein Thread gestartet
	 *            werden soll welcher zum Nachdenken und Neuzeichnen aufgerufen
	 *            wird (TRUE ja ich will einen Thread, FALSE nein)
	 */
	public ApoComponentBufferedStrategy(boolean bMouse, boolean bKey, int wait_time,
			boolean bTimer) {
		super();

		this.ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		this.gc = this.ge.getDefaultScreenDevice().getDefaultConfiguration();

		this.setFocusable(true);

		super.setIgnoreRepaint(true);

		this.setBRepaint(true);
		this.setBThink(true);
		this.bMouse = bMouse;
		this.bKey = bKey;
		this.bTimer = bTimer;

		this.WAIT_TIME = wait_time;

		this.bBack = false;
		this.bWin = false;
		this.bLoose = false;

		this.bDirections = new boolean[4];
	}

	/**
	 * gibt die Buttons zur�ck
	 * 
	 * @return gibt die Buttons zur�ck
	 */
	public ApoButton[] getButtons() {
		return this.buttons;
	}

	/**
	 * setzt die Buttons auf die �bergebenen
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
	 * k�mmert sich darum, wenn eine Taste gedr�ckt wird implementiert sind die
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
	 * k�mmert sich darum, wenn eine Taste losgelassen wird implementiert sind
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
	 * abstrakte Methode, die sich um das Handling von Buttons k�mmert
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
	 * erstellt den Backbuffer damit es smooth l�uft
	 * 
	 */
	private void createBackbuffer() {
		if (this.backbuffer != null) {
			this.backbuffer.flush();
			this.backbuffer = null;
		}
		// GraphicsConfiguration f�r VolatileImage
		this.ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		this.gc = this.ge.getDefaultScreenDevice().getDefaultConfiguration();
		this.backbuffer = this.gc.createCompatibleVolatileImage(getWidth(),
				getHeight());
	}

	/**
	 * pr�ft, ob der aktuelle Backbuffer noch valide ist sonst erstellt er einen
	 * neuen
	 * 
	 */
	private void checkBackbuffer() {
		if (this.backbuffer == null) {
			this.createBackbuffer();
		}
		if (this.backbuffer.validate(this.gc) == VolatileImage.IMAGE_INCOMPATIBLE) {
			this.createBackbuffer();
		}
	}

	/**
	 * wird vom Thread aufgerufen und k�mmert sich um das neuzeichnen
	 */
	public void render() {
		if (this.strategy == null) {
			return;
		}

		checkBackbuffer();
		Graphics2D g = (Graphics2D) this.backbuffer.getGraphics();
		render(g);
		g.dispose();

		Graphics g2 = this.strategy.getDrawGraphics();
		g2.drawImage(this.backbuffer, 0, 0, this);
		g2.dispose();

		this.strategy.show();
	}

	public void renderFPS(Graphics2D g, int x, int y) {
		if (ApoConstants.B_FPS) {
			g.setFont(new Font("Dialog", Font.BOLD, 13));
			g.setColor(Color.white);
			g.drawString("FPS: " + this.getFPS(), x, y);
		}
	}

	public void renderButtons(Graphics2D g) {
		if (this.getButtons() != null) {
			for (int i = 0; i < this.getButtons().length; i++) {
				this.getButtons()[i].render(g, 0, 0);
			}
		}
	}

	/**
	 * startet den Thread und f�gt Mouse und KeyListeners hinzu wenn es
	 * gew�nscht ist
	 */
	public void start() {
		if ((this.timer == null) && (this.bTimer)) {
			super.createBufferStrategy(2);
			this.strategy = getBufferStrategy();

			this.setBThink(true);

			this.timer = new ApoThread(this, WAIT_TIME);
			this.timer.start();
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
	 * hinzugef�gt worden sind
	 */
	public void stop() {
		if ((this.timer != null) && (this.bTimer)) {
			this.setBThink(false);

			this.timer.stop();
			this.timer = null;
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
	 * gibt die ungef�hre Anzahl der Frame per Second zur�ck
	 * 
	 * @return gibt die ungef�hre Anzahl der Frame per Second zur�ck
	 */
	public int getFPS() {
		if ((this.timer != null) && (this.bTimer)) {
			return (int) this.timer.getFps();
		}
		return -1;
	}

	/**
	 * gibt zur�ck, ob nachgedacht werden soll oder nicht
	 */
	public boolean isBThink() {
		return this.bThink;
	}

	/**
	 * setzt den boolean Wert, ob think aufgerufen wird oder nicht auf den
	 * �bergebenen Wert
	 * 
	 * @param bThink =
	 *            neuer boolean Wert
	 */
	public void setBThink(boolean bThink) {
		this.bThink = bThink;
	}

	/**
	 * gibt zur�ck, ob immer gezeichnet werdne soll oder nicht
	 */
	public boolean isBRepaint() {
		return this.bRepaint;
	}

	/**
	 * setzt den boolean Wert, ob immer neu gezeichnet werden soll auf den
	 * �bergebenen Wert
	 * 
	 * @param bRepaint
	 */
	public void setBRepaint(boolean bRepaint) {
		this.bRepaint = bRepaint;
	}

	/**
	 * gibt ein boolean Array zur�ck, in dem steht ob gerade die Pfeiltasten
	 * gedr�ckt sind (TRUE f�r gedr�ckt, sonst FALSE) herausbekommen, welche
	 * Taste im Array wof�r steht, kann man mithilfe der ApoConstants (Bsp.:
	 * getBDirections()[ApoConstants.UP] liefert einen boolean Wert zur�ck, ob
	 * die Pfeiltaste hoch gedr�ckt wird oder nicht)
	 * 
	 * @return
	 */
	public boolean[] getBDirections() {
		return this.bDirections;
	}

	/**
	 * setzt das booleanArray, ob die Pfeiltasten gerade gedr�ckt sind auf das
	 * �bergebene
	 * 
	 * @param bDirections
	 */
	public void setBDirections(boolean[] bDirections) {
		this.bDirections = bDirections;
	}

	/**
	 * gibt zur�ck, ob gerade ein Schritt zur�ck gemacht werden soll
	 * 
	 * @return gibt zur�ck, ob gerade ein Schritt zur�ck gemacht werden soll
	 */
	public boolean isBBack() {
		return this.bBack;
	}

	/**
	 * setzt den booleanWert, ob ein Schritt zur�ck gemacht werden soll auf den
	 * �bergebenen
	 * 
	 * @param bBack
	 */
	public void setBBack(boolean bBack) {
		this.bBack = bBack;
	}

	/**
	 * gibt zur�ck, ob das Spiel verloren wurde oder nicht
	 * 
	 * @return gibt zur�ck, ob das Spiel verloren wurde oder nicht
	 */
	public boolean isBLoose() {
		return this.bLoose;
	}

	/**
	 * setzt den booleanWert, ob das Spiel verloren wurde oder nicht auf den
	 * �bergebenen
	 * 
	 * @param bLoose
	 */
	public void setBLoose(boolean bLoose) {
		this.bLoose = bLoose;
	}

	/**
	 * gibt den booleanWert, ob das Spiel gewonnen wurde oder nicht zur�ck
	 * 
	 * @return gibt den booleanWert, ob das Spiel gewonnen wurde oder nicht
	 *         zur�ck
	 */
	public boolean isBWin() {
		return this.bWin;
	}

	/**
	 * setzt den booleanWert, ob das Spiel gewonnen wurde oder nicht, auf den
	 * �bergebenen Wert
	 * 
	 * @param bWin
	 */
	public void setBWin(boolean bWin) {
		this.bWin = bWin;
	}

	/**
	 * gibt den booleanWert, ob die Hilfe eingeschaltet ist oder nicht zur�ck
	 * 
	 * @return gibt den booleanWert, ob die Hilfe eingeschaltet ist oder nicht
	 *         zur�ck
	 */
	public boolean isBHelp() {
		return this.bHelp;
	}

	/**
	 * setzt den booleanWert, ob die Hilfe eingeschaltet ist oder nicht, auf den
	 * �bergebenen Wert
	 * 
	 * @param bHelp
	 */
	public void setBHelp(boolean bHelp) {
		this.bHelp = bHelp;
	}

	/**
	 * wenn die Maus �ber der Komponente ist, �berpr�ft er ob es �ber den
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
	 * wenn eine Maustaste gedr�ckt wurde �ber der Component, wird �berpr�ft ob
	 * man sich �ber einem Button aufh�lt ;)
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
	 * wenn eine Maustaste losgelassen wird wenn man �ber der Component ist,
	 * wird geschaut �ber welchem Button man ist und sagt ihm dann das die Maus
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
}
