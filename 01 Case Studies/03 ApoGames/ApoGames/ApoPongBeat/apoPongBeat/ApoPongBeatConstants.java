package apoPongBeat;

import java.awt.Font;

public class ApoPongBeatConstants {
	
	public static final String PROGRAM_NAME = "=== ApoPongBeat ===";
	public static final double VERSION = 0.1;
	
	public static final int FPS_THINK = 100;
	public static final int FPS_RENDER = 100;
	
	public static boolean FPS = false;
	
	public static final int WAIT_TIME_RENDER = (int)(1000.0 / (double)FPS_RENDER);
	public static final int WAIT_TIME_THINK = (int)(1000.0 / (double)FPS_THINK);
	
	public static final int GAME_WIDTH = 640;
	public static final int GAME_HEIGHT = 480;
	
	public static final int RACKET_WIDTH = 15;
	public static final int RACKET_HEIGHT = 60;
	
	public static final int PARTICLE_WIDTH = 50;
	public static final int PARTICLE_TIME = 200;
	public static final int PARTICLE_STRING_TIME = 300;
	public static final int PARTICLE_BACKGROUND_STRING_TIME = 1000;
	public static final int PARTICLE_POINTS_STRING_TIME = 600;
	
	public static final int PARTICLE_STRING_LEVEL = 7;
	public static final int PARTICLE_BACKGROUND_LEVEL = 10;
	public static final int PARTICLE_LEVEL = 4;

	public static final int RACKET_X = 30;
	
	public static final int MIN_Y = 60;
	public static final int MAX_Y = ApoPongBeatConstants.GAME_HEIGHT - ApoPongBeatConstants.MIN_Y;

	public static final Font FONT_FPS = new Font("Dialog", Font.PLAIN, 10);
	
	public static final Font FONT_STATISTICS = new Font("Dialog", Font.BOLD, 20);
	
	public static final boolean[] BUTTON_GAME = new boolean[] {false, false, true, false, false, false};
	public static final boolean[] BUTTON_MENU = new boolean[] {true, true, false, false, true, true};
	public static final boolean[] BUTTON_OPTIONS = new boolean[] {false, false, false, true, false, false};
	
	public static final String[] BACKGROUND_STRING = new String[] {
		"Great", "Cool", "ApoPongBeat", "Apo rocks", "Famous", "Genius", "Indiegame", "www.apo-games.de"
	};

}
