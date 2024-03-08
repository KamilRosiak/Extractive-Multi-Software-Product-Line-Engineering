package apoSkunkman.ai;

import apoSkunkman.ApoSkunkmanConstants;
import apoSkunkman.entity.ApoSkunkmanEntity;

/**
 * Klasse von der alle Entities erben und grundlegende Methoden zur Verfügung stellt
 * @author Dirk Aporius
 *
 */
public class ApoSkunkmanAIEntity {
	/** Entityobjekt */
	private final ApoSkunkmanEntity entity;
	
	public ApoSkunkmanAIEntity(final ApoSkunkmanEntity entity) {
		this.entity = entity;
	}
	
	/**
	 * gibt die x-Koordinate des Spielers zurück
	 * @return gibt die x-Koordinate des Spielers zurück
	 */
	public float getX() {
		return this.entity.getX() / (float)ApoSkunkmanConstants.TILE_SIZE;
	}
	
	/**
	 * gibt die y-Koordinate des Spielers zurück
	 * @return gibt die y-Koordinate des Spielers zurück
	 */
	public float getY() {
		return this.entity.getY() / (float)ApoSkunkmanConstants.TILE_SIZE;
	}

	/**
	 * gibt die Breite des Spielers zurück
	 * @return gibt die Breite des Spielers zurück
	 */
	public float getWidth() {
		return this.entity.getWidth() / (float)ApoSkunkmanConstants.TILE_SIZE;
	}
	
	/**
	 * gibt die Höhe des Spielers zurück
	 * @return gibt die Höhe des Spielers zurück
	 */
	public float getHeight() {
		return this.entity.getHeight() / (float)ApoSkunkmanConstants.TILE_SIZE;
	}
	
}
