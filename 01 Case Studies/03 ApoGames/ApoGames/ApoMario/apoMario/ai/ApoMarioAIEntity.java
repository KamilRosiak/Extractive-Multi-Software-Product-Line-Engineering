package apoMario.ai;

import java.awt.geom.Rectangle2D;

import apoMario.ApoMarioConstants;
import apoMario.entity.ApoMarioEntity;
import apoMario.level.ApoMarioLevel;

/**
 * Klasse, die ein Objekt im Level repräsentiert
 * @author Dirk Aporius
 *
 */
public class ApoMarioAIEntity {

	private ApoMarioEntity entity;
	
	private Rectangle2D.Float rec;
	
	private ApoMarioLevel level;
	
	protected float startX;
	
	public ApoMarioAIEntity(ApoMarioEntity entity, ApoMarioLevel level, int startX) {
		this.entity = entity;
		this.rec = entity.getRec();
		this.level = level;
		
		this.startX = startX;
	}
	
	public ApoMarioAIEntity(ApoMarioEntity entity, ApoMarioLevel level) {
		this.entity = entity;
		this.level = level;
		this.rec = entity.getRec();
	}
	
	protected void setStartX(float startX) {
		this.startX = startX;
	}
	
	/**
	 * gibt zurück, ob die Entity sichtbar ist
	 * @return TRUE Entity sichtbar, sonst FALSE
	 */
	public boolean isVisible() {
		return this.entity.isBVisible();
	}
	
	/**
	 * gibt eine eindeutige ID für diese Entity zurück
	 * @return gibt eine eindeutige ID für diese Entity zurück
	 */
	public int getID() {
		return this.entity.getId();
	}
	
	/**
	 * gibt die genaue x-Position der Entity zurück
	 * @return gibt die genaue x-Position der Entity zurück
	 */
	public float getX() {
		return (float)(((this.rec.getX()) / ApoMarioConstants.TILE_SIZE)) - this.startX;
	}
	
	/**
	 * gibt die genaue y-Position der Entity zurück
	 * @return gibt die genaue y-Position der Entity zurück
	 */
	public float getY() {
		return (float)(((this.rec.getY() - this.level.getComponent().getChangeY()) / ApoMarioConstants.TILE_SIZE));
	}
	
	/**
	 * gibt die Breite der Entity zurück
	 * @return gibt die Breite der Entity zurück
	 */
	public float getWidth() {
		return (float)this.rec.getWidth() / ApoMarioConstants.TILE_SIZE;
	}

	/**
	 * gibt die Hoehe der Entity zurück
	 * @return gibt die Hoehe der Entity zurück
	 */
	public float getHeight() {
		return (float)this.rec.getHeight() / ApoMarioConstants.TILE_SIZE;
	}
	
	/**
	 * gibt die Geschwindigkeit in x-Richtung zurück pro Millisekunde.<br />
	 * @return gibt die Geschwindigkeit in x-Richtung zurück pro Millisekunde
	 */
	public float getVecX() {
		return (this.entity.getVecX()) / ApoMarioConstants.TILE_SIZE;
	}
	
	/**
	 * gibt die Geschwindigkeit in y-Richtung zurück pro Millisekunde.<br />
	 * @return gibt die Geschwindigkeit in y-Richtung zurück pro Millisekunde
	 */
	public float getVecY() {
		return (this.entity.getVecY()) / ApoMarioConstants.TILE_SIZE;
	}

}
