package apoSkunkman.level;

import apoSkunkman.ApoSkunkmanConstants;

/**
 * Klasse, die Konstanten f�r die Level-Generierung bereitstellt
 * @author Enrico Ebert
 */
public class ApoSkunkmanLevelConstants {

	/** ByteKonstante f�r besuchte Gebiete */
	public static final byte VISITED = (byte)(1<<4);
	/** ByteKonstante f�r rechts keine Wand */
	public static final byte RIGHT   = (byte)(1<<0);
	/** ByteKonstante f�r oben keine Wand */
	public static final byte UP      = (byte)(1<<1);
	/** ByteKonstante f�r links keine Wand */
	public static final byte LEFT    = (byte)(1<<2);
	/** ByteKonstante f�r unten keine Wand */
	public static final byte DOWN    = (byte)(1<<3);
	
	/** Konstante f�r GoalX Zeit-Skalierung */
	public static final float LEVEL_GOALX_TIME_SCALE = 1.0f;	// falls Nicht-GoalX als GoalX-Level gespielt werden
	
	/** Konstante f�r Default-Strategie */
	public static final int LEVEL_GENERATION_DEFAULT = ApoSkunkmanConstants.LEVEL_TYPE_STANDARD;//LEVEL_GENERATION_STRATEGIES[0];
}
