package apoStarz;

import java.awt.Font;

import apoStarz.game.ApoStarzHighscore;

public class ApoStarzConstants {

	public final static int WAIT_TIME = 25;
	
	public static ApoStarzHighscore highscore;

	public final static int ANIMATION_TIME = 200;
	
	public final static int GAME_WIDTH = 640;
	public final static int GAME_GAME_WIDTH = 480;
	public final static int GAME_MENU_WIDTH = 160;
	public final static int GAME_HEIGHT = 480;
	
	public final static int TILE_SIZE = 32;
	
	public final static float VEC = 4;
	
	public final static int THEME_BLUE = 0;
	public final static int THEME_BLACK = 1;
	
	public final static int NORTH = 0;
	public final static int EAST = 1;
	public final static int SOUTH = 2;
	public final static int WEST = 3;
	
	public final static int EMPTY = 0;
	public final static int BLOCK = 1;
	public final static int BLOCK_FALLING = 2;
	public final static int GOAL = 3;
	public final static int STAR = 4;
	public final static int FIRE = 5;
	
	public final static int MAX_TIME_LEVELS = 40;
	
	public final static int MAX_TIME = 300000;
	
	public static final Font FONT_BIG = new Font("Dialog", Font.BOLD, 25);
	public static final Font FONT_VERY_BIG = new Font("Dialog", Font.BOLD, 35);
	public static final Font FONT_NORMAL = new Font("Dialog", Font.BOLD, 15);
	
	public static final String[] LEVELS = new String[] {"very_easy.ase", "easy.ase", "medium.ase", "hard.ase", "very_hard.ase", "fire.ase", "highscore", "only_six"};

	public static final String[] ROTATION_SPEED = new String[] {"slow", "medium", "fast"};
	
	public static final boolean[] MENU_MODE = new boolean[] {false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, false, false, false, false, true, true, true, true, false};
	public static final boolean[] TIME_MODE = new boolean[] {true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
	public static final boolean[] HIGHSCORE_MODE = new boolean[] {true, true, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, true, true, true, false, false, false, false, false, true};
	public static final boolean[] TUTORIAL_MODE = new boolean[] {true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false};
	public static final boolean[] LEVEL_KARTEI = new boolean[] {true, true, true, true, true, true, true, true, true, true, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
	public static final boolean[] LOAD_KARTEI = new boolean[] {true, true, true, true, true, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true};
	
	public static final String[] TUTORIAL_SPEECH = new String[] {
		"Hallo zum Tutorial, ich bin Blinky Star! Um zum nächsten Schritt des Tutorials zu gelangen, drücke einfach auf den nextStep-Button oder drücke Enter!",
		"Das Ziel in ApoStarz besteht darin, mich zu meinem roten Zielpunkt zu geleiten.",
		"Du darfst mich leider nicht direkt bewegen, sondern kannst nur das komplette Level drehen.",
		"Dazu musst du entweder auf die Pfeile links oder rechts über dem Level klicken oder die Pfeiltasten links oder rechts nutzen",
		"Versuche es jetzt einfach mal und bring mich ins Ziel!",
		"Sehr gut gemacht! Doch nicht jedes Level ist so einfach und kommen wir zum nächsten Level",
		"In vielen Levels gibt es noch diese Steine, die mir den Weg nach Hause blockieren. Versuche durch geschicktes Drehen, das Level zu lösen!",
		"Sehr schön gemacht. In einigen Levels gibt es noch mehr Steine, die komplizierter daliegen",
		"z.B. hier ... Versuche mich bitte ins Ziel zu bringen.",
		"Ah danke schön. Es gibt aber nicht nur Steine, die mich behindern können, sondern auch kleine Monster.",
		"Versuche mich hier bitte wieder ins Ziel zu bringen und schau was passiert, wenn ein Stein auf das Monster trifft.",
		"Sehr gut, aber diese kleinen Monster sind nicht nur schlecht für die Steine, sondern auch für mich.",
		"Versuche mich bitte jetzt nicht auf die Monster fallen zu lassen, sonst bin ich leider gefangen und komme nicht mehr nach Hause.",
		"Danke schön, aber manchmal bin nicht nur ich alleine unterwegs nach Hause. Sondern es haben sich noch mehr Sterne verirrt.",
		"Bitte hilf hier allen Sternen. Falls du nicht mehr weiterkommst, drücke einfach auf den retry-Button, um das Level neu zu starten.",
		"Sehr gut, du hast das Tutorial erfolgreich gemeistert",
	};
}