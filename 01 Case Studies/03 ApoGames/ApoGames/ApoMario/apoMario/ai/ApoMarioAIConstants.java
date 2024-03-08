package apoMario.ai;

import apoMario.ApoMarioConstants;

/**
 * AIKlasse mit relevanten Konstanten für die AIs
 * @author Dirk Aporius
 *
 */
public class ApoMarioAIConstants {

	public static final int LEVEL_EMPTY = ApoMarioConstants.EMPTY;
	public static final int LEVEL_WALL = ApoMarioConstants.WALL;
	public static final int LEVEL_QUESTIONMARKBOX = ApoMarioConstants.QUESTIONMARKBOX;
	public static final int LEVEL_DESTRUCTIBLEBOX = ApoMarioConstants.DESTRUCTIBLEBOX;
	public static final int LEVEL_CANNON = ApoMarioConstants.CANNON;
	public static final int LEVEL_ONLY_ABOVE_WALL = ApoMarioConstants.ONLY_ABOVE_WALL;
	public static final int LEVEL_FINISH = ApoMarioConstants.FINISH;
	
	/** der Spieler beschleunigt mit dieser Geschwindigkeit pro Millisekunde */
	public static final float PLAYER_INCREASE_VEC_X = ApoMarioConstants.PLAYER_INCREASE_VEC_X/ApoMarioConstants.TILE_SIZE;
	/** der Spieler bremst mit dieser Geschnwindigkeit pro Millisekunde */
	public static final float PLAYER_DECREASE_VEC_X = ApoMarioConstants.PLAYER_DECREASE_VEC_X/ApoMarioConstants.TILE_SIZE;
	/** maximale Geschwindigkeit des Spielers, die er erreichen kann, wenn dieser normal läuft und nicht sprintet pro Millisekunde */
	public static final float PLAYER_MAX_VEC_X_NORMAL = ApoMarioConstants.PLAYER_MAX_VEC_X_NORMAL/ApoMarioConstants.TILE_SIZE;
	/** maximale Geschwindigkeit des Spielers, die er erreichen kann, wenn er sprintet pro Millisekunde */
	public static final float PLAYER_MAX_VEC_X_FAST = ApoMarioConstants.PLAYER_MAX_VEC_X_FAST/ApoMarioConstants.TILE_SIZE;
	/** wenn der Spieler vom Boden anfängt mit Springen, dann hat er als allererstes diese Geschwindigkeit pro Millisekunde */
	public static final float PLAYER_VEC_Y = ApoMarioConstants.PLAYER_VEC_Y/ApoMarioConstants.TILE_SIZE;
	/** wenn der Spieler auf einen Gegner springt, dann hat er als allererstes diese Geschwindigkeit pro Millisekunde */
	public static final float PLAYER_VEC_Y_ENEMY = ApoMarioConstants.PLAYER_VEC_Y_ENEMY/ApoMarioConstants.TILE_SIZE;
	/** wenn der Spieler nach oben springt, beschleunigt er mit dieser Geschwindigkeit pro Millisekunde */
	public static final float PLAYER_VEC_DECREASE_Y = ApoMarioConstants.PLAYER_VEC_DECREASE_Y/ApoMarioConstants.TILE_SIZE;
	/** wenn der Spieler nach unten fällt beschleunigt er mit dieser Geschwindigkeit pro Millisekunde */
	public static final float PLAYER_VEC_DECREASE_FALL_Y = ApoMarioConstants.PLAYER_VEC_DECREASE_FALL_Y/ApoMarioConstants.TILE_SIZE;
	/** Zeit in Millisekunden, die pro Think aufruf vergehen */
	public static final int WAIT_TIME_THINK = ApoMarioConstants.WAIT_TIME_THINK;
	
	/** Punkteanzahl für das Ansammeln einer Münze */
	public static final int POINTS_COIN = ApoMarioConstants.POINTS_COIN;
	/** Punkteanzahl, die der Spieler für das Erscheinen eines Goodies bekommt */
	public static final int POINTS_GOODIE = ApoMarioConstants.POINTS_GOODIE;
	/** Punkteanzahl, die der Spieler für das Einsammeln eines Hamburgers/Pilzes bekommt */
	public static final int POINTS_GOODIE_MUSHROOM = ApoMarioConstants.POINTS_GOODIE_MUSHROOM;
	/** Punkteanzahl, die der Spieler für das Einsammeln eines Lollies/Blume bekommt */
	public static final int POINTS_GOODIE_FLOWER = ApoMarioConstants.POINTS_GOODIE_FLOWER;
	/** Punkteanzahl, die der Spieler für das Zerstören einer zerstörbaren Wand bekommt */
	public static final int POINTS_DESTRUCTABLE_WALL = ApoMarioConstants.POINTS_DESTRUCTABLE_WALL;
	/** Punkteanzahl, die der Spieler für das Erledigen eines Gumba Gegners erhält */
	public static final int POINTS_ENEMY_GUMBA = ApoMarioConstants.POINTS_ENEMY_GUMBA;
	/** Punkteanzahl, die der Spieler für das Erledigen einer Kanone erhält */
	public static final int POINTS_ENEMY_CANNON = ApoMarioConstants.POINTS_ENEMY_CANNON;
	/** Punkteanzahl, die der Spieler für das Erledigen eines Pflanze erhält */
	public static final int POINTS_ENEMY_FLOWER = ApoMarioConstants.POINTS_ENEMY_FLOWER;
	/** Punkteanzahl, die der Spieler für das Erledigen eines Koopa Gegners erhält */
	public static final int POINTS_ENEMY_KOOPA = ApoMarioConstants.POINTS_ENEMY_KOOPA;
	/** Punkteanzahl, die der Spieler für das Erledigen eines unzerstörbaren Gegners (durch eine Bananenschale/Panzer) erhält */
	public static final int POINTS_ENEMY_UNDESTRUCABLE = ApoMarioConstants.POINTS_ENEMY_UNDESTRUCABLE;
	/** Punkteanzahl, die der Spieler für das Erreichen des Ziels als Erster erhält */
	public static final int POINTS_END = ApoMarioConstants.POINTS_END;
	/** Punkteanzahl, die der Spieler für das Gewinnen gegen eines anderen Spielers erhält (nur wenn der Gegner stirbt) */
	public static final int POINTS_WIN_AGAINST_ANOTHER = ApoMarioConstants.POINTS_WIN_AGAINST_ANOTHER;
	/** Punkteanzahl, die der Spieler für das Werfen einer Excpetion bekommt */
	public static final int POINTS_EXCEPTION = -100000;
}
