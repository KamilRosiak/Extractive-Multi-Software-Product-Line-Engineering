package apoSkunkman.ai;

import apoSkunkman.ApoSkunkmanConstants;
import apoSkunkman.entity.ApoSkunkmanEntity;

/**
 * Klasse von der alle Entities erben und grundlegende Methoden zur Verf�gung stellt
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
	 * gibt die x-Koordinate des Spielers zur�ck
	 * @return gibt die x-Koordinate des Spielers zur�ck
	 */
	public float getX() {
		return this.entity.getX() / (float)ApoSkunkmanConstants.TILE_SIZE;
	}
	
	/**
	 * gibt die y-Koordinate des Spielers zur�ck
	 * @return gibt die y-Koordinate des Spielers zur�ck
	 */
	public float getY() {
		return this.entity.getY() / (float)ApoSkunkmanConstants.TILE_SIZE;
	}

	/**
	 * gibt die Breite des Spielers zur�ck
	 * @return gibt die Breite des Spielers zur�ck
	 */
	public float getWidth() {
		return this.entity.getWidth() / (float)ApoSkunkmanConstants.TILE_SIZE;
	}
	
	/**
	 * gibt die H�he des Spielers zur�ck
	 * @return gibt die H�he des Spielers zur�ck
	 */
	public float getHeight() {
		return this.entity.getHeight() / (float)ApoSkunkmanConstants.TILE_SIZE;
	}
	
}
