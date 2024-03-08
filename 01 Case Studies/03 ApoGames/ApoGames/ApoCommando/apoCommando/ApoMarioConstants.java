package apoCommando;

import java.awt.Font;

public class ApoMarioConstants {
	
	public static final boolean BUFFER_STRATEGY = false;
	
	public static String PROGRAM_NAME = "=== ApoCommando ===";
	
	private static int APPLICATION_SIZE = 2;
	public static boolean DEBUG = false;
	public static boolean FPS = false;
	
	public static final int TILE_SET_EAT = 0;
	public static final int TILE_SET_MARIO = 1;
	
	public static int TILE_SET = TILE_SET_MARIO;
	
	public static int DIFFICULTY = 2;
	
	public static final int SIZE = APPLICATION_SIZE;
	
	public static final double VERSION = 1.03;
	
	public static final int FPS_THINK = 100;

	public static final int FPS_RENDER = 100;
	
	public static final int WAIT_TIME_RENDER = (int)(1000.0 / (double)FPS_RENDER);
	public static final int WAIT_TIME_THINK = (int)(1000.0 / (double)FPS_THINK);
	
	public static final int GAME_WIDTH = 320 * SIZE;
	public static final int GAME_HEIGHT = 240 * SIZE;
	
	public static final int TILE_SIZE = 16 * SIZE;

	public static final Font FONT_FPS = new Font("Dialog", Font.PLAIN, 10);
	
	public static final Font FONT_STATISTICS = new Font("Dialog", Font.BOLD, 10 + 3 * SIZE);

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
	public static final float WALL_PARTICLE_ADD_VEC_Y = 0.008f;
	public static final int QUESTIONMARK_ANIMATION_FRAMES = 4;
	public static final int QUESTIONMARK_ANIMATION_TIME = 170;

	public static final int PLAYER_ANIMATION_FRAMES = 4;
	public static final int PLAYER_ANIMATION_TIME = 170;
	public static final float PLAYER_INCREASE_VEC_X = 1.4f * ApoMarioConstants.SIZE/10000f;
	public static final float PLAYER_DECREASE_VEC_X = 4.0f * ApoMarioConstants.SIZE/10000f;
	public static final float PLAYER_MAX_VEC_X_NORMAL = 40f * ApoMarioConstants.SIZE/1000f;
	public static final float PLAYER_MAX_VEC_X_DIF_NORMAL = 10f * ApoMarioConstants.SIZE/1000f;
	public static final float PLAYER_MAX_VEC_X_FAST = 85f * ApoMarioConstants.SIZE/1000f;
	public static final float PLAYER_MAX_VEC_X_DIF_FAST = 15f * ApoMarioConstants.SIZE/1000f;
	public static final float PLAYER_VEC_Y = 350f * ApoMarioConstants.SIZE/1000f;
	public static final float PLAYER_VEC_HIGH_Y = 450f * ApoMarioConstants.SIZE/1000f;
	public static final float PLAYER_VEC_Y_ENEMY = ApoMarioConstants.PLAYER_VEC_Y*5f/6f;
	public static final float PLAYER_VEC_DECREASE_Y = 0.001f * ApoMarioConstants.SIZE;
	public static final float PLAYER_VEC_DECREASE_FALL_Y = 0.0007f * ApoMarioConstants.SIZE;

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
	
	public static final int POINTS_COIN = 100;
	public static final int POINTS_GOODIE = 100;
	public static final int POINTS_GOODIE_MUSHROOM = 1000;
	public static final int POINTS_GOODIE_FLOWER = 1000;
	public static final int POINTS_DESTRUCTABLE_WALL = 25;
	public static final int POINTS_ENEMY_GUMBA = 250;
	public static final int POINTS_ENEMY_CANNON = 300;
	public static final int POINTS_ENEMY_FLOWER = 600;
	public static final int POINTS_ENEMY_KOOPA = 550;
	public static final int POINTS_ENEMY_UNDESTRUCABLE = 1000;
	public static final int POINTS_END = 5000;
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
	
	public static final Font FONT_BUTTON = new Font(Font.SANS_SERIF, Font.BOLD, 10 * ApoMarioConstants.SIZE);
	public static final Font FONT_ARROW = new Font(Font.SANS_SERIF, Font.BOLD, 12 * ApoMarioConstants.SIZE);
	public static final Font FONT_DOS_BOX = new Font(Font.MONOSPACED, Font.PLAIN, 15);
	
	public static final boolean[] BUTTON_GAME = new boolean[] {false};
	public static final boolean[] BUTTON_MENU = new boolean[] {false};
	public static final boolean[] BUTTON_ANALYSIS = new boolean[] {false};

	public static final int DRAG_WIDTH = 10 * ApoMarioConstants.SIZE;
	public static final int DRAG_HEIGHT = 10 * ApoMarioConstants.SIZE;
	
	public static final int MIN_DIFFICULTY = 0;
	public static final int MAX_DIFFICULTY = 1000;
	public static final int MIN_WIDTH = 100;
	public static final int MAX_WIDTH = 1000;
	
	public static final int TIMES_SLOWER_50 = 0;
	public static final int TIMES_SLOWER_5 = 1;
	public static final int TIMES_NORMAL = 2;
	public static final int TIMES_FASTER_3 = 3;
	public static final int TIMES_FASTER_10 = 4;
	
	public static final int POINT_BONUS_COIN = 500;
	public static final int POINT_BONUS_BAR = 1000;
	public static final float POINT_BONUS_BIG = 250;
	public static final float POINT_BONUS_FIRE = 500;
	public static final float POINT_TIME_MULTIPLICATOR = 0.1f;
	public static final float POINT_DIFFICULTY_MULTIPLICATOR = 10;
	public static final float POINT_WIDTH_MULTIPLICATOR = 1;
	
	public static final String[] TUTORIAL_SPEECH = new String[] {
		"Hello, ApoCommando is a mixture of platforming and keyboard command-controlled gaming. Type 'next' and press Enter to start the next step of the tutorial.",
		"You control a little guy who must make it to the finish at the end of each level which is on the right side. (type 'next')",
		"In order to make it there, you'll have to jump, highjump, duck, fire and switch directions, all while the little guy moves forward on his own. (type 'next')",
		"The catch? Instead of tapping keys to command the game, you must actually type \"jump\", \"high\", \"flip\" or other commands. (type 'next')",
		"Let's try it now. Please get over the little mountain (type \"jump\" and press Enter)",
		"Great. In some levels it is very dark. To see the level you have to collect the coins. Keep it in your mind. (type 'next')",
		"Now solve that little situation (type \"duck\" and press Enter)",
		"Your real enemies are the time and the speed of the player. If a level is too hard for you, try another difficulty (etc type \"dif 1\" and press Enter in the menu) (now type 'next')",
		"Now more 10 levels and more than 30 achievements are waiting for you and more than 10 commands for the player and more than 100 hidden words too etc. (type 'next')",
		"Thats all! Now reach the final and have fun with the game.",
	};
}