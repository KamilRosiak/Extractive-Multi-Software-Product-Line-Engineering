package apoSoccer;

import java.awt.Font;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

/**
 * Klasse mit allen Konstanten
 * @author Dirk Aporius
 *
 */
public class ApoSoccerConstants {

	/** boolean Variable die angibt, ob der Debug-Modus an ist oder aus
	 * boolean constant, wheter the debugmode is on or off */
	public static boolean B_DEBUG = false;
	/** boolean Variable, die angibt ob das Spiel in klein angezeigt wird
	 * boolean constant, if the game is drawn from the bird view or normal */
	public static boolean B_LITTLE_GAME = false;
	/** boolean Variable, die angibt, ob im Spiel der SPrachsynthesizer an ist oder off
	 * bolean constant, if the sound is played or not */
	public static final boolean B_MUSIC = false;
	/** boolean Variable die angibt, ob das Spiel kurzzeitig gestoppt werden soll, wenn ein Spieler schiessen darf (zum debuggen) */
	public static boolean B_STOP_SHOOT = false;
	
	public static int NO_HUMAN_PLAYER = 0;
	public static int ONE_HUMAN_PLAYER = 1;
	public static int TWO_HUMAN_PLAYER = 2;
	
	public static int NORTH = 0;
	public static int NORTH_RIGHT = 1;
	public static int RIGHT = 2;
	public static int SOUTH_RIGHT = 3;
	public static int SOUTH = 4;
	public static int SOUTH_LEFT = 5;
	public static int LEFT = 6;
	public static int NORTH_LEFT = 7;
	
	public static final int GAME_WIDTH = 640;
	public static final int GAME_HEIGHT = 480;
	
	public static final int GAME_FIELD_WIDTH = 480;
	public static final int GAME_FIELD_HEIGHT = 480;
	
	public static final int TICKER_X = GAME_FIELD_WIDTH;
	public static final int TICKER_Y = GAME_HEIGHT - 390;
	public static final int TICKER_WIDTH = GAME_WIDTH - GAME_FIELD_WIDTH;
	public static final int TICKER_HEIGHT = 340;	
	
	public static final int PENALTY_AREA_HEIGHT = 250;
	public static final int PENALTY_AREA_WIDTH = 70;
	public static final int GOAL_HEIGHT = 200;
	public static final int GOAL_WIDTH = 50;
	public static final int MIDDLE_CIRCLE_WIDTH = 130;
	
	public static final int COMPLETE_FIELD_WIDTH = 1100;
	public static final int COMPLETE_FIELD_HEIGHT = 700;
	
	public static final long SHOOT_RANDOM = 3;
	
	public static final int APO_HAIR = 25;
	
	/** Spielzeit in Millisekunden
	 * time to play in ms */
	public static final long TIME = 120000;
	public static final long HALF_TIME = TIME / 2;
	
	public static final int BOARD_WIDTH = 100;
	public static final int BOARD_HEIGHT = 100;
	
	/** maximale Breite des Spielfeldes
	 * playground width */
	public static final int FIELD_WIDTH = COMPLETE_FIELD_WIDTH - 2 * BOARD_WIDTH;
	/** maximale Höhe des Spielfeldes
	 * playground height */
	public static final int FIELD_HEIGHT = COMPLETE_FIELD_HEIGHT - 2 * BOARD_HEIGHT;
	
	public static final int RADAR_WIDTH = 200;
	public static final int RADAR_HEIGHT = (int)( (float)RADAR_WIDTH * (float)FIELD_HEIGHT / (float)FIELD_WIDTH ) + 1;
	
	public static final float DELTA_RADAR = (float)RADAR_WIDTH / (float)FIELD_WIDTH;
	
	/** Winkel, welcher von der BLickrichtung abweichen darf zum schiessen */
	public static final int ANGLE_TO_SHOOT = 60;
	
	/** Spieleranzahl
	 * player amount */
	public static final int PLAYER_AMOUNT = 4;
	/** Anzahl an Verteidigern im Spiel
	 * player amount of defenders in the game */
	public static final int PLAYER_AMOUNT_DEFENDER = 2;
	
	/** Konstante, die angibt wieviel ms vergehen müssen um wieder zu denken */
	public static final int WAIT_TIME_THINK = 10;
	/** Konstante, die angibt, wieviel ms vergehen müssen um wieder das Spiel zu zeichnen */
	public static final int WAIT_TIME_RENDER = 20;
	
	/** maximale Drehrichtung pro Tick für einen Spieler 
	 * max change of the line of sight of a player */
	public static final int MAX_CHANGE_LINEOFSIGHT = 15;
	
	public static final int MIN_CHANGE_LINEOFSIGHT = 5;
	
	/** Spiel endet nach 90 Minuten
	 * game ends after 90 minutes */
	public final static int END_TIME = 90;
	
	public final static int ANALYSIS_WIDTH = 400;
	public final static int ANALYSIS_HEIGHT = 400;
	
	/** Konstante, die bei der Halbzeit zur Freshness eines Spielers hinzugefügt wird
	 * constant, which is add at the half time to the playerfreshness */
	public final static int HALFTIME_FRESHNESS_ADD = 15;
	
	/** maximale Geschwindigkeit eines Spielers
	 * max speed for a player to run */
	public final static int MAX_SPEED_PLAYER = 100;
	/** maximale Geschwindigkeit die ein Schuss haben darf
	 * max speed for a shoot */
	public final static int MAX_SPEED_SHOOT = 150;
	/** Konstante, die angibt, wie schnell ein Spieler maximal sein darf, wenn er den Ball führt
	 * constant, how fast a player with the ball can be */
	public final static int MAX_SPEED_WITH_BALL = 40;
	/** Konstante, die angibt, wie schnell ein Spieler minimal sein darf, wenn er den Ball führt
	 * constant, how fast a player with the ball can be */
	public final static int MIN_SPEED_PLAYER = 0;
	/** minimale Geschwindigkeit die ein Schuss haben darf
	 * min speed for a shoot */
	public final static int MIN_SPEED_SHOOT = 0;
	public final static float MIN_RADIUS_SPEED = 0.01f;
	
	public final static int MAX_FRESHNESS_ALLOWANCE = 5;
	/** Konstante, die den maximalen Frischewert eines Spielers zurückgibt
	 * max freshness constant */
	public final static float MAX_FRESHNESS = 100;
	/** Konstante, die den minimalen Frischewert eines Spielers zurückgibt
	 * min freshness constant */
	public final static float MIN_FRESHNESS = 20;
	
	/** float Wert der den Freshnesszuwachs pro tick zurückgibt (beim Laufen) */
	public final static float INCREASE_FRESHNESS = 0.0028f;
	/** float Wert der den Freshnesszuwachs pro tick zurückgibt (beim Stehen) */
	public final static float INCREASE_FRESHNESS_STAND = 0.027f;
	/** float Wert, der von der max Freshness abgezogen wird, wenn die Freshness < maxFreshness */
	public final static float DECREASE_MAX_FRESHNESS = 0.007f;
	
	public final static float BALL_HIT_WALL = 0.9f;
	public final static float BALL_HIT_PLAYER = 0.85f;
	public final static float PLAYER_HIT_PLAYER = 0.9f;
	public final static float BALL_OVER_GRASS = 0.995f;
	
	/** maximaler float Wert den der Spieler in 20 ms wirklich zurücklegen kann */
	public final static float MAX_VEC_PLAYER = 4.5f;
	/** maximaler float Wert den der Ball in 20 ms wirklich zurücklegen kann */
	public final static float MAX_VEC_BALL = 6.5f;
	
	/** float Wert, das die Spielerbeschleunigung in 20 ms zurückgibt */
	public final static float PLAYER_ACCELERATION = 0.03f;
	/** float Wert, das die Torwartbeschleunigung in 20 ms zurückgibt */
	public final static float PLAYER_GOALKEEPER_ACCELERATION = 0.11f;
	/** float Wert, das den Abzug beim Bremsen repräsentiert */
	public final static float PLAYER_BREAK = -0.4f;
	
	public static final int ANIMATION_TIME = 150;
	
	public static final Font FONT_TEAM_NAME = new Font("Dialog", Font.BOLD, 12);
	public static final Font FONT_DEBUG = new Font("Dialog", Font.BOLD, 10);
	public static final Font FONT_LITTLE_PLAYER = new Font("Dialog", Font.BOLD, 8);
	public static final Font FONT_TEAM_NAME_ANAYLSIS = new Font("Dialog", Font.BOLD, 15);
	public static final Font FONT_SCORE = new Font("Dialog", Font.BOLD, 20);
	public static final Font FONT_SCORE_ANAYLSIS = new Font("Dialog", Font.BOLD, 30);
	public static final Font FONT_RENDER = new Font("Dialog", Font.BOLD, 13);
	
	public static final int REPLAY_TIME = 500;
	
	/** Konstante für einen Spieler ohne Haare 
	 * constant for a player without hairs */
	public static final int HAIR_WITHOUT = 0;
	/** Konstante für einen Spieler mit dem ersten Haarstil und einem Ogerfarbton 
	 * constant for a player with the first hairstyle and a oger color */
	public static final int HAIR_ONE_OGER = 1;
	/** Konstante für einen Spieler mit dem ersten Haarstil und einem Grünfarbton 
	 * constant for a player with the first hairstyle and a green color */
	public static final int HAIR_ONE_GREEN = 2;
	/** Konstante für einen Spieler mit dem ersten Haarstil und einem Blaufarbton 
	 * constant for a player with the first hairstyle and a blue color */
	public static final int HAIR_ONE_BLUE = 3;
	/** Konstante für einen Spieler mit dem ersten Haarstil und einem Rotfarbton 
	 * constant for a player with the first hairstyle and a red color */
	public static final int HAIR_ONE_RED = 4;
	/** Konstante für einen Spieler mit dem ersten Haarstil und einem Gelbfarbton 
	 * constant for a player with the first hairstyle and a yellow color */
	public static final int HAIR_ONE_YELLOW = 5;
	/** Konstante für einen Spieler mit dem ersten Haarstil und einem Graufarbton 
	 * constant for a player with the first hairstyle and a gray color */
	public static final int HAIR_ONE_GRAY = 6;
	/** Konstante für einen Spieler mit dem ersten Haarstil und einem Schwarzfarbton 
	 * constant for a player with the first hairstyle and a black color */
	public static final int HAIR_ONE_BLACK = 7;
	/** Konstante für einen Spieler mit dem zweiten Haarstil und einem Grünfarbton 
	 * constant for a player with the second hairstyle and a green color */
	public static final int HAIR_TWO_GREEN = 8;
	/** Konstante für einen Spieler mit dem zweiten Haarstil und einem Blaufarbton 
	 * constant for a player with the second hairstyle and a blue color */
	public static final int HAIR_TWO_BLUE = 9;
	/** Konstante für einen Spieler mit dem zweiten Haarstil und einem Rotfarbton 
	 * constant for a player with the second hairstyle and a red color */
	public static final int HAIR_TWO_RED = 10;
	/** Konstante für einen Spieler mit dem zweiten Haarstil und einem Gelbfarbton 
	 * constant for a player with the second hairstyle and a yellow color */
	public static final int HAIR_TWO_YELLOW = 11;
	/** Konstante für einen Spieler mit dem zweiten Haarstil und einem Graufarbton 
	 * constant for a player with the second hairstyle and a gray color */
	public static final int HAIR_TWO_GRAY = 12;
	
	/** Konstante für einen Spieler mit dem ersten Haarstil und einem Ogerfarbton 
	 * constant for a player with the first hairstyle and a oger color */
	public static final int HAIR_FEMALE_ONE_OGER = 13;
	/** Konstante für einen Spieler mit dem ersten Haarstil und einem Grünfarbton 
	 * constant for a player with the first hairstyle and a green color */
	public static final int HAIR_FEMALE_ONE_GREEN = 14;
	/** Konstante für einen Spieler mit dem ersten Haarstil und einem Blaufarbton 
	 * constant for a player with the first hairstyle and a blue color */
	public static final int HAIR_FEMALE_ONE_BLUE = 15;
	/** Konstante für einen Spieler mit dem ersten Haarstil und einem Rotfarbton 
	 * constant for a player with the first hairstyle and a red color */
	public static final int HAIR_FEMALE_ONE_RED = 16;
	/** Konstante für einen Spieler mit dem ersten Haarstil und einem Gelbfarbton 
	 * constant for a player with the first hairstyle and a yellow color */
	public static final int HAIR_FEMALE_ONE_YELLOW = 17;
	/** Konstante für einen Spieler mit dem ersten Haarstil und einem Graufarbton 
	 * constant for a player with the first hairstyle and a gray color */
	public static final int HAIR_FEMALE_ONE_GRAY = 18;
	/** Konstante für einen Spieler mit dem ersten Haarstil und einem Schwarzfarbton 
	 * constant for a player with the first hairstyle and a black color */
	public static final int HAIR_FEMALE_ONE_BLACK = 19;
	/** Konstante für einen Spieler mit dem zweiten Haarstil und einem Grünfarbton 
	 * constant for a player with the second hairstyle and a green color */
	public static final int HAIR_FEMALE_TWO_GREEN = 20;
	/** Konstante für einen Spieler mit dem zweiten Haarstil und einem Blaufarbton 
	 * constant for a player with the second hairstyle and a blue color */
	public static final int HAIR_FEMALE_TWO_BLUE = 21;
	/** Konstante für einen Spieler mit dem zweiten Haarstil und einem Rotfarbton 
	 * constant for a player with the second hairstyle and a red color */
	public static final int HAIR_FEMALE_TWO_RED = 22;
	/** Konstante für einen Spieler mit dem zweiten Haarstil und einem Gelbfarbton 
	 * constant for a player with the second hairstyle and a yellow color */
	public static final int HAIR_FEMALE_TWO_YELLOW = 23;
	/** Konstante für einen Spieler mit dem zweiten Haarstil und einem Graufarbton 
	 * constant for a player with the second hairstyle and a gray color */
	public static final int HAIR_FEMALE_TWO_GRAY = 24;
	/** unrelavante Variable */
	public static final int BALL_STOP_CHECK = 1000 / WAIT_TIME_THINK * 3;
	
	/** Konstante, die den ersten Verteidiger aus der ArrayList zurückgibt
	 * constant for the first defender */
	public static final int DEFENDER_ONE = 0;
	/** Konstante, die den zweiten Verteidiger aus der ArrayList zurückgibt
	 * constant for the second defender */
	public static final int DEFENDER_TWO = 1;
	/** Konstante, die den Torhüter aus der ArrayList zurückgibt
	 * constant for the goalkeeper */
	public static final int GOALKEEPER = 2;
	/** Konstante, die den Stürmer aus der ArrayList zurückgibt
	 * constant for the forward */
	public static final int FORWARD = 3;
	/** maximale Anzahl an Schritten, die vorausberechnet werden für den Ball */
	public static final int BALL_STEP_SAVE = 400;
	
	/** Rechteck, was das komplette Spielfeld repräsentiert */
	public static final Rectangle2D.Float COMPLETE_REC = new Rectangle2D.Float(0, 0, FIELD_WIDTH, FIELD_HEIGHT);
	/** Rechteck, was die linke Seite des Spielfeldes repräsentiert */
	public static final Rectangle2D.Float LEFT_SIDE_REC = new Rectangle2D.Float(0, 0, FIELD_WIDTH/2, FIELD_HEIGHT);
	/** Rechteck, was die rechte Seite des Spielfeldes repräsentiert */
	public static final Rectangle2D.Float RIGHT_SIDE_REC = new Rectangle2D.Float(FIELD_WIDTH/2, 0, FIELD_WIDTH/2, FIELD_HEIGHT);
	/** Ellipse, was den Mittelkreis repräsentiert */
	public static final Ellipse2D.Double MIDDLE_CIRCLE_ELLIPSE = new Ellipse2D.Double(FIELD_WIDTH/2 - MIDDLE_CIRCLE_WIDTH/2, FIELD_HEIGHT/2 - MIDDLE_CIRCLE_WIDTH/2, MIDDLE_CIRCLE_WIDTH, MIDDLE_CIRCLE_WIDTH);
	
	/** Rechteck, was den linken Torraum repräsentiert
	 * rectangle for the left gaolkeeper area  */
	public static final Rectangle2D.Float LEFT_GOALKEEPER_REC = new Rectangle2D.Float(0, ApoSoccerConstants.FIELD_HEIGHT/2 - ApoSoccerConstants.PENALTY_AREA_HEIGHT/2, ApoSoccerConstants.PENALTY_AREA_WIDTH, ApoSoccerConstants.PENALTY_AREA_HEIGHT);
	/** Rechteck, was den rechten Torraum repräsentiert
	 * rectangle for the right goalkeeper area */
	public static final Rectangle2D.Float RIGHT_GOALKEEPER_REC = new Rectangle2D.Float(ApoSoccerConstants.FIELD_WIDTH - ApoSoccerConstants.PENALTY_AREA_WIDTH, ApoSoccerConstants.FIELD_HEIGHT/2 - ApoSoccerConstants.PENALTY_AREA_HEIGHT/2, ApoSoccerConstants.PENALTY_AREA_WIDTH, ApoSoccerConstants.PENALTY_AREA_HEIGHT);
	
	/** Rechteck, was das linke Tor repräsentiert
	 * rectangle for the left goal */
	public static final Rectangle2D.Float LEFT_GOAL_REC = new Rectangle2D.Float(0 - ApoSoccerConstants.GOAL_WIDTH, ApoSoccerConstants.FIELD_HEIGHT/2 - ApoSoccerConstants.GOAL_HEIGHT/2, ApoSoccerConstants.GOAL_WIDTH, ApoSoccerConstants.GOAL_HEIGHT);
	/** Rechteck, was das rechte Tor repräsentiert
	 * rectangle for the right goal */
	public static final Rectangle2D.Float RIGHT_GOAL_REC = new Rectangle2D.Float(ApoSoccerConstants.FIELD_WIDTH, ApoSoccerConstants.FIELD_HEIGHT/2 - ApoSoccerConstants.GOAL_HEIGHT/2, ApoSoccerConstants.GOAL_WIDTH, ApoSoccerConstants.GOAL_HEIGHT);
	
	/** default Position von Verteidiger Eins auf der linken Seite */
	public static final Point DEFENDER_ONE_LEFT = new Point(ApoSoccerConstants.FIELD_WIDTH * 1 / 4, ApoSoccerConstants.FIELD_HEIGHT*1/4);
	/** default Position von Verteidiger Zwei auf der linken Seite */
	public static final Point DEFENDER_TWO_LEFT = new Point(ApoSoccerConstants.FIELD_WIDTH * 1 / 4, ApoSoccerConstants.FIELD_HEIGHT*3/4);
	/** default Position vom Torwart auf der linken Seite */
	public static final Point GOALKEEPER_LEFT = new Point(30, ApoSoccerConstants.FIELD_HEIGHT/2);
	/** default Position vom Stürmer auf der linken Seite */
	public static final Point FORWARD_LEFT = new Point(ApoSoccerConstants.FIELD_WIDTH * 3 / 4, ApoSoccerConstants.FIELD_HEIGHT/2);
	
	/** default Position vom Verteidiger Eins auf der rechten Seite */
	public static final Point DEFENDER_ONE_RIGHT = new Point(ApoSoccerConstants.FIELD_WIDTH * 3 / 4, ApoSoccerConstants.FIELD_HEIGHT*1/4);
	/** default Position vom Verteidiger Zwei auf der rechten Seite */
	public static final Point DEFENDER_TWO_RIGHT = new Point(ApoSoccerConstants.FIELD_WIDTH * 3 / 4, ApoSoccerConstants.FIELD_HEIGHT*3/4);
	/** default Position vom Torwart auf der rechten Seite */
	public static final Point GOALKEEPER_RIGHT = new Point(ApoSoccerConstants.FIELD_WIDTH - 30, ApoSoccerConstants.FIELD_HEIGHT/2);
	/** default Position vom Stürmer auf der rechten Seite */
	public static final Point FORWARD_RIGHT = new Point(ApoSoccerConstants.FIELD_WIDTH * 1 / 4, ApoSoccerConstants.FIELD_HEIGHT/2);
	
	/** maximale Länge eines Strings zum Sprechen */
	public static final int MAX_SPEECH_STRING_LENGTH = 50;
	/** maximale Anzahl von Spruechen die erlaubt sind */
	public static final int MAX_SPEECH_STRINGARRAY_LENGTH = 10;
	
	public static final boolean[] BUTTON_GAME = new boolean[] {true, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false};
	public static final boolean[] BUTTON_GAME_PAUSE = new boolean[] {true, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, true, true, false, false, false, false, false, false};
	public static final boolean[] BUTTON_REPLAY = new boolean[] {true, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, true, true, true, true, true, true};
	public static final boolean[] BUTTON_MENU = new boolean[] {true, true, false, true, true, true, true, true, true, false, false, false, true, false, false, false, false, true, false, true, true, true, false, false, false, false, false, false, false, false};
	public static final boolean[] BUTTON_MENU_APPLET = new boolean[] {true, true, false, true, true, true, false, false, true, false, false, false, true, true, true, true, true, true, false, true, true, false, false, false, false, false, false, false, false, false};
	public static final boolean[] BUTTON_ANALYSIS = new boolean[] {true, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
	
	public static final String MULTIPLEXERDLLNAME = "AIMultiplexer.dll"; 
}
