package net.apogames.aposnake;

public class ApoSnakeConstants {

	public final static String USERLEVELS_GETPHP = "http://www.apo-games.de/apoSn4ke/get_level.php";
	public final static String USERLEVELS_SAVEPHP = "http://www.apo-games.de/apoSn4ke/save_level.php";
	
	public static final int GAME_WIDTH = 480;
	public static final int GAME_HEIGHT = 640;
	
	public static final String PREFS_NAME = "ApoSnakePref";
	
	public static final boolean[] BUTTON_MENU = new boolean[] {true, true, true, true, false, false, false, false, false, false, false, false, false};
	public static final boolean[] BUTTON_GAME = new boolean[] {false, false, false, false, false, true, false, false, false, false, false, false, false};
	public static final boolean[] BUTTON_PUZZLE = new boolean[] {false, false, false, false, true, false, false, false, false, false, false, false, false};
	public static final boolean[] BUTTON_EDITOR = new boolean[] {false, false, false, false, false, false, true, true, true, true, true, true, true};
	
}
