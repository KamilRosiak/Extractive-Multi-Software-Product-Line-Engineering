package apoSkunkman.ai;

import apoSkunkman.entity.ApoSkunkmanSkunkman;

/**
 * Klasse die ein Skunkman im Levelarray darstellt und wichtige Informationen wie <br />
 * Zeit bis zur Explosion, Feuerlänge oder welcher Spieler sie gelegt hat angibt<br />
 * @author Dirk Aporius
 *
 */
public class ApoSkunkmanAILevelSkunkman extends ApoSkunkmanAILevelEntity {

	/** der Skunkman */
	private final ApoSkunkmanSkunkman skunkman;
	
	public ApoSkunkmanAILevelSkunkman(final ApoSkunkmanSkunkman entity) {
		super(entity);
		this.skunkman = entity;
	}

	/**
	 * Zeit, die noch vergehen muss, bis der Skunkman hochgeht in Millisekunden
	 * @return Zeit, die noch vergehen muss bis der Skunkman hochgeht in Millisekunden
	 */
	public int getTimeToExplosion() {
		return this.skunkman.getTimeToExplosion();
	}
	
	/**
	 * gibt zurück, wie groß der Radius des "Stinkens" ist<br />
	 * dabei bedeutet ein Wert von 1, dass das Stinktier in jeder Richtung eine Ausweitung von 1 hat<br />
	 * Bsp: Bei einer Länge von 1 ist also bei der Explosion über und unter und links und rechts vom Stinktier der Feuerradius 1 und bei Stinktier auch selber<br /> 
	 * @return gibt zurück, wie groß der Radius des "Stinkens" ist
	 */
	public final int getSkunkWidth() {
		return this.skunkman.getSkunkWidth();
	}
	
	/**
	 * gibt zurück, welcher Player das Stinktier gelegt hat
	 * @return gibt zurück, welcher Player das Stinktier gelegt hat
	 */
	public final int placedByPlayer() {
		return this.skunkman.getPlayer();
	}
}
