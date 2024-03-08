package apoJump;

import java.awt.Font;

public class ApoJumpConstants {

	public static final String PROGRAM_NAME = "=== ApoIcarus ===";
	public static final String PROGRAM_URL = "http://www.apo-games.de/apoIcarus/";
	public static final String COOKIE_NAME = "apoIcarus_level";
	
	public static final double VERSION = 0.2;
	
	public static final int FPS_THINK = 100;
	public static final int FPS_RENDER = 100;
	
	public static final int WAIT_TIME_RENDER = (int)(1000.0 / (double)FPS_RENDER);
	public static final int WAIT_TIME_THINK = (int)(1000.0 / (double)FPS_THINK);
	
	public static final int GAME_WIDTH = 640;
	public static final int GAME_HEIGHT = 480;
	
	public static final int COUNTDOWN_TIME = 2000;

	public static final Font FONT_FPS = new Font("Dialog", Font.PLAIN, 10);
	
	public static final Font FONT_STATISTICS = new Font(Font.SANS_SERIF, Font.BOLD, 20);
	public static final Font FONT_BUTTON = new Font(Font.SANS_SERIF, Font.BOLD, 15);
	public static final Font FONT_TEXTFIELD = new Font(Font.SANS_SERIF, Font.BOLD, 30);
	public static final Font FONT_ANALYSIS = new Font(Font.SANS_SERIF, Font.BOLD, 25);
	public static final Font FONT_STATICS = new Font(Font.SANS_SERIF, Font.BOLD, 40);
	public static final Font FONT_COUNTDOWN = new Font(Font.SANS_SERIF, Font.BOLD, 70);
	
	public static final float GRAVITY = 0.01f;
	public static final float MAX_VEC_X = 0.3f;
	public static final float MAX_VEC_Y = 0.77f;
	public static final float MAX_VEC_ARROW = 0.9f;
	
	public static final int POINTS_ENEMY_ONE = 950;
	public static final int POINTS_ENEMY_TWO = 1000;
	public static final int POINTS_ENEMY_THREE = 970;
	public static final int POINTS_ENEMY_FOUR = 1200;
	public static final int POINTS_ENEMY_FIVE = 1020;
	public static final int POINTS_ENEMY_SIX = 1400;
	public static final int POINTS_ENEMY_SEVEN = 1050;
	public static final int POINTS_ENEMY_EIGHT = 1300;
	public static final int POINTS_ENEMY_NINE = 1150;
	public static final int POINTS_ARROW = -30;
	
	public static final int PLATFORM_WIDTH = 60;
	public static final int PLATFORM_HEIGHT = 15;
	
	public static boolean FPS = false; 
	
	public static final boolean[] BUTTONS_MENU = new boolean[] {true, true, true, false, true, true, false, false, false, false, false, false, false, true, false};
	public static final boolean[] BUTTONS_GAME = new boolean[] {false, false, false, true, false, false, false, false, false, false, false, false, false, false, false};
	public static final boolean[] BUTTONS_OPTIONS = new boolean[] {false, false, false, false, false, false, true, false, false, false, false, false, false, false, false};
	public static final boolean[] BUTTONS_ACHIEVEMENTS = new boolean[] {false, false, false, false, false, false, false, true, false, false, false, false, false, false, false};
	public static final boolean[] BUTTONS_HIGHSCORE = new boolean[] {false, false, false, false, false, false, false, false, true, false, false, false, false, false, false};
	public static final boolean[] BUTTONS_HELP = new boolean[] {false, false, false, false, false, false, false, false, false, false, false, false, false, false, true};
}
