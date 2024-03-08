package org.apogames;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JApplet;
import javax.swing.JFrame;

import org.apogames.input.ApoKeyboard;
import org.apogames.input.ApoMouse;

/**
 * Klasse, die das eigentliche Frame erstellt und die Komponente beherbergt<br />
 * und sich darum kümmert, dass die Update und die Render-Methode ordentlich weitergereicht werden<br />
 * @author Dirk Aporius
 *
 */
public class ApoScreen {

	/** das graphicsEnvironmentObjekt des Spiels */
	private GraphicsEnvironment graphicEnvironment;
	/** das graphicsDeviceObjekt des Spiels */
	private GraphicsDevice graphicDevice;
	/** das graphicsConfigurationObjekt des Spiels */
	private GraphicsConfiguration graphicConfiguration;
	/** Der Titel des Spiels */
	private String title;
	/** die DisplayConfiguration des Spiels */
	private ApoDisplayConfiguration displayConfiguration;
	/** das BufferStrategy objekt zum hardwarebeschleunigten Zeichnen*/
	private BufferStrategy bufferStrategy;
	/** die eigentliche Komponente, wo das Spiel draufliegt */
	private Component component = null;
	/** Elternobjekt, um herauszufinden ob es ein JApplet ist */
	private Component parent = null;
	/** das aktuelle SubGameobjekt */
	private ApoSubGame subGame;
	/** das eigentliche JFrame, was das Spiel beinhaltet und anzeigt */
	private JFrame frame;
	/** Variable, die die Frames per Second speichert */
	private int fps;

	/**
	 * Konstruktor mit dem Titel und der Displaykonfiguration des Spiels
	 * @param title : Titel des Spiels
	 * @param displayConfiguration : Displaykonfigurationen
	 */
	public ApoScreen(String title, ApoDisplayConfiguration displayConfiguration) {
		this.graphicEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		this.graphicDevice = graphicEnvironment.getDefaultScreenDevice();
		this.graphicConfiguration = graphicDevice.getDefaultConfiguration();

		this.title = title;
		this.displayConfiguration = displayConfiguration;
	}

	/**
	 * wird beim initialisieren des Spiels aufgerufen und erstellt das eigentliche Fenster oder das Applet
	 */
	public void init() {
		if (this.displayConfiguration.isApplet()) {
			this.initApplet();
		} else {
			if (this.displayConfiguration.isWindowed()) {
				this.initWindowed();
			} else {
				this.initFullscreen();
			}
		}
	}

	/**
	 * gibt die eigentliche Komponente, wo das Spiel draufliegt zurück
	 * @return gibt die eigentliche Komponente, wo das Spiel draufliegt zurück
	 */
	public final Component getComponent() {
		return this.component;
	}

	/**
	 * gibt die Frames per Seconds zurück
	 * @return gibt die Frames per Seconds zurück
	 */
	public int getFps() {
		return this.fps;
	}

	/**
	 * setzt die Frame per Seconds auf den übergebenen Wert
	 * @param fps : neue Frames per Seconds
	 */
	public void setFps(int fps) {
		this.fps = fps;
	}

	/**
	 * gibt das aktuelle SubGame des Spiels zurück
	 * @return gibt das aktuelle SubGame des Spiels zurück
	 */
	public ApoSubGame getSubGame() {
		return this.subGame;
	}

	/**
	 * setzt das SubGame auf den übergebenen Wert und malt es neu
	 * @param subGame : neues subGame
	 */
	public void setSubGame(ApoSubGame subGame) {
		this.subGame = subGame;
		
		this.subGame.render();
	}

	/**
	 * gibt das Elternelement des Screens zurück
	 * @return gibt das Elternelement des Screens zurück
	 */
	public final Component getParent() {
		return this.parent;
	}

	/**
	 * setzt das Elternelement des Screens auf das übergebene
	 * @param parent : neues Elternelement
	 */
	public void setParent(Component parent) {
		this.parent = parent;
	}

	/**
	 * initialisiert das Applet
	 */
	public void initApplet() {
		if ((this.parent != null) && (this.parent instanceof JApplet)) {
			final int width = this.displayConfiguration.getWidth();
			final int height = this.displayConfiguration.getHeight();

			Canvas window = null;
			if (ApoConstants.BUFFER_STRATEGY) {
				window = new Canvas(graphicConfiguration);
			} else {
				window = new ApoCanvas();
			}
			window.setPreferredSize(new Dimension(width, height));
			window.setBackground(Color.BLACK);

			ApoConstants.B_APPLET = true;

			JApplet applet = (JApplet)this.parent;
			applet.setSize(new Dimension(width, height));
			applet.add(window);
			applet.setIgnoreRepaint(true);
			applet.add(window);
			applet.setEnabled(true);
			applet.setVisible(true);

			if (ApoConstants.BUFFER_STRATEGY) {
				window.createBufferStrategy(2);
				this.bufferStrategy = window.getBufferStrategy();
			}
			
			this.component = window;
		}
	}

	/**
	 * initialisiert das eigentliche Spielfenster im Windowmodus und unterscheidet, ob mit BufferStrategy oder ohne gespielt werden soll
	 */
	private void initWindowed() {
		this.frame = new JFrame(this.title, this.graphicConfiguration);

		try {
			this.frame.setIconImage(ImageIO.read(this.getClass().getClassLoader().getResource("images/icon.gif")));
		} catch (IOException e) {
		} catch (Exception e) {
		}
		
		if (ApoConstants.BUFFER_STRATEGY) {
			Canvas window = new Canvas(this.graphicConfiguration);
	
			int width = this.displayConfiguration.getWidth();
			int height = this.displayConfiguration.getHeight();
			window.setPreferredSize(new Dimension(width, height));
			window.setBackground(Color.BLACK);
	
			this.addWindowListener(this.frame);
			this.frame.setResizable(false);
			this.frame.setIgnoreRepaint(true);
			this.frame.add(window);
			this.frame.pack();
			this.frame.setLocationRelativeTo(null);
			this.frame.setEnabled(true);
			this.frame.setVisible(true);
	
			window.createBufferStrategy(2);
			this.bufferStrategy = window.getBufferStrategy();
			
			this.component = window;
		} else {
			ApoCanvas window = new ApoCanvas();
			
			int width = this.displayConfiguration.getWidth();
			int height = this.displayConfiguration.getHeight();
			window.setPreferredSize(new Dimension(width, height));
			window.setBackground(Color.BLACK);
	
			this.addWindowListener(this.frame);
			this.frame.setResizable(false);
			this.frame.setIgnoreRepaint(false);
			this.frame.add(window);
			this.frame.pack();
			this.frame.setLocationRelativeTo(null);
			this.frame.setEnabled(true);
			this.frame.setVisible(true);
			
			this.component = window;
		}
	}
	
	/**
	 * repaintet das Frame, wenn es repainten darf
	 */
	public void repaint() {
		if (this.frame != null) {
			this.frame.repaint();
		}
	}
	
	/**
	 * fügt einen Windowlistener auf ein übergebenes Frame hinzu und überschreibt die Close-Methode
	 * @param frame : Fenster
	 */
	private void addWindowListener(JFrame frame) {
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				ApoScreen.this.quit();
				System.exit(0);
			}
		});
	}
	
	/**
	 * wird aufgerufen, wenn man das Spiel ausschalten will
	 */
	private void quit() {
	}

	/**
	 * initialisiert das Frame im Vollbildmodus
	 */
	private void initFullscreen() {
		this.frame = new JFrame(this.title, this.graphicConfiguration);
		java.awt.Window window = new java.awt.Window(this.frame, this.graphicConfiguration);

		this.addWindowListener(this.frame);
		this.frame.setEnabled(true);
		this.frame.setVisible(true);

		int width = this.displayConfiguration.getWidth();
		int height = this.displayConfiguration.getHeight();
		window.setPreferredSize(new Dimension(width, height));
		window.setBackground(Color.BLACK);
		window.setVisible(true);
		window.setEnabled(true);

		this.graphicDevice.setFullScreenWindow(window);
		DisplayMode display = new DisplayMode(this.displayConfiguration.getWidth(), this.displayConfiguration.getHeight(), this.displayConfiguration.getDepth(), ApoConstants.FPS_RENDER);
		this.graphicDevice.setDisplayMode(display);

		window.createBufferStrategy(2);
		this.bufferStrategy = window.getBufferStrategy();

		this.component = window;
	}

	/**
	 * versucht das Spiel zu zeichnen
	 */
	protected void update() {
		if (this.bufferStrategy != null) {
			try {
				this.bufferStrategy.getDrawGraphics().dispose();
				this.bufferStrategy.show();
			} catch (Exception ex) {
			}
		}
	}

	/**
	 * gibt die BufferStrategy vom Spiel zurück (kann auch NULL sein)
	 * @return gibt die BufferStrategy vom Spiel zurück (kann auch NULL sein)
	 */
	public final BufferStrategy getBufferStrategy() {
		return this.bufferStrategy;
	}

	/**
	 * gibt das Graphics2D-Objekt zum Zeichnen zurück
	 * @return gibt das Graphics2D-Objekt zum Zeichnen zurück
	 */
	public final Graphics2D getGraphics2D() {
		try {
			return (Graphics2D) this.bufferStrategy.getDrawGraphics();
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * gibt die Displaykonfiguration des Spieles zurück
	 * @return gibt die Displaykonfiguration des Spieles zurück
	 */
	public final ApoDisplayConfiguration getDisplayConfiguration() {
		return this.displayConfiguration;
	}

	/**
	 * fügt einen keyListener auf die komponente hinzu, damit auch Tastenevents betrachtet werden können
	 * @param keyboard : Keyboardklassenobjekt
	 */
	public void addKeyboard(ApoKeyboard keyboard) {
		this.component.addKeyListener(keyboard);
		this.component.requestFocus();
	}

	/**
	 * fügt einen mouseListener auf die komponente hinzu, damit auch Mausevents betrachtet werden können
	 * @param mouse : Mausklassenobjekt
	 */
	public void addMouse(ApoMouse mouse) {
		this.component.addMouseListener(mouse);
		this.component.addMouseMotionListener(mouse);
		this.component.requestFocus();
	}
}
