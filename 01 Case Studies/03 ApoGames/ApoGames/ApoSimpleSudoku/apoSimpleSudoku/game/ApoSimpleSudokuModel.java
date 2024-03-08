package apoSimpleSudoku.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * abstrakte Klasse, von der die eigentlichen Screens nachher erben, um sie übersichtlicher zu halten<br />
 * Beispiel der Editor erbt von dieser Klasse und auch das eigentliche Spiel<br />
 * Diese Klassen erben jeweils von dieser<br />
 * @author Dirk Aporius
 *
 */
public abstract class ApoSimpleSudokuModel {
	/** Spielobjekt, um mit der eigentlichen Hauptklasse zu kommunizieren */
	private final ApoSimpleSudokuPanel game;
	
	private BufferedImage iBackground;
	
	/**
	 * Konstruktor
	 * @param game : Das Gameobjekt der Hauptklasse für das Spiel
	 */
	public ApoSimpleSudokuModel(ApoSimpleSudokuPanel game) {
		this.game = game;
	}
	
	/**
	 * gibt das Spielobjekt zurück
	 * @return gibt das Spielobjekt zurück
	 */
	public final ApoSimpleSudokuPanel getGame() {
		return this.game;
	}

	/**
	 * wird beim initialisieren der Klasse aufgerufen<br />
	 */
	public void init() {
	}
	
	/**
	 * gibt das Hintergrundbild zurück
	 * @return gibt das Hintergrundbild zurück
	 */
	public final BufferedImage getBackground() {
		return this.iBackground;
	}

	/**
	 * setzt das Hintergrundbild auf das übergebene Bild
	 * @param iBackground : Hintergrundbild
	 */
	public final void setBackground(BufferedImage iBackground) {
		this.iBackground = iBackground;
	}
	
	public void makeBackground(int width, int height) {
		this.iBackground = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = this.iBackground.createGraphics();
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		
		g.dispose();
	}

	/**
	 * wird aufgerufen, wenn eine Tastaturtaste gedrückt wird<br />
	 * @param button : KeyEventKonstante für die Variable
	 * @param character : Character der KeyEventKostante
	 */
	public void keyButtonPressed(int button, char character) {
		
	}
	
	/**
	 * wird aufgerufen, wenn eine Tastaturtaste losgelassen wird<br />
	 * @param button : KeyEventKonstante für die Variable
	 * @param character : Character der KeyEventKostante
	 */
	public abstract void keyButtonReleased(int button, char character);
	
	/**
	 * wird aufgerufen, wenn ein Button gedrückt wurde
	 * @param function : Funktion, die der Button ausführen soll und ihn einzigartig macht
	 */
	public abstract void mouseButtonFunction(String function);
	
	/**
	 * wird aufgerufen, wenn die Maus bewegt wurde
	 * @param x : X-Wert der Maus (im Frame gesehen)
	 * @param y : Y-Wert der Maus (im Frame gesehen)
	 * @return unrelevant
	 */
	public boolean mouseMoved(int x, int y) {
		return false;
	}
	
	/**
	 * wird aufgerufen, wenn die Maus bewegt wurde und dabei eine Maustaste gedrückt gehalten wird
	 * @param x : X-Wert der Maus (im Frame gesehen)
	 * @param y : Y-Wert der Maus (im Frame gesehen)
	 * @param bRight : TRUE, rechte Maustaste, ansonsten FALSE
	 * @return unrelevant
	 */
	public boolean mouseDragged(int x, int y, boolean bRight) {
		return false;
	}
	
	/**
	 * wird aufgerufen, wenn eine Maustaste gedrückt wird
	 * @param x : X-Wert der Maus (im Frame gesehen)
	 * @param y : Y-Wert der Maus (im Frame gesehen)
	 * @param bRight : TRUE, rechte Maustaste, ansonsten FALSE
	 * @return unrelevant
	 */
	public boolean mousePressed(int x, int y, boolean bRight) {
		return false;
	}

	/**
	 * wird aufgerufen, wenn eine Maustaste losgelassen wurde
	 * @param x : X-Wert der Maus (im Frame gesehen)
	 * @param y : Y-Wert der Maus (im Frame gesehen)
	 * @param bRight : TRUE, rechte Maustaste, ansonsten FALSE
	 * @return unrelevant
	 */
	public abstract void mouseReleased(int x, int y, boolean bRight);
	
	/**
	 * die eigentliche Logik der Klasse passiert in dieser Methode<br />
	 * Sie wird alle delta-Millisekunden aufgerufen
	 * @param delta : Millisekunden, die vergangen sind, seit dem letzten Aufruf
	 */
	public abstract void think(long delta);
	
	/**
	 * Alles was gemalt werden soll, passiert in dieser Methode
	 * @param g : Das Graphicsobjekt auf weclchem gezeichnet wird
	 */
	public abstract void render(Graphics2D g);
	
}
