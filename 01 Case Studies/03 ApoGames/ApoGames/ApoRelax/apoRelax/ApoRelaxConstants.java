package apoRelax;

import java.awt.Font;

public class ApoRelaxConstants {
	
	public static boolean CHEAT = false;
	
	public static final double VERSION = 0.01;
	
	public static boolean FPS = false;
	
	public static final String PROGRAM_NAME = "=== ApoRelax ===";
	
	public static final int FPS_THINK = 100;
	public static final int FPS_RENDER = 50;
	
	public static final int WAIT_TIME_RENDER = (int)(1000.0 / (double)FPS_RENDER);
	public static final int WAIT_TIME_THINK = (int)(1000.0 / (double)FPS_THINK);
	
	public static final int GAME_WIDTH = 640;
	public static final int GAME_HEIGHT = 480;

	public static final float SPEED = 0.2f;
	public static final float SPEED_IMAGE = 0.3f;
	
	public static final Font FONT_FPS = new Font(Font.SANS_SERIF, Font.PLAIN, 10);
	public static final Font FONT_LEVELNAME = new Font(Font.SANS_SERIF, Font.BOLD, 35);
	public static final Font FONT_DESCRIPTIONS = new Font(Font.SANS_SERIF, Font.PLAIN, 20);
	public static final Font FONT_INSTRUCTIONS = new Font(Font.SANS_SERIF, Font.PLAIN, 25);
	public static final Font FONT_STATISTICS = new Font(Font.SANS_SERIF, Font.BOLD, 20);
	public static final Font FONT_IMAGE = new Font(Font.SANS_SERIF, Font.BOLD, 25);
	
	public static final boolean[] BUTTON_GAME = new boolean[] {false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false};
	public static final boolean[] BUTTON_LEVELCHOOSER = new boolean[] {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true};
	public static final boolean[] BUTTON_MENU = new boolean[] {true, true, false, true, false, true, false, true, false, false, false, false, true, false, false, false};
	public static final boolean[] BUTTON_CREDITS = new boolean[] {false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false};
	public static final boolean[] BUTTON_EDITOR = new boolean[] {false, false, false, false, false, false, true, false, false, false, false, false, false, true, true, false};
	public static final boolean[] BUTTON_OPTIONS = new boolean[] {false, false, false, false, false, false, false, false, true, true, true, false, false, false, false, false};
}
