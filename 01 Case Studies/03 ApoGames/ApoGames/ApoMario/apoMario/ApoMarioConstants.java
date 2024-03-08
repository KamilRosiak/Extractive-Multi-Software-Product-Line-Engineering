package apoMario;

import java.awt.Font;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apogames.ApoConstants;

import apoMario.entity.ApoMarioPlayer;

/**
 * Konstantenklasse, in der alle wichtigen Konstenten definiert sind
 * @author Dirk Aporius
 *
 */
public class ApoMarioConstants {

	public static final double VERSION = 1.13;
	
	public static int FPS_RENDER = 50;
	
	private static final String PROP_SIZE = "size";
	private static final String PROP_DEBUG = "debug";
	private static final String PROP_FPS = "FPS";
	private static final String PROP_LOAD_EXTERN_IMAGE = "imageLoadExtern";
	private static final String PROP_AI_LEFT = "aiLeft";
	private static final String PROP_AI_RIGHT = "aiRight";
	private static final String PROP_WIDTH = "width";
	private static final String PROP_DIFFICULTY = "difficulty";
	private static final String PROP_MAX_ROUNDS = "maxRounds";
	private static final String PROP_FPS_COUNT = "maxFPS";
	private static final String PROP_BURN = "icarusBurn";
	
	public static final boolean BUFFER_STRATEGY = false;
	
	private static Object props;
	private static final String MARIO_PROPERTIES = "mario.properties";
	
	private static int APPLICATION_SIZE = 2;

	public static boolean DEBUG = false;
	public static boolean FPS = false;
	public static boolean LOAD_EXTERN = false;
	public static String AI_LEFT = "";
	public static String AI_RIGHT = "";
	public static int SIM_WIDTH = 200;
	public static int SIM_DIFFICULTY = 100;
	public static int SIM_MAX_ROUNDS = 5000;
	public static int FPS_COUNT = -1;
	public static boolean ICARUS_BURN = true;
	
	static {
		if (!ApoConstants.B_APPLET) {
			loadProperties();
		}
	}
	
	public static void saveProperties() {
		if (!ApoConstants.B_APPLET) {
			try {
				if (props != null) {
					Properties props = (Properties)(ApoMarioConstants.props);
					props.setProperty(ApoMarioConstants.PROP_SIZE, String.valueOf(ApoMarioConstants.APPLICATION_SIZE));
					props.setProperty(ApoMarioConstants.PROP_DEBUG, String.valueOf(ApoMarioConstants.DEBUG));
					props.setProperty(ApoMarioConstants.PROP_BURN, String.valueOf(ApoMarioConstants.ICARUS_BURN));
					props.setProperty(ApoMarioConstants.PROP_FPS, String.valueOf(ApoMarioConstants.FPS));
					props.setProperty(ApoMarioConstants.PROP_LOAD_EXTERN_IMAGE, String.valueOf(ApoMarioConstants.LOAD_EXTERN));
					props.setProperty(ApoMarioConstants.PROP_AI_LEFT, String.valueOf(ApoMarioConstants.AI_LEFT));
					props.setProperty(ApoMarioConstants.PROP_AI_RIGHT, String.valueOf(ApoMarioConstants.AI_RIGHT));
					props.setProperty(ApoMarioConstants.PROP_WIDTH, String.valueOf(ApoMarioConstants.SIM_WIDTH));
					props.setProperty(ApoMarioConstants.PROP_DIFFICULTY, String.valueOf(ApoMarioConstants.SIM_DIFFICULTY));
					props.setProperty(ApoMarioConstants.PROP_MAX_ROUNDS, String.valueOf(ApoMarioConstants.SIM_MAX_ROUNDS));
					props.setProperty(ApoMarioConstants.PROP_FPS_COUNT, String.valueOf(ApoMarioConstants.FPS_COUNT));
					FileOutputStream out = new FileOutputStream(MARIO_PROPERTIES);
					props.store(out, null);
					out.close();
				}
			} catch (IOException e) {
			} finally {
			}
		}
	}
	
	public static void loadProperties() {
		if (!ApoConstants.B_APPLET) {
			Properties props = new Properties();
			try {
				props.load(new FileInputStream(MARIO_PROPERTIES));
			} catch (IOException ex) {
				System.out.println("exception: can not load properties");
			}
			String size = props.getProperty(PROP_SIZE, "1");
			try {
				APPLICATION_SIZE = Integer.valueOf(size);
			} catch (NumberFormatException ex) {
				APPLICATION_SIZE = 1;
			}
			if (APPLICATION_SIZE < 1) {
				APPLICATION_SIZE = 1;
			} else if (APPLICATION_SIZE > 3) {
				APPLICATION_SIZE = 3;
			}
			String debug = props.getProperty(PROP_DEBUG, "false");
			try {
				DEBUG = Boolean.valueOf(debug);
			} catch (Exception ex) {
				DEBUG = false;
			}
			String icarus = props.getProperty(PROP_BURN, "true");
			try {
				ICARUS_BURN = Boolean.valueOf(icarus);
			} catch (Exception ex) {
				ICARUS_BURN = false;
			}
			String myFPS = props.getProperty(PROP_FPS, "false");
			try {
				FPS = Boolean.valueOf(myFPS);
			} catch (Exception ex) {
				FPS = false;
			}
			String myImageLoad = props.getProperty(PROP_LOAD_EXTERN_IMAGE, "false");
			try {
				LOAD_EXTERN = Boolean.valueOf(myImageLoad);
			} catch (Exception ex) {
				LOAD_EXTERN = false;
			}
			ApoMarioConstants.AI_LEFT = props.getProperty(ApoMarioConstants.PROP_AI_LEFT, "");
			ApoMarioConstants.AI_RIGHT = props.getProperty(ApoMarioConstants.PROP_AI_RIGHT, "");
			String simWidth = props.getProperty(ApoMarioConstants.PROP_WIDTH, "200");
			try {
				ApoMarioConstants.SIM_WIDTH = Integer.valueOf(simWidth);
			} catch (NumberFormatException ex) {
				ApoMarioConstants.SIM_WIDTH = 200;
			}
			if (ApoMarioConstants.SIM_WIDTH < ApoMarioConstants.MIN_WIDTH) {
				ApoMarioConstants.SIM_WIDTH = ApoMarioConstants.MIN_WIDTH;
			} else if (ApoMarioConstants.SIM_WIDTH > ApoMarioConstants.MAX_WIDTH) {
				ApoMarioConstants.SIM_WIDTH = ApoMarioConstants.MAX_WIDTH;
			}
			
			String simDifficulty = props.getProperty(ApoMarioConstants.PROP_DIFFICULTY, "100");
			try {
				ApoMarioConstants.SIM_DIFFICULTY = Integer.valueOf(simDifficulty);
			} catch (NumberFormatException ex) {
				ApoMarioConstants.SIM_DIFFICULTY = 100;
			}
			if (ApoMarioConstants.SIM_DIFFICULTY < ApoMarioConstants.MIN_DIFFICULTY) {
				ApoMarioConstants.SIM_DIFFICULTY = ApoMarioConstants.MIN_DIFFICULTY;
			} else if (ApoMarioConstants.SIM_DIFFICULTY > ApoMarioConstants.MAX_DIFFICULTY) {
				ApoMarioConstants.SIM_DIFFICULTY = ApoMarioConstants.MAX_DIFFICULTY;
			}
			
			String simMaxRounds = props.getProperty(ApoMarioConstants.PROP_MAX_ROUNDS, "5000");
			try {
				ApoMarioConstants.SIM_MAX_ROUNDS = Integer.valueOf(simMaxRounds);
			} catch (NumberFormatException ex) {
				ApoMarioConstants.SIM_MAX_ROUNDS = 5000;
			}
			if (ApoMarioConstants.SIM_MAX_ROUNDS < ApoMarioConstants.MIN_GAMES) {
				ApoMarioConstants.SIM_MAX_ROUNDS = ApoMarioConstants.MIN_GAMES;
			} else if (ApoMarioConstants.SIM_MAX_ROUNDS > ApoMarioConstants.MAX_GAMES) {
				ApoMarioConstants.SIM_MAX_ROUNDS = ApoMarioConstants.MAX_GAMES;
			}
			
			String fpsCount = props.getProperty(ApoMarioConstants.PROP_FPS_COUNT, "50");
			try {
				ApoMarioConstants.FPS_COUNT = Integer.valueOf(fpsCount);
			} catch (NumberFormatException ex) {
				ApoMarioConstants.FPS_COUNT = 50;
			}
			if (ApoMarioConstants.FPS_COUNT > 100) {
				ApoMarioConstants.FPS_COUNT = 100;
			} else if (ApoMarioConstants.FPS_COUNT < 10) {
				ApoMarioConstants.FPS_COUNT = 10;
			}
			ApoMarioConstants.setWaitTimeRender();
			
			ApoMarioConstants.props = props;
		}
	}
	
	private static void setWaitTimeRender() {
		ApoMarioConstants.FPS_RENDER = ApoMarioConstants.FPS_COUNT;
		ApoMarioConstants.WAIT_TIME_RENDER = (int)(1000.0 / (double)ApoMarioConstants.FPS_RENDER);
	}
	
	public static final int TILE_SET_EAT = 0;
	public static final int TILE_SET_MARIO = 1;
	
	public static final int PLAYER_ONE = 0;
	public static final int PLAYER_TWO = 1;
	
	public static int TILE_SET = TILE_SET_MARIO;
	
	public static final int APP_SIZE = APPLICATION_SIZE;
	public static final int SIZE = 1;
	
	public static final int FPS_THINK = 100;
	
	public static int WAIT_TIME_RENDER = (int)(1000.0 / (double)FPS_RENDER);
	public static final int WAIT_TIME_THINK = (int)(1000.0 / (double)FPS_THINK);
	
	public static final int GAME_WIDTH = 320 * APP_SIZE;
	public static final int GAME_HEIGHT = 240 * APP_SIZE;
	
	public static final int TILE_SIZE = 16 * SIZE;

	public static final Font FONT_FPS = new Font("Dialog", Font.PLAIN, 9 + 1 * APP_SIZE);
	
	public static final Font FONT_STATISTICS = new Font("Dialog", Font.BOLD, 10 + 4 * APP_SIZE);
	public static final Font FONT_MENU = new Font("Dialog", Font.BOLD, 10 + 6 * APP_SIZE);
	public static final Font FONT_CREDITS = new Font("Dialog", Font.BOLD, 15 + 6 * APP_SIZE);

	public static final float MENU_PLAYER_VEC = 70f * ApoMarioConstants.SIZE/1000f;
	public static final int MENU_PLAYER_ANIMATION_TIME = 120;
	public static final int MENU_PLAYER_TILES = 2;
	
	public static final int VECY_PLAYER = 0;
	public static final int VECY_ENEMY = 1;
	public static final int VECY_FIREBALL = 2;
	
	public static final int COIN_ANIMATION_FRAMES = 4;
	public static final int COIN_ANIMATION_TIME = 150;
	public static final int PARTICLE_ANIMATION_FRAMES = 3;
	public static final int PARTICLE_ANIMATION_TIME = 180;

	public static final int WALL_DESTRUCABLE_ANIMATION_FRAMES = 4;
	public static final int WALL_DESTRUCABLE_ANIMATION_TIME = 170;
	public static final int WALL_DESTRUCABLE_REPEATTIME = 2000;
	public static final float WALL_PARTICLE_VEC_X = 0.1f * ApoMarioConstants.SIZE;
	public static final float WALL_PARTICLE_VEC_Y = 0.3f * ApoMarioConstants.SIZE;
	public static final float WALL_PARTICLE_ADD_VEC_Y = 0.0035f * ApoMarioConstants.SIZE;
	public static final int QUESTIONMARK_ANIMATION_FRAMES = 4;
	public static final int QUESTIONMARK_ANIMATION_TIME = 170;

	public static final int PLAYER_ANIMATION_FRAMES = 4;
	public static final int PLAYER_ANIMATION_TIME = 170;
	public static final float PLAYER_INCREASE_VEC_X = 1.4f * ApoMarioConstants.SIZE/10000f;
	public static final float PLAYER_DECREASE_VEC_X = 4.0f * ApoMarioConstants.SIZE/10000f;
	public static final float PLAYER_MAX_VEC_X_NORMAL = 90f * ApoMarioConstants.SIZE/1000f;
	public static final float PLAYER_MAX_VEC_X_FAST = 150f * ApoMarioConstants.SIZE/1000f;
	public static final float PLAYER_VEC_Y = 380f * ApoMarioConstants.SIZE/1000f;
	public static final float PLAYER_VEC_Y_ENEMY = ApoMarioConstants.PLAYER_VEC_Y*5f/6f;
	public static final float PLAYER_VEC_DECREASE_Y = 0.001f * ApoMarioConstants.SIZE;
	public static final float PLAYER_VEC_DECREASE_FALL_Y = 0.0007f * ApoMarioConstants.SIZE;
	public static final float PLAYER_VEC_FALL_Y_ICARUS = 0.00004f * ApoMarioConstants.SIZE;

	public static final int PLAYER_TYPE_LITTLE = 0;
	public static final int PLAYER_TYPE_BIG = 1;
	public static final int PLAYER_TYPE_FIRE = 2;
	
	public static final int PLAYER_DAMAGE_TIME = 2100;
	
	public static final int PLAYER_FIREBALL_TIME = 350;
	public static final float PLAYER_FIREBALL_VEC_X = 151f * ApoMarioConstants.SIZE/1000f;
	public static final float PLAYER_FIREBALL_VEC_Y = 120f * ApoMarioConstants.SIZE/1000f;
	public static final float PLAYER_FIREBALL_VEC_DECREASE_Y = 0.001f * ApoMarioConstants.SIZE;
	
	public static final int ENEMY_ANIMATION_FRAMES = 4;
	public static final int ENEMY_ANIMATION_TIME = 150;
	public static final float ENEMY_VEC_X = 80f * ApoMarioConstants.SIZE/1000f;
	public static final float ENEMY_VEC_Y = 100f * ApoMarioConstants.SIZE/1000f;
	public static final float ENEMY_VEC_DECREASE_Y = 0.0005f * ApoMarioConstants.SIZE;
	public static final float ENEMY_VEC_DECREASE_Y_FLY = 0.00018f * ApoMarioConstants.SIZE;
	public static final float ENEMY_VEC_DECREASE_Y_DIE = 0.00042f * ApoMarioConstants.SIZE;
	public static final float ENEMY_VEC_Y_DIE = 80f * ApoMarioConstants.SIZE/1000f;
	
	public static final float CANNON_VEC_X = 105f * ApoMarioConstants.SIZE/1000f;
	
	public static final float FLOWER_VEC_Y = 35f * ApoMarioConstants.SIZE/1000f;
	public static final int FLOWER_ANIMATION_TIME = 180;
	
	public static final float GUMBA_VEC_X = 25f * ApoMarioConstants.SIZE/1000f;
	public static final int GUMBA_ANIMATION_FRAMES = 2;
	public static final int GUMBA_ANIMATION_TIME = 200;

	public static final float KOOPA_VEC_X = 28f * ApoMarioConstants.SIZE/1000f;
	public static final int KOOPA_ANIMATION_FRAMES = 2;
	public static final int KOOPA_ANIMATION_TIME = 200;
	
	public static final float SHELL_VEC_X = 155f * ApoMarioConstants.SIZE/1000f;
	
	public static final float MUSHROOM_VEC_X = 70f * ApoMarioConstants.SIZE/1000f;
	public static final float GOODIE_VEC_Y = 45f * ApoMarioConstants.SIZE/1000f;
	
	public static final float END_VEC_Y = 50f * ApoMarioConstants.SIZE/1000f;
	
	public static final int GOODIE_COIN = 0;
	public static final int GOODIE_GOODIE = 1;
	
	public static final int FLOWER_SPAWNTIME = 1000;
	public static final int CANNON_SPAWNTIME = 4500;
	
	public static final int POINTS_COIN = 80;
	public static final int POINTS_GOODIE = 100;
	public static final int POINTS_GOODIE_MUSHROOM = 500;
	public static final int POINTS_GOODIE_FLOWER = 700;
	public static final int POINTS_DESTRUCTABLE_WALL = 35;
	public static final int POINTS_ENEMY_GUMBA = 150;
	public static final int POINTS_ENEMY_CANNON = 200;
	public static final int POINTS_ENEMY_FLOWER = 500;
	public static final int POINTS_ENEMY_KOOPA = 450;
	public static final int POINTS_ENEMY_UNDESTRUCABLE = 1000;
	public static final int POINTS_END = 10000;
	public static final int POINTS_WIN_AGAINST_ANOTHER = 20000;
	public static final int POINTS_EXCEPTION = -100000;
	
	public static final int EMPTY = 0;
	public static final int WALL = 1;
	public static final int QUESTIONMARKBOX = 2;
	public static final int DESTRUCTIBLEBOX = 3;
	public static final int CANNON = 4;
	public static final int COIN = 5;
	public static final int ONLY_ABOVE_WALL = 6;
	public static final int NO_COLLISION_WALL = 7;
	public static final int FINISH = 8;
	public static final int NO_GROUND_WALL = 9;
	
	public static final Font FONT_BUTTON = new Font(Font.SANS_SERIF, Font.BOLD, 10 * ApoMarioConstants.APP_SIZE);
	public static final Font FONT_BUTTON_START = new Font(Font.SANS_SERIF, Font.BOLD, 15 * ApoMarioConstants.APP_SIZE);
	public static final Font FONT_ARROW = new Font(Font.SANS_SERIF, Font.BOLD, 12 * ApoMarioConstants.APP_SIZE);

	public static final boolean[] BUTTON_EDITOR = new boolean[] {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
	public static final boolean[] BUTTON_GAME = new boolean[] {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
	public static final boolean[] BUTTON_MENU = new boolean[] {true, true, true, true, true, false, false, false, false, false, false, false, false, true, true, true, true, false, false, true, true, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, true, true};
	public static final boolean[] BUTTON_OPTIONS = new boolean[] {false, false, false, false, false, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
	public static final boolean[] BUTTON_ANALYSIS = new boolean[] {false, false, false, false, false, false, false, false, false, false, true, true, true, false, false, false, false, false, false, false, false, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
	public static final boolean[] BUTTON_CREDITS = new boolean[] {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false};
	public static final boolean[] BUTTON_SIMULATION = new boolean[] {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, false, false};
	
	public static final int DRAG_WIDTH = 10 * ApoMarioConstants.APP_SIZE;
	public static final int DRAG_HEIGHT = 10 * ApoMarioConstants.APP_SIZE;

	public static final int MIN_GAMES = 1;
	public static final int MAX_GAMES = 10000;
	public static final int MIN_DIFFICULTY = 0;
	public static final int MAX_DIFFICULTY = 1000;
	public static final int MIN_WIDTH = 100;
	public static final int MAX_WIDTH = 1500;
	
	public static final int TIMES_SLOWER_50 = 0;
	public static final int TIMES_SLOWER_5 = 1;
	public static final int TIMES_NORMAL = 2;
	public static final int TIMES_FASTER_3 = 3;
	public static final int TIMES_FASTER_10 = 4;
	
	public static final float POINT_TIME_MULTIPLICATOR = 0.2f;
	public static final float POINT_DIFFICULTY_MULTIPLICATOR = 15;
	public static final float POINT_WIDTH_MULTIPLICATOR = 100;
	
	public static final String[] TIMES_SPEED = new String[] {
		"50 times slower",
		"5 times slower",
		"normal",
		"3 times faster",
		"10 times faster"
	};
	
	public static float getStartX(ApoMarioPlayer player) {
		int myX = (int)(player.getRec().getX() * ApoMarioConstants.APP_SIZE - ((int)ApoMarioConstants.GAME_WIDTH / 2f));
		if (myX < 0) {
			myX = 0;
		}
		int startX = (int)((myX) / ApoMarioConstants.TILE_SIZE / ApoMarioConstants.APP_SIZE);
		return startX;
	}
}