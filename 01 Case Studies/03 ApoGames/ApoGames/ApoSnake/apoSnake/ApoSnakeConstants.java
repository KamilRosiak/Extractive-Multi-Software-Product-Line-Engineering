package apoSnake;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ApoSnakeConstants {

	public final static String USERLEVELS_GETPHP = "http://www.apo-games.de/apoSnake/get_level.php";
	public final static String USERLEVELS_SAVEPHP = "http://www.apo-games.de/apoSnake/save_level.php";
	
	public static final int GAME_WIDTH = 480;
	public static final int GAME_HEIGHT = 640;
	
	public static final String PROGRAM_NAME = "=== ApoSnake ===";
	public static final float VERSION = 1.0f;
	
	public static boolean FPS = false;

	public static Font ORIGINAL_FONT;
	
	static {
		try {
			ApoSnakeConstants.ORIGINAL_FONT = Font.createFont(Font.TRUETYPE_FONT, ApoSnakeConstants.class.getResourceAsStream("/font/reprise.ttf") );
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static final boolean[] BUTTON_MENU = new boolean[] {true, true, true, true, false, false, false, false, false, false, false, false, false};
	public static final boolean[] BUTTON_GAME = new boolean[] {false, false, false, false, false, true, false, false, false, false, false, false, false};
	public static final boolean[] BUTTON_PUZZLE = new boolean[] {false, false, false, false, true, false, false, false, false, false, false, false, false};
	public static final boolean[] BUTTON_EDITOR = new boolean[] {false, false, false, false, false, false, true, true, true, true, true, true, true};
	
}
