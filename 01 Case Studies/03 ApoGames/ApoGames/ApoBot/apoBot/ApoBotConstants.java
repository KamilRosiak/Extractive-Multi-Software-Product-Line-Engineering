package apoBot;

import java.awt.Font;

/**
 * Klasse mit den Konstanten für das Spiel
 * @author Dirk Aporius
 *
 */
public class ApoBotConstants {
	
	public static final int SIZE = 2;
	
	public static final int WAIT_TIME_THINK = 20;
	public static final int WAIT_TIME_RENDER = 20;

	public static final int GAME_WIDTH = 640;
	public static final int GAME_HEIGHT = 480;
	
	public static final int GAME_GAME_WIDTH = 480;
	public static final int GAME_GAME_HEIGHT = 480;
	
	public static final int GAME_MENU_WIDTH = GAME_WIDTH - GAME_GAME_WIDTH;
	public static final int GAME_MENU_HEIGHT = 480;
	
	public static final int ANIMATION_TIME = 200;
	
	public static final int TILE_WIDTH = 23;
	public static final int TILE_HEIGHT = 35;
	
	public static final int GROUND_EMPTY = 0;
	public static final int GROUND_ORIGINAL = 1;
	public static final int GROUND_GOAL = 2;
	public static final int GROUND_REAL_EMPTY = 3;
	public static final int PLAYER = 4;
	
	public static final int DIR_NORTH_UPLEFT = 0;
	public static final int DIR_EAST_UPRIGHT = 1;
	public static final int DIR_SOUTH_DOWNRIGHT = 2;
	public static final int DIR_WEST_DOWNLEFT = 3;
	
	public static final boolean[] BUTTON_MENU = new boolean[] {true, true, true, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
	public static final boolean[] BUTTON_EDITOR = new boolean[] {false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false};
	public static final boolean[] BUTTON_GAME = new boolean[] {false, false, false, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, false, false};
	public static final boolean[] BUTTON_TUTORIAL = new boolean[] {false, false, false, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};

	public static final String[] LEVELS = new String[] {"easy", "middle", "hard", "editor", "tutorial"};
	
	public static final Font FONT_MENU = new Font("Dialog", Font.BOLD, 15);
	public static final Font FONT_WIN = new Font("Dialog", Font.BOLD, 20);
	
	public static final int FORWARD = 0;
	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	public static final int JUMP = 3;
	public static final int REDYE = 4;
	public static final int FUNCTION_ONE = 5;
	public static final int FUNCTION_TWO = 6;
	
	public static final String[] TUTORIAL_SPEECH = new String[] {
		"Artificial Intelligence is hard to program. Not every bot ever created can maneuver and function on its own.",
		"Rather some bots run along a path that the programmer presets for them for various situations.",
		"In ApoBot your job is to redye the blue tiles into red ones by the commands you issue to the ApoBot.",
		"You can drag the commands with the mouse to the empty commandfield.",
		"At first you need to go forward and redye the blue tile. Drag the commands to the main-method. If you want to delete a command ..",
		"press the right mouse button. Finally press the go button to start the bot.",
		"Great. Now you have to turn your bot. Use the left and right commands to turn your direction. Please try it now and solve the level.",
		"Another command is the \"jump\". Wih the jump-command you can reach a higher level. Try it out and redye all blue tiles.",
		"Sometimes you need to create and use functions to solve the levels. You can drag the f1-command into the main method",
		"and all commands in the f1-menu will be used. You can reuse the f1-command as many times as you want. Try it now.",
		"If the level is to big then you can hold down the right mouse button and scroll the map.",
		"The last level is a tricky one. You can try to solve or press the menu button.",
		"That's all for now. Try the puzzles and have FUN."
	};
}
