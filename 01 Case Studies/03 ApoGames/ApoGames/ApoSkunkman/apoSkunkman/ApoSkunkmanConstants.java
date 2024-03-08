package apoSkunkman;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apogames.ApoConstants;

/**
 * Klasse, die alle Konstanten des Spiels beinhaltet<br />
 * Außerdem stellt sie Methoden zum Laden, Auslesen und Speichern zur Verfügung<br />
 * @author Dirk Aporius
 *
 */
public class ApoSkunkmanConstants {
	
	/** Kostante die angibt wie oft pro Sekunde nachgedacht werden soll */
	public static final int FPS_THINK = 50;
	/** Kostante die angibt wie oft pro Sekunde gezeichnet werden soll */
	public static int FPS_RENDER = 100;
	/** Kostante die angibt, wie groß ein Tile ist */
	public static int TILE_SIZE = 32;
	/** Konstante die angibt, um wieviel Pixel das Level in y-Richtung verschoben wird bei der Anzeige */
	public static int CHANGE_Y_LEVEL = 28 + ApoSkunkmanConstants.TILE_SIZE;
	/** gibt die Breite des Spiels zurück */
	public static int GAME_WIDTH = 800;
	/** gibt die Höhe des Spiels zurück */
	public static int GAME_HEIGHT = 600;
	/** gibt die Größe der Application wieder<br />
	 * bei 1 startet das Spiel in 400 x 300<br />
	 * und bei 2 startet das Spiel in 800 x 600<br /> */
	public static int APPLICATION_SIZE = 2;
	/** Taste, um den Spieler eins nach links zu bewegen */
	public static int PLAYER_ONE_LEFT = KeyEvent.VK_LEFT;
	/** Taste, um den Spieler eins nach rechts zu bewegen */
	public static int PLAYER_ONE_RIGHT = KeyEvent.VK_RIGHT;
	/** Taste, um den Spieler eins nach oben zu bewegen */
	public static int PLAYER_ONE_UP = KeyEvent.VK_UP;
	/** Taste, um den Spieler eins nach unten zu bewegen */
	public static int PLAYER_ONE_DOWN = KeyEvent.VK_DOWN;
	/** Taste, um den Spieler eins einen Skunkman legen zu lassen */
	public static int PLAYER_ONE_LAY = KeyEvent.VK_CONTROL;
	/** Taste, um den Spieler zwei nach links zu bewegen */
	public static int PLAYER_TWO_LEFT = KeyEvent.VK_A;
	/** Taste, um den Spieler zwei nach rechts zu bewegen */
	public static int PLAYER_TWO_RIGHT = KeyEvent.VK_D;
	/** Taste, um den Spieler zwei nach oben zu bewegen */
	public static int PLAYER_TWO_UP = KeyEvent.VK_W;
	/** Taste, um den Spieler zwei nach unten zu bewegen */
	public static int PLAYER_TWO_DOWN = KeyEvent.VK_S;
	/** Taste, um den Spieler zwei einen Skunkman legen zu lassen */
	public static int PLAYER_TWO_LAY = KeyEvent.VK_SHIFT;
	/** Taste, um den Spieler zwei einen Skunkman legen zu lassen */
	public static String[] LEVEL_TILESETS = new String[] {
		"SKUNK", "ASCII", "ANTJE"
	};
	
	private static final String PROP_SIZE = "size";
	private static final String PROP_DEBUG = "debug";
	private static final String PROP_FPS = "fps";
	private static final String PROP_LOAD_EXTERN_IMAGE = "imageLoadExtern";
	private static final String PROP_AI_ONE = "aiOne";
	private static final String PROP_AI_TWO = "aiTwo";
	private static final String PROP_AI_THREE = "aiThree";
	private static final String PROP_AI_FOUR = "aiFour";
	private static final String PROP_FPS_COUNT = "maxFPS";
	private static final String PROP_BUFFER_STRATEGY = "bufferStrategy";
	
	private static final String PROP_PLAYER_ONE_KEYLEFT = "playerOneKeyLeft";
	private static final String PROP_PLAYER_ONE_KEYRIGHT = "playerOneKeyRight";
	private static final String PROP_PLAYER_ONE_KEYUP = "playerOneKeyUp";
	private static final String PROP_PLAYER_ONE_KEYDOWN = "playerOneKeyDown";
	private static final String PROP_PLAYER_ONE_KEYLAY = "playerOneKeyLay";
	
	private static final String PROP_PLAYER_TWO_KEYLEFT = "playerTwoKeyLeft";
	private static final String PROP_PLAYER_TWO_KEYRIGHT = "playerTwoKeyRight";
	private static final String PROP_PLAYER_TWO_KEYUP = "playerTwoKeyUp";
	private static final String PROP_PLAYER_TWO_KEYDOWN = "playerTwoKeyDown";
	private static final String PROP_PLAYER_TWO_KEYLAY = "playerTwoKeyLay";

	private static final String PROP_LEVEL_PLAYERCOUNT = "levelPlayerCount";
	private static final String PROP_LEVEL_BUSH = "levelBush";
	private static final String PROP_LEVEL_TYPE = "levelType";
	private static final String PROP_LEVEL_TIME = "levelTime";
	private static final String PROP_LEVEL_RANDOM = "levelRandom";
	private static final String PROP_LEVEL_TILESET = "levelTileset";
	
	/** Objekt mit den Properties */
	private static Object props;
	/** Name der Properties Datei */
	private static final String SKUNKMAN_PROPERTIES = "skunkman.properties";
	/** Boolean Variable die angibt, ob das Spiel sich gerade im Debugmodus befindet oder nicht */
	public static boolean DEBUG = false;
	/** Boolean Variable die angibt, ob das Spiel die FPS anzeigen soll oder nicht */
	public static boolean FPS = false;
	/** Boolean Variable die angibt, ob die Bilder für das Spiel extern geladen werden sollen */
	public static boolean LOAD_EXTERN = false;
	/** String Variable die angibt, welchen Pfad die KI für Spieler eins besitzt */
	public static String AI_ONE = "";
	/** String Variable die angibt, welchen Pfad die KI für Spieler zwei besitzt */
	public static String AI_TWO = "";
	/** String Variable die angibt, welchen Pfad die KI für Spieler drei besitzt */
	public static String AI_THREE = "";
	/** String Variable die angibt, welchen Pfad die KI für Spieler vier besitzt */
	public static String AI_FOUR = "";
	/** Integer Variable die angibt, wieviel FPS gezeichnet werden sollen */
	public static int FPS_COUNT = -1;
	/** Boolean Variable die angibt, ob mit oder ohne Buffer Strategy gezeichnet werden soll */
	public static boolean BUFFER_STRATEGY = false;
	/** wieviel Spieler sollen am Anfang eingestellt sein */
	public static int LEVEL_PLAYERS = 4;
	/** boolean Variable die angibt, ob ein Bush angeschaltet ist oder nicht */
	public static boolean LEVEL_BUSH = true;
	/** Integer Variable die angibt, welcher Leveltyp als letztes ausgewählt war */
	public static int LEVEL_TYPE = 0;
	/** Integer Variable die angibt, wieviel Zeit das letzte Mal im Level zur Verfügung stand */
	public static int LEVEL_LASTTIME = 150000;
	/** Integer Variable die angibt, wie die letzte Zufallsvariable war */
	public static long LEVEL_LASTRANDOM = -1;
	/** String Variable die angibt, welches Tileset verwendet werden soll */
	public static String LEVEL_TILESET = "ANTJE";
	
	/**
	 * gleich am Anfang laden der Properties
	 */
	static {
		ApoSkunkmanConstants.loadProperties();
	}
	
	/**
	 * Methode zum Speichern der Properties und deren Variablen
	 */
	public static void saveProperties() {
		if (!ApoConstants.B_APPLET) {
			try {
				if (ApoSkunkmanConstants.props != null) {
					Properties props = (Properties)(ApoSkunkmanConstants.props);
					props.setProperty(ApoSkunkmanConstants.PROP_SIZE, String.valueOf(ApoSkunkmanConstants.APPLICATION_SIZE));
					props.setProperty(ApoSkunkmanConstants.PROP_DEBUG, String.valueOf(ApoSkunkmanConstants.DEBUG));
					props.setProperty(ApoSkunkmanConstants.PROP_FPS, String.valueOf(ApoSkunkmanConstants.FPS));
					props.setProperty(ApoSkunkmanConstants.PROP_LOAD_EXTERN_IMAGE, String.valueOf(ApoSkunkmanConstants.LOAD_EXTERN));
					props.setProperty(ApoSkunkmanConstants.PROP_AI_ONE, String.valueOf(ApoSkunkmanConstants.AI_ONE));
					props.setProperty(ApoSkunkmanConstants.PROP_AI_TWO, String.valueOf(ApoSkunkmanConstants.AI_TWO));
					props.setProperty(ApoSkunkmanConstants.PROP_AI_THREE, String.valueOf(ApoSkunkmanConstants.AI_THREE));
					props.setProperty(ApoSkunkmanConstants.PROP_AI_FOUR, String.valueOf(ApoSkunkmanConstants.AI_FOUR));
					props.setProperty(ApoSkunkmanConstants.PROP_FPS_COUNT, String.valueOf(ApoSkunkmanConstants.FPS_COUNT));
					props.setProperty(ApoSkunkmanConstants.PROP_BUFFER_STRATEGY, String.valueOf(ApoSkunkmanConstants.BUFFER_STRATEGY));
					
					props.setProperty(ApoSkunkmanConstants.PROP_PLAYER_ONE_KEYLEFT, String.valueOf(ApoSkunkmanConstants.PLAYER_ONE_LEFT));
					props.setProperty(ApoSkunkmanConstants.PROP_PLAYER_ONE_KEYRIGHT, String.valueOf(ApoSkunkmanConstants.PLAYER_ONE_RIGHT));
					props.setProperty(ApoSkunkmanConstants.PROP_PLAYER_ONE_KEYUP, String.valueOf(ApoSkunkmanConstants.PLAYER_ONE_UP));
					props.setProperty(ApoSkunkmanConstants.PROP_PLAYER_ONE_KEYDOWN, String.valueOf(ApoSkunkmanConstants.PLAYER_ONE_DOWN));
					props.setProperty(ApoSkunkmanConstants.PROP_PLAYER_ONE_KEYLAY, String.valueOf(ApoSkunkmanConstants.PLAYER_ONE_LAY));
					props.setProperty(ApoSkunkmanConstants.PROP_PLAYER_TWO_KEYLEFT, String.valueOf(ApoSkunkmanConstants.PLAYER_TWO_LEFT));
					props.setProperty(ApoSkunkmanConstants.PROP_PLAYER_TWO_KEYRIGHT, String.valueOf(ApoSkunkmanConstants.PLAYER_TWO_RIGHT));
					props.setProperty(ApoSkunkmanConstants.PROP_PLAYER_TWO_KEYUP, String.valueOf(ApoSkunkmanConstants.PLAYER_TWO_UP));
					props.setProperty(ApoSkunkmanConstants.PROP_PLAYER_TWO_KEYDOWN, String.valueOf(ApoSkunkmanConstants.PLAYER_TWO_DOWN));
					props.setProperty(ApoSkunkmanConstants.PROP_PLAYER_TWO_KEYLAY, String.valueOf(ApoSkunkmanConstants.PLAYER_TWO_LAY));

					props.setProperty(ApoSkunkmanConstants.PROP_LEVEL_BUSH, String.valueOf(ApoSkunkmanConstants.LEVEL_BUSH));
					props.setProperty(ApoSkunkmanConstants.PROP_LEVEL_PLAYERCOUNT, String.valueOf(ApoSkunkmanConstants.LEVEL_PLAYERS));
					props.setProperty(ApoSkunkmanConstants.PROP_LEVEL_TIME, String.valueOf(ApoSkunkmanConstants.LEVEL_LASTTIME));
					props.setProperty(ApoSkunkmanConstants.PROP_LEVEL_TYPE, String.valueOf(ApoSkunkmanConstants.LEVEL_TYPE));
					props.setProperty(ApoSkunkmanConstants.PROP_LEVEL_RANDOM, String.valueOf(ApoSkunkmanConstants.LEVEL_LASTRANDOM));
					props.setProperty(ApoSkunkmanConstants.PROP_LEVEL_TILESET, String.valueOf(ApoSkunkmanConstants.LEVEL_TILESET));
					
					FileOutputStream out = new FileOutputStream(ApoSkunkmanConstants.SKUNKMAN_PROPERTIES);
					props.store(out, null);
					out.close();
				}
			} catch (IOException e) {
			} finally {
			}
		}
	}
	
	/**
	 * Methode zum Laden und Setzten der Properties und deren Variablen
	 */
	public static void loadProperties() {
		if (!ApoConstants.B_APPLET) {
			Properties props = new Properties();
			try {
				props.load(new FileInputStream(ApoSkunkmanConstants.SKUNKMAN_PROPERTIES));
			} catch (IOException ex) {
				System.out.println("exception: can not load properties");
			}
			String playerOneLeft = props.getProperty(ApoSkunkmanConstants.PROP_PLAYER_ONE_KEYLEFT, String.valueOf(ApoSkunkmanConstants.PLAYER_ONE_LEFT));
			try {
				ApoSkunkmanConstants.PLAYER_ONE_LEFT = Integer.valueOf(playerOneLeft);
			} catch (NumberFormatException ex) {
				ApoSkunkmanConstants.PLAYER_ONE_LEFT = KeyEvent.VK_LEFT;
			}
			String playerOneRight = props.getProperty(ApoSkunkmanConstants.PROP_PLAYER_ONE_KEYRIGHT, String.valueOf(ApoSkunkmanConstants.PLAYER_ONE_RIGHT));
			try {
				ApoSkunkmanConstants.PLAYER_ONE_RIGHT = Integer.valueOf(playerOneRight);
			} catch (NumberFormatException ex) {
				ApoSkunkmanConstants.PLAYER_ONE_RIGHT = KeyEvent.VK_RIGHT;
			}
			String playerOneUp = props.getProperty(ApoSkunkmanConstants.PROP_PLAYER_ONE_KEYUP, String.valueOf(ApoSkunkmanConstants.PLAYER_ONE_UP));
			try {
				ApoSkunkmanConstants.PLAYER_ONE_UP = Integer.valueOf(playerOneUp);
			} catch (NumberFormatException ex) {
				ApoSkunkmanConstants.PLAYER_ONE_UP = KeyEvent.VK_UP;
			}
			String playerOneDown = props.getProperty(ApoSkunkmanConstants.PROP_PLAYER_ONE_KEYDOWN, String.valueOf(ApoSkunkmanConstants.PLAYER_ONE_DOWN));
			try {
				ApoSkunkmanConstants.PLAYER_ONE_DOWN = Integer.valueOf(playerOneDown);
			} catch (NumberFormatException ex) {
				ApoSkunkmanConstants.PLAYER_ONE_DOWN = KeyEvent.VK_DOWN;
			}
			String playerOneLay = props.getProperty(ApoSkunkmanConstants.PROP_PLAYER_ONE_KEYLAY, String.valueOf(ApoSkunkmanConstants.PLAYER_ONE_LAY));
			try {
				ApoSkunkmanConstants.PLAYER_ONE_LAY = Integer.valueOf(playerOneLay);
			} catch (NumberFormatException ex) {
				ApoSkunkmanConstants.PLAYER_ONE_LAY = KeyEvent.VK_CONTROL;
			}
			
			String playerTwoLeft = props.getProperty(ApoSkunkmanConstants.PROP_PLAYER_TWO_KEYLEFT, String.valueOf(ApoSkunkmanConstants.PLAYER_TWO_LEFT));
			try {
				ApoSkunkmanConstants.PLAYER_TWO_LEFT = Integer.valueOf(playerTwoLeft);
			} catch (NumberFormatException ex) {
				ApoSkunkmanConstants.PLAYER_TWO_LEFT = KeyEvent.VK_A;
			}
			String playerTwoRight = props.getProperty(ApoSkunkmanConstants.PROP_PLAYER_TWO_KEYRIGHT, String.valueOf(ApoSkunkmanConstants.PLAYER_TWO_RIGHT));
			try {
				ApoSkunkmanConstants.PLAYER_TWO_RIGHT = Integer.valueOf(playerTwoRight);
			} catch (NumberFormatException ex) {
				ApoSkunkmanConstants.PLAYER_TWO_RIGHT = KeyEvent.VK_D;
			}
			String playerTwoUp = props.getProperty(ApoSkunkmanConstants.PROP_PLAYER_TWO_KEYUP, String.valueOf(ApoSkunkmanConstants.PLAYER_TWO_UP));
			try {
				ApoSkunkmanConstants.PLAYER_TWO_UP = Integer.valueOf(playerTwoUp);
			} catch (NumberFormatException ex) {
				ApoSkunkmanConstants.PLAYER_TWO_UP = KeyEvent.VK_W;
			}
			String playerTwoDown = props.getProperty(ApoSkunkmanConstants.PROP_PLAYER_TWO_KEYDOWN, String.valueOf(ApoSkunkmanConstants.PLAYER_TWO_DOWN));
			try {
				ApoSkunkmanConstants.PLAYER_TWO_DOWN = Integer.valueOf(playerTwoDown);
			} catch (NumberFormatException ex) {
				ApoSkunkmanConstants.PLAYER_TWO_DOWN = KeyEvent.VK_S;
			}
			String playerTwoLay = props.getProperty(ApoSkunkmanConstants.PROP_PLAYER_TWO_KEYLAY, String.valueOf(ApoSkunkmanConstants.PLAYER_TWO_LAY));
			try {
				ApoSkunkmanConstants.PLAYER_TWO_LAY = Integer.valueOf(playerTwoLay);
			} catch (NumberFormatException ex) {
				ApoSkunkmanConstants.PLAYER_TWO_LAY = KeyEvent.VK_SHIFT;
			}
			
			
			String size = props.getProperty(ApoSkunkmanConstants.PROP_SIZE, "2");
			try {
				ApoSkunkmanConstants.APPLICATION_SIZE = Integer.valueOf(size);
			} catch (NumberFormatException ex) {
				ApoSkunkmanConstants.APPLICATION_SIZE = 2;
			}
			if (ApoSkunkmanConstants.APPLICATION_SIZE < 1) {
				ApoSkunkmanConstants.APPLICATION_SIZE = 1;
			} else if (ApoSkunkmanConstants.APPLICATION_SIZE > 2) {
				ApoSkunkmanConstants.APPLICATION_SIZE = 2;
			}
			ApoSkunkmanConstants.TILE_SIZE = 16 * ApoSkunkmanConstants.APPLICATION_SIZE;
			ApoSkunkmanConstants.CHANGE_Y_LEVEL = 14*ApoSkunkmanConstants.APPLICATION_SIZE + ApoSkunkmanConstants.TILE_SIZE;
			ApoSkunkmanConstants.GAME_WIDTH = 400 * ApoSkunkmanConstants.APPLICATION_SIZE;
			ApoSkunkmanConstants.GAME_HEIGHT = 300 * ApoSkunkmanConstants.APPLICATION_SIZE;
			
			String debug = props.getProperty(ApoSkunkmanConstants.PROP_DEBUG, "false");
			try {
				ApoSkunkmanConstants.DEBUG = Boolean.valueOf(debug);
			} catch (Exception ex) {
				ApoSkunkmanConstants.DEBUG = false;
			}
			String myFPS = props.getProperty(ApoSkunkmanConstants.PROP_FPS, "false");
			try {
				ApoSkunkmanConstants.FPS = Boolean.valueOf(myFPS);
			} catch (Exception ex) {
				ApoSkunkmanConstants.FPS = false;
			}
			String myImageLoad = props.getProperty(ApoSkunkmanConstants.PROP_LOAD_EXTERN_IMAGE, "false");
			try {
				ApoSkunkmanConstants.LOAD_EXTERN = Boolean.valueOf(myImageLoad);
			} catch (Exception ex) {
				ApoSkunkmanConstants.LOAD_EXTERN = false;
			}
			ApoSkunkmanConstants.AI_ONE = props.getProperty(ApoSkunkmanConstants.PROP_AI_ONE, "");
			ApoSkunkmanConstants.AI_TWO = props.getProperty(ApoSkunkmanConstants.PROP_AI_TWO, "");
			ApoSkunkmanConstants.AI_THREE = props.getProperty(ApoSkunkmanConstants.PROP_AI_THREE, "");
			ApoSkunkmanConstants.AI_FOUR = props.getProperty(ApoSkunkmanConstants.PROP_AI_FOUR, "");
			
			String fpsCount = props.getProperty(ApoSkunkmanConstants.PROP_FPS_COUNT, "100");
			try {
				ApoSkunkmanConstants.FPS_COUNT = Integer.valueOf(fpsCount);
			} catch (NumberFormatException ex) {
				ApoSkunkmanConstants.FPS_COUNT = 100;
			}
			if (ApoSkunkmanConstants.FPS_COUNT > 300) {
				ApoSkunkmanConstants.FPS_COUNT = 300;
			} else if (ApoSkunkmanConstants.FPS_COUNT < 10) {
				ApoSkunkmanConstants.FPS_COUNT = 10;
			}
			ApoSkunkmanConstants.setWaitTimeRender();
			
			String bufferStrategy = props.getProperty(PROP_BUFFER_STRATEGY, "false");
			try {
				ApoSkunkmanConstants.BUFFER_STRATEGY = Boolean.valueOf(bufferStrategy);
			} catch (Exception ex) {
				ApoSkunkmanConstants.BUFFER_STRATEGY = false;
			}
			
			String levelBushes = props.getProperty(PROP_LEVEL_BUSH, "true");
			try {
				ApoSkunkmanConstants.LEVEL_BUSH = Boolean.valueOf(levelBushes);
			} catch (Exception ex) {
				ApoSkunkmanConstants.LEVEL_BUSH = true;
			}
			String levelTime = props.getProperty(ApoSkunkmanConstants.PROP_LEVEL_TIME, String.valueOf(ApoSkunkmanConstants.LEVEL_LASTTIME));
			try {
				ApoSkunkmanConstants.LEVEL_LASTTIME = Integer.valueOf(levelTime);
			} catch (NumberFormatException ex) {
				ApoSkunkmanConstants.LEVEL_LASTTIME = 150000;
			}
			String levelType = props.getProperty(ApoSkunkmanConstants.PROP_LEVEL_TYPE, String.valueOf(ApoSkunkmanConstants.LEVEL_TYPE));
			try {
				ApoSkunkmanConstants.LEVEL_TYPE = Integer.valueOf(levelType);
			} catch (NumberFormatException ex) {
				ApoSkunkmanConstants.LEVEL_TYPE = 0;
			}
			
			String levelPlayerCount = props.getProperty(ApoSkunkmanConstants.PROP_LEVEL_PLAYERCOUNT, String.valueOf(ApoSkunkmanConstants.LEVEL_PLAYERS));
			try {
				ApoSkunkmanConstants.LEVEL_PLAYERS = Integer.valueOf(levelPlayerCount);
			} catch (NumberFormatException ex) {
				ApoSkunkmanConstants.LEVEL_PLAYERS = 4;
			}
			String levelRandom = props.getProperty(ApoSkunkmanConstants.PROP_LEVEL_RANDOM, String.valueOf(ApoSkunkmanConstants.LEVEL_LASTRANDOM));
			try {
				ApoSkunkmanConstants.LEVEL_LASTRANDOM = Long.valueOf(levelRandom);
			} catch (NumberFormatException ex) {
				ApoSkunkmanConstants.LEVEL_LASTRANDOM = -1;
			}
			ApoSkunkmanConstants.LEVEL_TILESET = props.getProperty(ApoSkunkmanConstants.PROP_LEVEL_TILESET, ApoSkunkmanConstants.LEVEL_TILESETS[2]);
			
			ApoSkunkmanConstants.props = props;
		}
	}
	
	/**
	 * statische Methode um die Konstanten für das Neuzeichnen zu setzen
	 */
	private static void setWaitTimeRender() {
		ApoSkunkmanConstants.FPS_RENDER = ApoSkunkmanConstants.FPS_COUNT;
		ApoSkunkmanConstants.WAIT_TIME_RENDER = (int)(1000.0 / (double)ApoSkunkmanConstants.FPS_RENDER);
	}
	/** Konstante die angibt, aller wieviel Millisekunden die Render-Methode aufgerufen werden soll */
	public static int WAIT_TIME_RENDER = (int)(1000.0 / (double)FPS_RENDER);
	/** Konstante die angibt, aller wieviel Millisekunden die Think-Methode aufgerufen werden soll */
	public static final int WAIT_TIME_THINK = (int)(1000.0 / (double)FPS_THINK);
	/** Konstante die angibt, wielange Millisekunden die Spieler maximal nachdenken dürfen. Wenn sie länger benötigen, werfen sie eine Exception und sterben */
	public static final int MAX_TIME_THINK = ApoSkunkmanConstants.WAIT_TIME_THINK;
	
	/** Konstante die angibt, wie das Programm heißt */
	public static final String PROGRAM_NAME = "=== ApoSkunkman ===";
	/** Konstante die angibt, um welche Version es sich bei dem Spiel handelt */
	public static final String VERSION = "1.01";

	/** Konstante die angibt, dass eine KI als Mensch gehandelt werden sollte */
	public static final int AI_HUMAN = 0;
	/** Konstante die angibt, dass die runner KI geladen werden soll */
	public static final int AI_RUNNER = 1;
	/** Konstante die angibt, dass die stinky KI geladen werden soll */
	public static final int AI_STINKY = 2;
	/** Konstante die angibt, dass die leftright KI geladen werden soll */
	public static final int AI_LEFTRIGHT = 3;

	/** Konstante die angibt, um wieviel Zeit in Millisekunden sich die Levelzeit verändert, wenn man die Zeit umstellt */
	public static final int TIME_CHANGE = 5000;
	/** Konstante die angibt, wieviel Zeit für ein Level minimal zur Verfügung stehen kann */
	public static final int TIME_MIN = 5000;
	/** Konstante die angibt, wieviel Zeit für ein Level maximal zur Verfügung stehen kann */
	public static final int TIME_MAX = 595000;
	
	/** Konstante die angibt, wie breit ein Level ist */
	public static final int LEVEL_WIDTH = 15;
	/** Konstante die angibt, wie hoch ein Level ist */
	public static final int LEVEL_HEIGHT = 15;
	
	/** Konstante die den Leveltypen "Standard" angibt */
	public static final int LEVEL_TYPE_STANDARD = 0;
	/** Konstante die angibt, wieviel Zeit ein Standardlevel am Anfang hat */
	public static final int LEVEL_TIME_STANDARD = 150000;
	/** Konstante die angibt, wieviel Zeit vergehen muss, damit ein neuer Stein bei Ablauf der richtigen Levelzeit entsteht */
	public static final int LEVEL_FILL_TIME = 400;
	/** Konstante die angibt, zu wieviel Prozent bei einem leeren Feld ein Busch entstehen soll */
	public static final int LEVEL_BUSH_GENERATION_STANDARD = 80;
	/** Konstante die angibt, zu wieviel Prozent ein Busch ein Goodie enthalten soll */
	public static final int LEVEL_GOODIE_GENERATION_STANDARD = 40;
	/** Konstante die den Leveltypen "GoalX" angibt */
	public static final int LEVEL_TYPE_GOAL_X = 1;
	/** Konstante die angibt, wieviel Zeit ein GoalXlevel am Anfang hat */
	public static final int LEVEL_TIME_GOAL_X = 150000;
	/** Konstante die den Leveltypen "Editor" angibt */
	public static final int LEVEL_TYPE_EDITOR = 2;	
	/** Konstante die angibt, wieviel Zeit vergehen muss, damit ein Spieler, welcher sichtbar ist, einen Punkt erhält */
	public static final int PLAYER_TIME_TO_NEXT_POINTS = 250;
	/** Konstante die angibt, wieviel Punkte der Spieler erhält, der das Spiel gewinnt */
	public static final int PLAYER_WINNING_POINTS = 3000;
	/** Konstante die angibt, wieviel Punkte der Spieler erhält, wenn er eine Exception wirft */
	public static final int PLAYER_EXCEPTION_POINTS = -100000;
	/** Konstante die angibt, wieviel Spieler maximal an einem Spiel mitspielen dürfen */
	public static int PLAYER_MAX_PLAYER = 4;
	/** Konstante die den Leveltypen "Standard" mit anderen Steinkonstellationen angibt */
	public static final int LEVEL_TYPE_STANDARD_SECOND = 3;
	/** Konstante die den Leveltypen "Standard" mit anderen Steinkonstellationen angibt */
	public static final int LEVEL_TYPE_STANDARD_THIRD = 4;
	/** Konstante für den Leveltypen "Prim" mit dem Ziel in der Mitte und einigen kleinen zufälligen Steinkostellationen */
	public static final int LEVEL_TYPE_PRIM = 5;
	/** Konstante für den Leveltypen "DeadEnd" mit kleinen Sackgassen */
	public static final int LEVEL_TYPE_DEADEND = 6;
	/** Konstante für den Leveltypen "Little" mit kleinerem Spielfeld */
	public static final int LEVEL_TYPE_LITTLE = 7;
	/** Konstante für den Leveltypen "Easy" mit keinen Hindernissen und einem Ziel */
	public static final int LEVEL_TYPE_EASY = 8;
	
	/** Konstante die angibt, mit wievielen Skunkmans der Spieler in ein Spiel startet */
	public static int PLAYER_SKUNKMAN_START_MAX_COUNT = 1;
	
	/** gibt die Richtung "runter" bzw Richtung Süden an */
	public static final int PLAYER_DIRECTION_DOWN = 0;
	/** gibt die Richtung "links" bzw Richtung Westen an */
	public static final int PLAYER_DIRECTION_LEFT = 1;
	/** gibt die Richtung "rechts" bzw Richtung Osten an */
	public static final int PLAYER_DIRECTION_RIGHT = 2;
	/** gibt die Richtung "hoch" bzw Richtung Norden an */
	public static final int PLAYER_DIRECTION_UP = 3;
	
	/** Konstante die angibt, wie der minimale Speed des Spieler pro Millisekunden ist */
	public static final float PLAYER_SPEED_MIN = 0.05f;
	/** Konstante die angibt, wie der maximale Speed des Spieler pro Millisekunden ist */
	public static final float PLAYER_SPEED_MAX = 0.09f;
	/** Konstante die angibt, um welchen Wert sich die Geschwindigkeit des Spieler erhöht oder senkt, wenn er ein Speed Goodie einsammelt */
	public static final float PLAYER_SPEED_INCREASE = 0.005f;
	/** Konstante die angibt, wie lang die minimale Weite der Skunkmans des Spielers werden kann */
	public static final int PLAYER_WIDTH_MIN = 1;
	/** Konstante die angibt, wie lang die maximale Weite der Skunkmans des Spielers werden kann */
	public static final int PLAYER_WIDTH_MAX = ApoSkunkmanConstants.LEVEL_WIDTH;
	
	/** Konstante die angibt, aus wievielen Tiles ein Spieler besteht */
	public static final int PLAYER_TILES = 4;
	/** Konstante die angibt, wieviel Zeit beim Spieler vergehen muss, damit das nächste Frame in der Animation gezeigt werden soll */
	public static final int PLAYER_ANIMATION_TIME = 200;
	
	/** Konstante die angibt, aus wievielen Tiles ein Skunkman besteht */
	public static final int SKUNKMAN_TILES = 3;
	/** Konstante die angibt, wieviel Zeit beim Skunkman vergehen muss damit er explodiert */
	public static final int SKUNKMAN_TIME_TO_EXPLODE = 2700;
	/** Konstante die angibt, wieviel Zeit beim Skunkman vergehen muss damit das nächste Frame in der Animation gezeigt werden soll */
	public static final int SKUNKMAN_ANIMATION_TIME = ApoSkunkmanConstants.SKUNKMAN_TIME_TO_EXPLODE / ApoSkunkmanConstants.SKUNKMAN_TILES;
	/** Konstante die angibt, wieviel Punkte es bringt jemanden mit seinem Skunkman zu verduften */
	public static final int SKUNKMAN_POINTS_ENEMY = 2000;
	/** Konstante die angibt, wieviel Punkte es bringt sich selber mit seinem Skunkman zu verduften */
	public static final int SKUNKMAN_POINTS_OWN = -1000;
	
	/** Konstante die angibt, wie lange das Feuer angezeigt werden soll */
	public static final int FIRE_MAX_SHOWTIME = 400;
	
	/** Konstante die angibt, zu wieviel Prozent ein gutes Goodie entstehen soll */
	public static final int GOODIE_GENERATION_GOOD = 70;
	/** Konstante die angibt, zu wieviel Prozent ein Goodie für die Erhöhung/Senkung der Weite entstehen soll */
	public static final int GOODIE_GENERATION_WIDTH = 32;
	/** Konstante die angibt, zu wieviel Prozent ein Goodie für die Erhöhung/Senkung der maximalen Anzahl der Skunkmans entstehen soll */
	public static final int GOODIE_GENERATION_SKUNKMAN = 65;
	/** Konstante die angibt, zu wieviel Prozent ein Goodie für die Erhöhung/Senkung der Schnelligkeit des Spielers entstehen soll */
	public static final int GOODIE_GENERATION_FAST = 98;
	/** Konstante die angibt, zu wieviel Prozent ein Goodie für die Erhöhung/Senkung der Werte komplett entstehen soll */
	public static final int GOODIE_GENERATION_GOD = 100;
	
	/** Konstante die angibt, wieviel Punkte der Spieler für das Einsammeln eines Goodies erhält */
	public static final int GOODIE_POINTS = 20;
	/** Konstante die angibt, aus wievielen Tiles ein Goodie besteht */
	public static final int GOODIE_TILES = 2;
	/** Konstante die angibt, wieviel Zeit beim Goodie vergehen muss damit das nächste Frame in der Animation gezeigt werden soll */
	public static final int GOODIE_ANIMATION_TIME = 240;
	/** Konstante die angibt, wielange ein Goodie MAXIMAL angezeigt wird, wenn 0 dann verschwindet es */
	public static final int GOODIE_MAX_SHOWTIME = 13000;
	
	public static final String[] GOODIE_STRING = new String[] {
		"no goodie", "add skunkwidth", "add max skunk", "faster player", "maximize all", "decrease skunkwidth", "decrease max skunk", "slower player", "minimize all"
	};
	/** Goodiekonstante für ein Goodie, was kein Goodie ist */
	public static final int GOODIE_EMPTY = 0;
	/** Goodiekonstante für ein Goodie, was die Feuerlänge des Skunkmans um 1 erhöht */
	public static final int GOODIE_GOOD_WIDTH = 1;
	/** Goodiekonstante für ein Goodie, was die maximale Anzahl an Skunkmans um 1 erhöht */
	public static final int GOODIE_GOOD_SKUNKMAN = 2;
	/** Goodiekonstante für ein Goodie, was die Geschwindigkeit des Spielers um ApoSkunkmanConstants.PLAYER_SPEED_INCREASE erhöht */
	public static final int GOODIE_GOOD_FAST = 3;
	/** Goodiekonstante für ein Goodie, was alle Werte des Spieler maximiert */
	public static final int GOODIE_GOOD_GOD = 4;
	/** Goodiekonstante für ein Goodie, was die Feuerlänge des Skunkmans um 1 vermindert */
	public static final int GOODIE_BAD_WIDTH = 5;
	/** Goodiekonstante für ein Goodie, was die maximale Anzahl an Skunkmans um 1 vermindert */
	public static final int GOODIE_BAD_SKUNKMAN = 6;
	/** Goodiekonstante für ein Goodie, was die Geschwindigkeit des Spielers um ApoSkunkmanConstants.PLAYER_SPEED_INCREASE senkt */
	public static final int GOODIE_BAD_FAST = 7;
	/** Goodiekonstante für ein Goodie, was alle Werte des Spieler minimiert */
	public static final int GOODIE_BAD_GOD = 8;	
	
	/** Fontkostante für die Auswertung */
	public static final Font FONT_HUD_ANALYSIS = new Font(Font.SANS_SERIF, Font.PLAIN, 15 * ApoSkunkmanConstants.APPLICATION_SIZE);
	/** Fontkostante um auf dem Baumimage zu malen */
	public static final Font FONT_HUD_TREE = new Font(Font.SANS_SERIF, Font.PLAIN, 10 * ApoSkunkmanConstants.APPLICATION_SIZE);
	/** Fontkostante um auf dem Baumimage zu malen */
	public static final Font FONT_HUD_TREE_ASCII = new Font(Font.MONOSPACED, Font.BOLD, 10 * ApoSkunkmanConstants.APPLICATION_SIZE);
	/** Fontkostante für die Options (dick) */
	public static final Font FONT_OPTIONS_BOLD = new Font(Font.SANS_SERIF, Font.BOLD, 9 * ApoSkunkmanConstants.APPLICATION_SIZE);
	/** Fontkostante für die Options (normal) */
	public static final Font FONT_OPTIONS = new Font(Font.SANS_SERIF, Font.PLAIN, 9 * ApoSkunkmanConstants.APPLICATION_SIZE);
	/** Fontkostante für die Options (dick) */
	public static final Font FONT_OPTIONS_BOLD_ASCII = new Font(Font.MONOSPACED, Font.BOLD, 9 * ApoSkunkmanConstants.APPLICATION_SIZE);
	/** Fontkostante für die Options (normal) */
	public static final Font FONT_OPTIONS_ASCII = new Font(Font.MONOSPACED, Font.PLAIN, 9 * ApoSkunkmanConstants.APPLICATION_SIZE);
	/** Fontkostante für die Anzeige der FPS */
	public static final Font FONT_LEVEL_FPS = new Font(Font.SANS_SERIF, Font.BOLD, 10);
	/** Fontkostante für die Zeitanzeige */
	public static final Font FONT_LEVEL_TIME = new Font(Font.SANS_SERIF, Font.BOLD, 7 * ApoSkunkmanConstants.APPLICATION_SIZE);
	/** Fontkostante für die Zeitanzeige */
	public static final Font FONT_LEVEL_TIME_ASCII = new Font(Font.MONOSPACED, Font.BOLD, 7 * ApoSkunkmanConstants.APPLICATION_SIZE);
	
	/** Konstante die angibt, das ein Spiel 20 mal langsamer ablaufen soll */
	public static final int SPEED_OPTIONS_VERY_SLOW = 0;
	/** Konstante die angibt, das ein Spiel 10 mal langsamer ablaufen soll */
	public static final int SPEED_OPTIONS_SLOWLY_SLOW = 1;
	/** Konstante die angibt, das ein Spiel 5 mal langsamer ablaufen soll */
	public static final int SPEED_OPTIONS_SLOW = 2;
	/** Konstante die angibt, das ein Spiel normal ablaufen soll */
	public static final int SPEED_OPTIONS_NORMAL = 3;
	/** Konstante die angibt, das ein Spiel 5 Mal schneller ablaufen soll */
	public static final int SPEED_OPTIONS_FAST = 4;
	/** Konstante die angibt, das ein Spiel 10 Mal schneller ablaufen soll */
	public static final int SPEED_OPTIONS_MODERATE_FAST = 5;
	/** Konstante die angibt, das ein Spiel 20 Mal schneller ablaufen soll */
	public static final int SPEED_OPTIONS_VERY_FAST = 6;
	/** String Array Konstante mit den Namen der unterschiedlichen Schnelligkeitsstufen */
	public static final String[] SPEED_OPTIONS = new String[] {
		"20 times slower", "10 times slower", "5 times slower", "normal", "5 times faster", "10 times faster", "20 times faster"
	};
	/** boolean Array für den Editor, welcher die Sichtbarkeit der Buttons regelt */
	public static final boolean[] BUTTON_EDITOR = new boolean[] {false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, false, false};
	/** boolean Array für das Spielmenu, welcher die Sichtbarkeit der Buttons regelt */
	public static final boolean[] BUTTON_GAME = new boolean[] {true, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false};
	/** boolean Array für das eigentliche Spiel, welcher die Sichtbarkeit der Buttons regelt */
	public static final boolean[] BUTTON_PLAY = new boolean[] {true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
	/** boolean Array für die Auswertung, welcher die Sichtbarkeit der Buttons regelt */
	public static final boolean[] BUTTON_ANALYSIS = new boolean[] {true, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false};
	
	/** Linienstärke für die Linie über dem Editorloadbutton */
	public static final Stroke LINE_STROKE = new BasicStroke(4);
	
	/**
	 * rendert einen String 2 mal mit unterschiedlichen Farben (Schwarz und Weiß) verschoben um einen Pixel nach unten und rechts
	 * @param g : Grafikobjekt auf welchem gezeichnet wird
	 * @param s : Der String der gezeichnet werden soll
	 * @param x : Die x-Position an die gezeichnet werden soll
	 * @param y : Die y-Position an die gezeichnet werden soll
	 * @param bLeft : BooleanVariable die angibt, ob der String zentriert werden soll oder links beginnt (TRUE mittig malen, FALSE an X-Position malen
	 */
	public static final void drawString(Graphics2D g, String s, float x, float y, boolean bLeft) {
		ApoSkunkmanConstants.drawString(g, s, x, y, bLeft, Color.BLACK, Color.WHITE);
	}
	

	/**
	 * rendert einen String 2 mal mit unterschiedlichen Farben verschoben um einen Pixel nach unten und rechts
	 * @param g : Grafikobjekt auf welchem gezeichnet wird
	 * @param s : Der String der gezeichnet werden soll
	 * @param x : Die x-Position an die gezeichnet werden soll
	 * @param y : Die y-Position an die gezeichnet werden soll
	 * @param bLeft : BooleanVariable die angibt, ob der String zentriert werden soll oder links beginnt (TRUE mittig malen, FALSE an X-Position malen
	 * @param foreground : Farbe für den Vordergrund
	 * @param background : Farbe für den Hintergrund
	 */
	public static final void drawString(Graphics2D g, String s, float x, float y, boolean bLeft, Color foreground, Color background) {
		int w = 0;
		if (bLeft) {
			w = g.getFontMetrics().stringWidth(s);
		}
		if (s != null) {
			g.setColor(background);
			g.drawString(s, x - w/2 + 1, y + 1);
			g.setColor(foreground);
			g.drawString(s, x - w/2, y);
		}
	}
}