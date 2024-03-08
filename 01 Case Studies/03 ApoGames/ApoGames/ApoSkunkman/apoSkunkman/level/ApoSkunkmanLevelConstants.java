package apoSkunkman.level;

import apoSkunkman.ApoSkunkmanConstants;

/**
 * Klasse, die Konstanten für die Level-Generierung bereitstellt
 * @author Enrico Ebert
 */
public class ApoSkunkmanLevelConstants {

	/** ByteKonstante für besuchte Gebiete */
	public static final byte VISITED = (byte)(1<<4);
	/** ByteKonstante für rechts keine Wand */
	public static final byte RIGHT   = (byte)(1<<0);
	/** ByteKonstante für oben keine Wand */
	public static final byte UP      = (byte)(1<<1);
	/** ByteKonstante für links keine Wand */
	public static final byte LEFT    = (byte)(1<<2);
	/** ByteKonstante für unten keine Wand */
	public static final byte DOWN    = (byte)(1<<3);
	
	/** Konstante für GoalX Zeit-Skalierung */
	public static final float LEVEL_GOALX_TIME_SCALE = 1.0f;	// falls Nicht-GoalX als GoalX-Level gespielt werden
	
	/** Konstante für Default-Strategie */
	public static final int LEVEL_GENERATION_DEFAULT = ApoSkunkmanConstants.LEVEL_TYPE_STANDARD;//LEVEL_GENERATION_STRATEGIES[0];
}
