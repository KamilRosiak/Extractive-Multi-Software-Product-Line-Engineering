package apoIcejump.ai;

import apoIcejump.ApoIcejumpConstants;

/**
 * relevante Konstanten<br />
 * constants
 * @author Dirk Aporius
 *
 */
public class ApoIcejumpAIConstants {
	/** Wert für den Feuergoodie / value for the fire goodie */
	public static final int GOODIE_FIRE = ApoIcejumpConstants.GOODIE_FIRE;
	/** Wert für den 3er Eisblockgoodie / value for the 3 new ice block goodie  */
	public static final int GOODIE_ICE_LITTLE = ApoIcejumpConstants.GOODIE_ICE_LITTLE;
	/** Wert für den 6er Eisblockgoodie / value for the 6 new ice block goodie  */
	public static final int GOODIE_ICE_BIG = ApoIcejumpConstants.GOODIE_ICE_BIG;
	/** Wert für den Vereisungsgoodie / value for the slow goodie */
	public static final int GOODIE_SLOWER = ApoIcejumpConstants.GOODIE_SLOWER;
	/** Wert für den Hochsprunggoodie / value for the high jump goodie */
	public static final int GOODIE_HIGHER = ApoIcejumpConstants.GOODIE_HIGHER;
	/** Wert für den Schnelligkeitsgoodie / value for the fast goodie */
	public static final int GOODIE_FASTER = ApoIcejumpConstants.GOODIE_FASTER;
	
	/** maximale Geschwindigkeit in X-Richtung vom Spieler pro Millisekunden<br />
	 * max speed in x-direction per ms */
	public static final float PLAYER_MAX_VEC_X = ApoIcejumpConstants.PLAYER_MAX_VEC_X;
	/** maximale Geschwindigkeit in X-Richtung vom Spieler, wenn er über dem anderen Spieler ist, pro Millisekunden<br />
	 * max speed in x-direction per ms, if the player is over the other player */
	public static final float PLAYER_MAX_VEC_X_OVER_ENEMY = ApoIcejumpConstants.PLAYER_MAX_VEC_X_OVER_ENEMY;
	/** maximale y-Geschwindigkeit vom Spieler pro Millisekunden<br />
	 * max speed in y-direction per ms */
	public static final float PLAYER_MAX_VEC_Y = ApoIcejumpConstants.PLAYER_MAX_VEC_Y;
	/** Millisekunden die vergehen, bis zum nächsten Aufruf<br />
	 * ms until the next call */
	public static final int WAIT_TIME_THINK = ApoIcejumpConstants.WAIT_TIME_THINK;
	/** Breite des Spielfeldes<br />
	 * width of the game */
	public static final int GAME_WIDTH = ApoIcejumpConstants.GAME_WIDTH;
	/** Höhe des Spielfeldes<br />
	 * high of the game */
	public static final int GAME_HEIGHT = ApoIcejumpConstants.GAME_HEIGHT;
	/** Zeit, ab der der SuddenDeath Mode startet (in Millieskundenangabe)<br />
	 * time when the sudden death mode starts in ms */
	public static final int GAME_SUDDEN_DEATH_TIME = ApoIcejumpConstants.GAME_SUDDEN_DEATH_TIME;
	
}
