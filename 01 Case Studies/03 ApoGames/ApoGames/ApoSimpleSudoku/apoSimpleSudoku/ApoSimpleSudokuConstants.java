package apoSimpleSudoku;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 * Klasse, die alle Konstanten des Spiels beinhaltet<br />
 * Au�erdem stellt sie Methoden zum Laden, Auslesen und Speichern zur Verf�gung<br />
 * @author Dirk Aporius
 *
 */
public class ApoSimpleSudokuConstants {
	
	/** Kostante die angibt wie oft pro Sekunde nachgedacht werden soll */
	public static final int FPS_THINK = 50;
	/** Kostante die angibt wie oft pro Sekunde gezeichnet werden soll */
	public static int FPS_RENDER = 100;
	/** Kostante die angibt, wie gro� ein Tile ist */
	public static int TILE_SIZE = 64;
	/** gibt die Breite des Spiels zur�ck */
	public static int GAME_WIDTH = 640;
	/** gibt die H�he des Spiels zur�ck */
	public static int GAME_HEIGHT = 480;

	public static final Color BROWN = new Color(85, 5, 5);
	
	public static final String PROGRAM_URL = "http://www.apo-games.de/apoSudoku/";
	public static final String COOKIE_NAME = "apoSudoku_level";
	
	/** Fontkostante f�r die Buttons im Menu */
	public static final Font FONT_BUTTON_MENU = new Font(Font.SANS_SERIF, Font.BOLD, 25);
	/** Fontkostante f�r die Buttons zur�ck ins Menu */
	public static final Font FONT_BUTTON_BACK = new Font(Font.SANS_SERIF, Font.BOLD, 18);
	/** Fontkostante f�r die Zeitanzeige */
	public static final Font FONT_LEVEL_FPS = new Font(Font.MONOSPACED, Font.BOLD, 10);
	/** Fontkostante f�r die Button im LevelChooser */
	public static final Font FONT_LEVEL_CHOOSER = new Font(Font.SANS_SERIF, Font.BOLD, 20);
	/** Fontkostante f�r die Button im LevelChooser */
	public static final Font FONT_INGAME = new Font(Font.SANS_SERIF, Font.BOLD, 35);

	/** boolean Array f�r das Spielmenu, welcher die Sichtbarkeit der Buttons regelt */
	public static final boolean[] BUTTON_MENU = new boolean[] {true, true, true, false, true, false, false, false, false, false, false, false};
	/** boolean Array f�r das eigentliche Spiel, welcher die Sichtbarkeit der Buttons regelt */
	public static final boolean[] BUTTON_GAME = new boolean[] {false, false, false, false, false, true, false, false, true, false, false, false};
	/** boolean Array f�r das Creditspanel, welcher die Sichtbarkeit der Buttons regelt */
	public static final boolean[] BUTTON_CREDITS = new boolean[] {false, false, false, false, false, false, true, false, false, false, false, false};
	/** boolean Array f�r das Optionspanel, welcher die Sichtbarkeit der Buttons regelt */
	public static final boolean[] BUTTON_OPTIONS = new boolean[] {false, false, false, false, false, false, false, true, false, false, false, false};
	/** boolean Array f�r das Optionspanel, welcher die Sichtbarkeit der Buttons regelt */
	public static final boolean[] BUTTON_LEVEL_CHOOSER = new boolean[] {false, false, false, false, false, false, false, false, false, true, false, false};
	/** boolean Array f�r das Optionspanel, welcher die Sichtbarkeit der Buttons regelt */
	public static final boolean[] BUTTON_HELP = new boolean[] {false, false, false, false, false, false, false, false, false, false, false, true};


	public static final float VERSION = 0.1f;
	
	public static final String PROGRAM_NAME = "=== ApoSimpleSudoku ===";
	
	public static final boolean BUFFER_STRATEGY = false;
	
	public static boolean FPS = false;
	
	/**
	 * rendert einen String 2 mal mit unterschiedlichen Farben (Schwarz und Wei�) verschoben um einen Pixel nach unten und rechts
	 * @param g : Grafikobjekt auf welchem gezeichnet wird
	 * @param s : Der String der gezeichnet werden soll
	 * @param x : Die x-Position an die gezeichnet werden soll
	 * @param y : Die y-Position an die gezeichnet werden soll
	 * @param bLeft : BooleanVariable die angibt, ob der String zentriert werden soll oder links beginnt (TRUE mittig malen, FALSE an X-Position malen
	 */
	public static final void drawString(Graphics2D g, String s, float x, float y, boolean bLeft) {
		ApoSimpleSudokuConstants.drawString(g, s, x, y, bLeft, Color.BLACK, Color.WHITE);
	}
	

	/**
	 * rendert einen String 2 mal mit unterschiedlichen Farben verschoben um einen Pixel nach unten und rechts
	 * @param g : Grafikobjekt auf welchem gezeichnet wird
	 * @param s : Der String der gezeichnet werden soll
	 * @param x : Die x-Position an die gezeichnet werden soll
	 * @param y : Die y-Position an die gezeichnet werden soll
	 * @param bLeft : BooleanVariable die angibt, ob der String zentriert werden soll oder links beginnt (TRUE mittig malen, FALSE an X-Position malen
	 * @param foreground : Farbe f�r den Vordergrund
	 * @param background : Farbe f�r den Hintergrund
	 */
	public static final void drawString(Graphics2D g, String s, float x, float y, boolean bLeft, Color foreground, Color background) {
		int w = 0;
		if (bLeft) {
			w = g.getFontMetrics().stringWidth(s);
		}
		g.setColor(background);
		g.drawString(s, x - w/2 + 1, y + 1);
		g.setColor(foreground);
		g.drawString(s, x - w/2, y);		
	}
}