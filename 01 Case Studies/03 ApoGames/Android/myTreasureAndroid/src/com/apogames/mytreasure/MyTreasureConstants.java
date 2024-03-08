package com.apogames.mytreasure;

import net.gliblybits.bitsengine.graphics.opengl.BitsGLFont;
import net.gliblybits.bitsengine.graphics.opengl.BitsGLImage;

public class MyTreasureConstants {

	public static final int GAME_WIDTH = 320;
	public static final int GAME_HEIGHT = 480;
	
	
	public final static String USERLEVELS_GETPHP = "http://www.apo-games.de/myTreasure/get_level.php";
	public final static String USERLEVELS_SAVEPHP = "http://www.apo-games.de/myTreasure/save_level.php";
	
	public static boolean FREE_VERSION = false;
	
	public static final String PREFS_NAME = "MytreasurePref";
	
	public static String REGION;
	
	static {
		MyTreasureConstants.REGION = "en";
		try {
			MyTreasureConstants.REGION = System.getProperty("user.language");
		} catch (Exception ex) {
			MyTreasureConstants.REGION = "en";
		}
		MyTreasureConstants.setLanguage(MyTreasureConstants.REGION);
	}
	
	public static final String PROGRAM_NAME = "=== MyTreasure ===";
	public static final double VERSION = 0.01;
	
	public static boolean FPS = false;
	public static boolean FIRST_MAP = true;
	public static boolean FIRST_GAME = true;
	public static boolean FIRST_LEVELCHOOSER = true;
	public static boolean FIRST_LEVELCHOOSER_DRAG = true;
	public static boolean SOUND_GAME = true;
	public static boolean MUSIC_GAME = true;
	
	public static int LEVELCHOOSER_STEP = 0;
	
	public static final int FPS_THINK = 100;
	public static final int FPS_RENDER = 100;
	
	public static final int WAIT_TIME_RENDER = (int)(1000.0 / (double)FPS_RENDER);
	public static final int WAIT_TIME_THINK = (int)(1000.0 / (double)FPS_THINK);
	
	public static final int CHANGE_DRAG = 50;
	
	public static BitsGLFont font;
	public static BitsGLFont FONT_FPS;
	public static BitsGLFont FONT_STATISTICS;
	public static BitsGLFont FONT_LEVELCHOOSER;
	
	public static BitsGLFont fontBig;
	public static BitsGLFont fontMedium;
	public static BitsGLFont fontSmall;
	public static BitsGLFont fontVerySmall;
	
	public static BitsGLImage iSheet;
	public static BitsGLImage iWays;
	
	public static final int[] COLOR_DARK = new int[] {78, 61, 37};
	public static final int[] COLOR_LIGHT = new int[] {175, 136, 78};
	public static final int[] COLOR_SEPARATOR = new int[] {68, 68, 68};
	
	public static final int START_IDLE_TEXT_TIME = 5000;
	public static final int END_IDLE_TEXT_TIME = 9000;
	
	public static final String[] TEXT_IDLE = new String[] {
		"come on",
		"gogo",
		"gaehn",
		"zzZZzz",
	};
	
	public static final String[] TEXT_WIN = new String[] {
		"Juhu",
		"Yeah",
		"*gg*",
	};
	
	public static final String[] DIFFICULTY_ENG = new String[] {
		"camp",
		"temple",
		"pyramid",
		"crypt"
	};
	
	public static final String FIRST_COLLECT_ENG[] = new String[] {
		"Your task:",
		"Collect all Relics."
	};
	
	public static final String FIRST_MOVE_ENG[] = new String[] {
		"To move the player, rotate the level.",
		"Touch the screen on the left side",
		"to rotate the level clockwise.",
		"Touch the screen on the right side",
		"to rotate the level counterclockwise.",
	};
	
	public static final String[] DIFFICULTY_DE = new String[] {
		"Camp",
		"Tempel",
		"Pyramide",
		"Gruft"
	};
	
	public static final String FIRST_COLLECT_DE[] = new String[] {
		"Dein Ziel:",
		"Sammle alle Schätze"
	};
	
	public static final String FIRST_MOVE_DE[] = new String[] {
		"Um den Spieler zu bewegen, rotiere das Level.",
		"Berühre die linke Hälfte des Bildschirms,",
		"um das Level im Uhrzeigersinn zu drehen.",
		"Berühre die rechte Hälfte des Bildschirms,",
		"um das Level gegen im Uhrzeigersinn zu drehen.",
	};
	
	public static String[] DIFFICULTY = MyTreasureConstants.DIFFICULTY_ENG;
	public static String[] FIRST_COLLECT = MyTreasureConstants.FIRST_COLLECT_ENG;
	public static String[] FIRST_MOVE = MyTreasureConstants.FIRST_MOVE_ENG;
	
	/** 
	 * 0 = menu quit
	 * 1 = menu play
	 * 2 = menu userlevels
	 * 3 = menu editor
	 * 4 = menu credits / about
	 * 5 = level Chooser back
	 * 6 = game back
	 * 7 = map back
	 * 8 = game ingame restart
	 * 9 = game ingame next
	 * 10 = game ingame previous
	 * 11 = game result back
	 * 12 = game result restart
	 * 13 = game result next
	 * 14 = credits back
	 * 15 = editor back
	 * 16 = editor size left
	 * 17 = editor size right
	 * 18 = editor test
	 * 19 = editor upload
	*/
	public static final boolean[] BUTTON_MENU = new boolean[] {true, true, false, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false};
	public static final boolean[] BUTTON_MAP = new boolean[] {false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
	public static final boolean[] BUTTON_LEVELCHOOSER = new boolean[] {false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
	public static final boolean[] BUTTON_GAME = new boolean[] {false, false, false, false, false, false, true, false, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true};
	public static final boolean[] BUTTON_EDITOR = new boolean[] {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, false, false, false, false, false, false, false, false};
	public static final boolean[] BUTTON_CREDITS = new boolean[] {false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false};
	public static final boolean[] BUTTON_OPTIONS = new boolean[] {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, false, false};

	public static final void setLanguage(final String region) {
		if ((region != null) && (region.equals("de"))) {
			MyTreasureConstants.DIFFICULTY = MyTreasureConstants.DIFFICULTY_DE;
			MyTreasureConstants.FIRST_COLLECT = MyTreasureConstants.FIRST_COLLECT_DE;
			MyTreasureConstants.FIRST_MOVE = MyTreasureConstants.FIRST_MOVE_DE;
		} else {
			MyTreasureConstants.DIFFICULTY = MyTreasureConstants.DIFFICULTY_ENG;
			MyTreasureConstants.FIRST_COLLECT = MyTreasureConstants.FIRST_COLLECT_ENG;
			MyTreasureConstants.FIRST_MOVE = MyTreasureConstants.FIRST_MOVE_ENG;
		}
	}
	
}
