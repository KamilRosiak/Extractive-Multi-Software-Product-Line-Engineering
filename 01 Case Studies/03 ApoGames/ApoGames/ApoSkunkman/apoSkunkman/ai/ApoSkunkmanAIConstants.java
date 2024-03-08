package apoSkunkman.ai;

import apoSkunkman.ApoSkunkmanConstants;

/**
 * wichtige Konstanten f�r die KI's
 * @author Dirk Aporius
 *
 */
public class ApoSkunkmanAIConstants {

	/** Konstante die angibt, aller wieviel Millisekunden die Think-Methode aufgerufen wird */
	public static final int WAIT_TIME_THINK = ApoSkunkmanConstants.WAIT_TIME_THINK;

	/** Konstante die angibt, wieviele Millisekunden der Spieler maximal nachdenken darf. Wenn er l�nger ben�tigt, wirft er eine Exception und stirbt */
	public static final int MAX_WAIT_TIME = ApoSkunkmanConstants.MAX_TIME_THINK;
	
	/** gibt die Breite des Spiels zur�ck */
	public final static int GAME_WIDTH = ApoSkunkmanConstants.GAME_WIDTH;
	/** gibt die H�he des Spiels zur�ck */
	public final static int GAME_HEIGHT = ApoSkunkmanConstants.GAME_HEIGHT;
	
	/** Levelkonstante f�r ein freies Feld */
	public final static byte LEVEL_FREE = 0;
	/** Levelkonstante f�r ein Feld, wo ein Stein ist */
	public final static byte LEVEL_STONE = 1;
	/** Levelkonstante f�r ein Feld, wo ein Busch ist */
	public final static byte LEVEL_BUSH = 2;
	/** Levelkonstante f�r ein Feld, wo ein Goodie liegt */
	public final static byte LEVEL_GOODIE = 3;
	/** Levelkonstante f�r ein Feld, wo ein Stinktier/Skunkman liegt */
	public final static byte LEVEL_SKUNKMAN = 4;
	
	/** Goodiekonstante f�r ein Goodie, was die Feuerl�nge des Skunkmans um 1 erh�ht */
	public static final int GOODIE_GOOD_WIDTH = ApoSkunkmanConstants.GOODIE_GOOD_WIDTH;
	/** Goodiekonstante f�r ein Goodie, was die maximale Anzahl an Skunkmans um 1 erh�ht */
	public static final int GOODIE_GOOD_SKUNKMAN = ApoSkunkmanConstants.GOODIE_GOOD_SKUNKMAN;
	/** Goodiekonstante f�r ein Goodie, was die Geschwindigkeit des Spielers um ApoSkunkmanConstants.PLAYER_SPEED_INCREASE erh�ht */
	public static final int GOODIE_GOOD_FAST = ApoSkunkmanConstants.GOODIE_GOOD_FAST;
	/** Goodiekonstante f�r ein Goodie, was alle Werte des Spieler maximiert */
	public static final int GOODIE_GOOD_GOD = ApoSkunkmanConstants.GOODIE_GOOD_GOD;
	/** Goodiekonstante f�r ein Goodie, was die Feuerl�nge des Skunkmans um 1 vermindert */
	public static final int GOODIE_BAD_WIDTH = ApoSkunkmanConstants.GOODIE_BAD_WIDTH;
	/** Goodiekonstante f�r ein Goodie, was die maximale Anzahl an Skunkmans um 1 vermindert */
	public static final int GOODIE_BAD_SKUNKMAN = ApoSkunkmanConstants.GOODIE_BAD_SKUNKMAN;
	/** Goodiekonstante f�r ein Goodie, was die Geschwindigkeit des Spielers um ApoSkunkmanConstants.PLAYER_SPEED_INCREASE senkt */
	public static final int GOODIE_BAD_FAST = ApoSkunkmanConstants.GOODIE_BAD_FAST;
	/** Goodiekonstante f�r ein Goodie, was alle Werte des Spieler minimiert */
	public static final int GOODIE_BAD_GOD = ApoSkunkmanConstants.GOODIE_BAD_GOD;
	
	/** Konstante die angibt, wieviel Zeit vergehen muss, damit ein Spieler, welcher sichtbar ist, einen Punkt erh�lt */
	public static final int POINTS_PLAYER_TIME_TO_NEXT = ApoSkunkmanConstants.PLAYER_TIME_TO_NEXT_POINTS;
	/** Konstante die angibt, wieviel Punkte der Spieler erh�lt, der das Spiel gewinnt */
	public static final int POINTS_PLAYER_WINNING = ApoSkunkmanConstants.PLAYER_WINNING_POINTS;
	/** Konstante die angibt, wieviel Punkte es bringt jemanden mit seinem Skunkman zu verduften */
	public static final int POINTS_SKUNKMAN_ENEMY = ApoSkunkmanConstants.SKUNKMAN_POINTS_ENEMY;
	/** Konstante die angibt, wieviel Punkte es bringt sich selber mit seinem Skunkman zu verduften */
	public static final int POINTS_SKUNKMAN_OWN = ApoSkunkmanConstants.SKUNKMAN_POINTS_OWN;
	/** Konstante die angibt, wieviel Punkte der Spieler f�r das Einsammeln eines Goodies erh�lt */
	public static final int POINTS_GOODIE = ApoSkunkmanConstants.GOODIE_POINTS;
	
	/** gibt die Richtung "runter" bzw Richtung S�den an */
	public static final int PLAYER_DIRECTION_DOWN = ApoSkunkmanConstants.PLAYER_DIRECTION_DOWN;
	/** gibt die Richtung "links" bzw Richtung Westen an */
	public static final int PLAYER_DIRECTION_LEFT = ApoSkunkmanConstants.PLAYER_DIRECTION_LEFT;
	/** gibt die Richtung "rechts" bzw Richtung Osten an */
	public static final int PLAYER_DIRECTION_RIGHT = ApoSkunkmanConstants.PLAYER_DIRECTION_RIGHT;
	/** gibt die Richtung "hoch" bzw Richtung Norden an */
	public static final int PLAYER_DIRECTION_UP = ApoSkunkmanConstants.PLAYER_DIRECTION_UP;
	
	/** Konstante die f�r den Leveltypus steht, wenn das Level eine Zielkoordinate besitzt. Wer dort zuerst ist, hat gewonnen */
	public static final int LEVEL_TYPE_GOAL_X = ApoSkunkmanConstants.LEVEL_TYPE_GOAL_X;
	/** Konstante die den Leveltypen "Standard" steht und keine Zielkoordinate besitzt */
	public static final int LEVEL_TYPE_STANDARD = ApoSkunkmanConstants.LEVEL_TYPE_STANDARD;
	
}
