package apoNotSoSimple;

import java.awt.Font;

public class ApoNotSoSimpleConstants {
	
	public static boolean CHEAT = false;
	
	public static final double VERSION = 0.21;
	
	public static boolean FPS = false;
	
	public static final String PROGRAM_NAME = "=== ApoNotSoSimple ===";
	public static final String PROGRAM_URL = "http://www.apo-games.de/apoNotSoSimple/";
	public static final String COOKIE_NAME = "apoNotSoSimple_level";
	
	public static final int FPS_THINK = 100;
	public static final int FPS_RENDER = 50;
	
	public static final int WAIT_TIME_RENDER = (int)(1000.0 / (double)FPS_RENDER);
	public static final int WAIT_TIME_THINK = (int)(1000.0 / (double)FPS_THINK);
	
	public static final int TILE_SIZE = 45;
	public static final int LEVEL_HEIGHT = 3 * TILE_SIZE;
	public static final int LEVEL_WIDTH = 10 * TILE_SIZE;
	
	public static final int EMPTY = 0;
	public static final int GREEN = 1;
	public static final int YELLOW = 2;
	public static final int RED = 3;
	public static final int BLUE = 4;
	
	/** gibt die Richtung "runter" bzw Richtung Süden an */
	public static final int PLAYER_DIRECTION_DOWN = 0;
	/** gibt die Richtung "links" bzw Richtung Westen an */
	public static final int PLAYER_DIRECTION_LEFT = 1;
	/** gibt die Richtung "rechts" bzw Richtung Osten an */
	public static final int PLAYER_DIRECTION_RIGHT = 2;
	/** gibt die Richtung "hoch" bzw Richtung Norden an */
	public static final int PLAYER_DIRECTION_UP = 3;
	/** gibt die Richtung "hoch" bzw Richtung Norden an */
	public static final int PLAYER_DIRECTION_CHANGEVISIBLE_UP = 4;
	/** gibt die Richtung "hoch" bzw Richtung Norden an */
	public static final int PLAYER_DIRECTION_CHANGEVISIBLE_LEFT = 5;
	/** gibt an das es sich um das Finish handelt */
	public static final int PLAYER_DIRECTION_FINISH = 6;
	/** gibt an das es sich um das Finish handelt */
	public static final int PLAYER_DIRECTION_STEP = 7;
	/** gibt an das es sich um das Finish handelt */
	public static final int PLAYER_DIRECTION_STEP_FINISH = 8;
	/** gibt die Richtung "null" an */
	public static final int PLAYER_DIRECTION_NO_MOVEMENT = 9;
	
	/** Konstante die angibt, wie der minimale Speed des Spieler pro Millisekunden ist */
	public static final float PLAYER_SPEED_MIN = 0.15f;
	/** Konstante die angibt, wie der maximale Speed des Spieler pro Millisekunden ist */
	public static final float PLAYER_SPEED_MAX = 0.15f;

	/** Level freies Feld */
	public static final int LEVEL_FREE = 0;
	/** Level fixed Feld */
	public static final int LEVEL_FIXED = 1;
	/** Level player Feld */
	public static final int LEVEL_PLAYER = 2;
	/** Level up movement Feld */
	public static final int LEVEL_UP = 3;
	/** Level down movement Feld */
	public static final int LEVEL_DOWN = 4;
	/** Level left movement Feld */
	public static final int LEVEL_LEFT = 5;
	/** Level right movement Feld */
	public static final int LEVEL_RIGHT = 6;
	/** Level true Feld */
	public static final int LEVEL_VISIBLE_TRUE = 7;
	/** Level false Feld */
	public static final int LEVEL_VISIBLE_FALSE = 8;
	/** Level false Feld */
	public static final int LEVEL_FINISH = 9;
	/** Level false Feld */
	public static final int LEVEL_STEP = 10;
	/** Level false Feld */
	public static final int LEVEL_STEP_FINISH = 11;

	public static final Font FONT_FPS = new Font(Font.SANS_SERIF, Font.PLAIN, 10);
	public static final Font FONT_LEVELNAME = new Font(Font.SANS_SERIF, Font.BOLD, 35);
	public static final Font FONT_DESCRIPTIONS = new Font(Font.SANS_SERIF, Font.PLAIN, 20);
	public static final Font FONT_INSTRUCTIONS = new Font(Font.SANS_SERIF, Font.PLAIN, 25);
	public static final Font FONT_STATISTICS = new Font(Font.SANS_SERIF, Font.BOLD, 20);
	public static final Font FONT_IMAGE = new Font(Font.SANS_SERIF, Font.BOLD, 25);
	
	public static final boolean[] BUTTON_GAME = new boolean[] {false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
	public static final boolean[] BUTTON_LEVELCHOOSER = new boolean[] {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true};
	public static final boolean[] BUTTON_MENU = new boolean[] {true, true, false, true, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, true, false, false, false, false};
	public static final boolean[] BUTTON_CREDITS = new boolean[] {false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
	public static final boolean[] BUTTON_EDITOR = new boolean[] {false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false};
	public static final boolean[] BUTTON_OPTIONS = new boolean[] {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, false};
}
