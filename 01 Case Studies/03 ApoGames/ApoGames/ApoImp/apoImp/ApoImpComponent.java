package apoImp;

import java.awt.Cursor;
import java.awt.Graphics2D;
import java.io.File;

import javax.swing.JFileChooser;

import org.apogames.ApoConstants;
import org.apogames.ApoScreen;
import org.apogames.ApoSubGame;
import org.apogames.entity.ApoButton;
import org.apogames.help.ApoFileFilter;
import org.apogames.input.ApoMouse;

/**
 * Abstrakte Klasse von der das eigentliche Spiel erbt und grundlegende Funktionalitäten
 * dem Spielobjekt bereitstellt
 * @author Dirk Aporius
 *
 */
public abstract class ApoImpComponent extends ApoSubGame {

	/** Objekt, mit dem mit bestimmten Werten Bilder erstellt und geladen werden können */
	private ApoImpImages images;
	/** Array der ganzen Buttons im Spiel */
	private ApoButton[] buttons;
	/** boolean Variable, ob gerade ein Handcursor angezeigt wird oder nicht */
	private boolean bHandCursor;
	
	/** A FileChooser to load an ai */
	private JFileChooser fileChooser;
	/** A Class file filter for the ai */
	private final ApoFileFilter	fileFilter = new ApoFileFilter(new String[] {"jpg", "png", "bmp", "gif", "jpeg"});
	
	private boolean bWin;
	
	/**
	 * Konstruktor
	 * @param screen : Screenobjekt
	 */
	public ApoImpComponent(ApoScreen screen) {
		super(screen);
	}
	
	@Override
	protected void load() {
		if (this.images == null) {
			this.images = new ApoImpImages();
		}
		super.setShouldRepaint(false);
	}

	public boolean isBWin() {
		return this.bWin;
	}

	public void setBWin(boolean win) {
		this.bWin = win;
	}

	/**
	 * gibt den Filechooser zum Laden einer KI zurück
	 * @return gibt den Filechooser zum Laden einer KI zurück
	 */
	public JFileChooser getFileChooser() {
		if ((!ApoConstants.B_APPLET) && (this.fileChooser == null)) {
			this.fileChooser = new JFileChooser();
			this.fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir") + File.separator));
			this.fileChooser.setFileFilter(this.fileFilter);
		}
		return this.fileChooser;
	}

	/**
	 * gibt den FileFilter für das Laden einer KI zurück
	 * @return gibt den FileFilter für das Laden einer KI zurück
	 */
	public ApoFileFilter getFileFilter() {
		return this.fileFilter;
	}

	/**
	 * boolean Variable, ob die FPS angezeigt werden soll oder nicht
	 * @return TRUE, FPS sollen angezeigt werden, FALSE FPS sollen nicht angezeigt werden
	 */
	public boolean isShowFPS() {
		return ApoImpConstants.FPS;
	}

	/**
	 * setzt den boolean-Wert, ob die FPS angezeigt werden soll oder nicht, auf den übergebenen
	 * @param showFPS : TRUE, FPS sollen angezeigt werden, FALSE FPS sollen nicht angezeigt werden
	 */
	public void setShowFPS(boolean showFPS) {
		ApoImpConstants.FPS = showFPS;
	}

	/**
	 * gibt das Array mit den Buttons zurück
	 * @return gibt das Array mit den Buttons zurück
	 */
	public final ApoButton[] getButtons() {
		return this.buttons;
	}

	/**
	 * setzt das Array mit den Buttons auf den übergebenen Wert
	 * @param buttons : neues Array mit Buttons
	 */
	public void setButtons(ApoButton[] buttons) {
		this.buttons = buttons;
	}

	/**
	 * gibt das Imagesobjekt zurück, zum Laden und Erstellen von Bildern
	 * @return gibt das Imagesobjekt zurück, zum Laden und Erstellen von Bildern
	 */
	public final ApoImpImages getImages() {
		return this.images;
	}
	
	/**
	 * gibt die Frames per Seconds zurück
	 * @return gibt die Frames per Seconds zurück
	 */
	public final int getFPS() {
		return this.screen.getFps();
	}

	/**
	 * rendert die Buttons
	 * @param g : das Graphics2D Object
	 */
	public void renderButtons(Graphics2D g) {
		if (this.buttons != null) {
			for (int i = 0; i < this.buttons.length; i++) {
				this.buttons[i].render(g, 0, 0);
			}
		}
	}
	
	// Methode, die alle delta-Millisekunden aufgerufen wird und sich um die Logik des Spiels kümmert und um die Eingaben der Tastatur und Maus
	@Override
	protected void update(long delta) {
		int[] keyPressed = this.keyboard.getPressed();
		if (keyPressed != null) {
			for (int i = 0; i < keyPressed.length; i++) {
				this.keyPressed(keyPressed[i], (char) ((int) keyPressed[i]));
			}
		}

		int[][] keyReleased = this.keyboard.getReleased();
		if (keyReleased != null) {
			for (int i = 0; i < keyReleased.length; i++) {
				this.keyReleased(keyReleased[i][0], (char)(keyReleased[i][1]));
			}
		}

		if (this.mouse.isDragged()) {
			this.mouseDragged(this.mouse.getX(), this.mouse.getY(), this.mouse.hasClicked(ApoMouse.LEFT));
		} else if (this.mouse.hasClicked(ApoMouse.LEFT)) {
			this.mousePressed(this.mouse.getX(), this.mouse.getY(), true);
		} else if (this.mouse.hasClicked(ApoMouse.RIGHT)) {
			this.mousePressed(this.mouse.getX(), this.mouse.getY(), false);
		} else if (this.mouse.isMoved()) {
			this.mouseMoved(this.mouse.getX(), this.mouse.getY());
		}
		boolean[] mouseReleased = this.mouse.getReleased();
		if (mouseReleased[ApoMouse.LEFT]) {
			this.mouseReleased(this.mouse.getX(), this.mouse.getY(), true);
		} else if (mouseReleased[ApoMouse.RIGHT]) {
			this.mouseReleased(this.mouse.getX(), this.mouse.getY(), false);
		}
		
		int changed = this.mouse.getMouseWheelChanged();
		if (changed != 0) {
			this.mouseWheelChanged(changed);
		}
		
		this.think(delta);
	}

	/**
	 * abstrakte Methode, die die Klasse zur Logikberechnung benutzen soll alle delta Millisekunden
	 * @param delta : Zeit die vergangen ist in Millieskunden seit dem letzten Aufruf
	 */
	public abstract void think(long delta);
	
	/**
	 * wird aufgerufen, wenn eine Tastaturtaste gedrückt wird<br />
	 * @param button : KeyEventKonstante für die Variable
	 * @param character : Character der KeyEventKostante
	 */
	public void keyPressed(int keyCode, char keyCharacter) {
	}

	/**
	 * wird aufgerufen, wenn eine Tastaturtaste losgelassen wird<br />
	 * @param button : KeyEventKonstante für die Variable
	 * @param character : Character der KeyEventKostante
	 */
	public void keyReleased(int keyCode, char keyChar) {
	}

	/**
	 * wird aufgerufen, wenn die Maus bewegt wurde und dabei eine Maustaste gedrückt gehalten wird
	 * @param x : X-Wert der Maus (im Frame gesehen)
	 * @param y : Y-Wert der Maus (im Frame gesehen)
	 * @param left : TRUE, linke Maustaste gedrückt, ansonsten FALSE
	 */
	public void mouseDragged(int x, int y, boolean left) {
	}

	/**
	 * wird aufgerufen, wenn die Maus bewegt wurde und wertet aus, ob die Maus über einem Button ist oder nicht
	 * @param x : X-Wert der Maus (im Frame gesehen)
	 * @param y : Y-Wert der Maus (im Frame gesehen)
	 */
	public void mouseMoved(int x, int y) {
		boolean bOver = false;
		if (this.buttons != null) {
			for (int i = 0; i < this.buttons.length; i++) {
				if (this.buttons[i].getMove(x, y)) {
					bOver = true;
					if (!super.shouldRepaint()) {
						this.render();
					}
					break;
				} else if (this.buttons[i].isBOver()) {
					bOver = true;
				}
			}
		}
		if (bOver) {
			if (!this.bHandCursor) {
				this.screen.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				this.bHandCursor = true;
			}
		} else {
			if (this.bHandCursor) {
				this.screen.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				this.bHandCursor = false;
			}
		}
	}
	
	public void setCursor(Cursor cursor) {
		if (!this.screen.getComponent().getCursor().equals(cursor)) {
			this.screen.getComponent().setCursor(cursor);
		}
	}

	/**
	 * wird aufgerufen, wenn eine Maustaste gedrückt wird und überprüft, ob sie über einem Button ist
	 * @param x : X-Wert der Maus (im Frame gesehen)
	 * @param y : Y-Wert der Maus (im Frame gesehen)
	 * @param left : TRUE, linke Maustaste gedrückt, ansonsten FALSE
	 */
	public void mousePressed(int x, int y, boolean left) {
		if (this.buttons != null) {
			for (int i = 0; i < this.buttons.length; i++) {
				if (this.buttons[i].getPressed(x, y)) {
					if (!super.shouldRepaint()) {
						this.render();
					}
					break;
				}
			}
		}
	}

	/**
	 * wird aufgerufen, wenn eine Maustaste losgelassen wurde und überprüft, ob sie über einem Button ist<br />
	 * und ruft in diesem Fall die setButtonFunction mit der eindeutigen Funktion des Buttons auf
	 * @param x : X-Wert der Maus (im Frame gesehen)
	 * @param y : Y-Wert der Maus (im Frame gesehen)
	 * @param left : TRUE, linke Maustaste gedrückt, ansonsten FALSE
	 */
	public boolean mouseReleased(int x, int y, boolean left) {
		if (this.buttons != null) {
			for (int i = 0; i < this.buttons.length; i++) {
				if (this.buttons[i].getReleased(x, y)) {
					String function = this.buttons[i].getFunction();
					this.setButtonFunction(function);
					if (!super.shouldRepaint()) {
						this.render();
					}
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * wird aufgerufen, wenn ein Button gedrückt wurde
	 * @param function : Funktion, die der Button ausführen soll und ihn einzigartig macht
	 */
	public abstract void setButtonFunction(String function);
	
	public abstract void mouseWheelChanged(int changed);
	
}
