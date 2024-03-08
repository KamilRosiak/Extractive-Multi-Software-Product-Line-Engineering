package apoMarc;

import java.awt.Color;
import java.awt.Font;

public class ApoMarcConstants {
	
	public static final String PROGRAM_NAME = "ApoMarc";
	
	public static boolean FPS = true;
	
	public static final double VERSION = 0.03;
	
	public static final int GAME_WIDTH = 440;
	public static final int GAME_HEIGHT = 600;
	
	public static boolean EFFECTS = true;
	public static boolean LIGHTS = true;
	
	public static final int GOAL_WIDTH = 200;
	public static final int PLAYER_WIDTH = 60;
	public static final int PLAYER_WIDTH_MARC = 25;
	public static final int PLAYER_WIDTH_MANDY = 50;
	public static final int PLAYER_WIDTH_APO = 65;
	public static final int PLAYER_WIDTH_UNBEATABLE = 80;
	public static final int PADDLE_WIDTH = 30;
	public static final int WINNING_POINTS = 7;
	
	public static final int DIFFICULTY_MARC = 0;
	public static final int DIFFICULTY_MANDY = 1;
	public static final int DIFFICULTY_APO = 2;
	public static final int DIFFICULTY_UNBEATABLE = 3;
	
	public static final Font FONT_FPS = new Font("Dialog", Font.PLAIN, 10);
	
	public static final Font FONT_STATISTICS = new Font("Dialog", Font.BOLD, 20);
	
	public static final Color[] COLOR_ORDER = new Color[] {
		Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW, Color.CYAN, Color.MAGENTA, Color.ORANGE
	};
	
	/**
	 * which button should be shown in which screen
	 */
	public static final boolean[] BUTTON_GAME = new boolean[] {false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
	public static final boolean[] BUTTON_MENU = new boolean[] {true, true, true, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false};
	public static final boolean[] BUTTON_OPTIONS = new boolean[] {false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, true, true, false, false, false};
	public static final boolean[] BUTTON_DIFFICULTY = new boolean[] {false, false, false, false, false, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false};
	public static final boolean[] BUTTON_ACHIEVEMENTS = new boolean[] {false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false};
	public static final boolean[] BUTTON_CREDITS = new boolean[] {false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false};

}
